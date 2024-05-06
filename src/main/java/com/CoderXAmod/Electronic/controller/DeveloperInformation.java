package com.CoderXAmod.Electronic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeveloperInformation {

 @RequestMapping("/Info")  // Ensure this annotation specifies POST method
    public String printIntroduction(@RequestParam("name") String name,
                                    @RequestParam("jobTitle") String jobTitle,
                                    @RequestParam("technology") String technology,
                                    @RequestParam("imageUrl") String imageUrl) {
        // Here you can process the form data as needed
        System.out.println("Name: " + name);
        System.out.println("Job Title: " + jobTitle);
        System.out.println("Technology: " + technology);
        System.out.println("Image URL: " + imageUrl);

        // You can return a view name or redirect to another URL
        // For simplicity, let's return a view name "hello" (assuming you have a corresponding view)
        return "hello";
    }
}
