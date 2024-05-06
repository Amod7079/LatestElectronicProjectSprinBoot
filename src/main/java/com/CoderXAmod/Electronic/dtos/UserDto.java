package com.CoderXAmod.Electronic.dtos;


import com.CoderXAmod.Electronic.validate.ImageNameValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;
@Size(min=3,max = 15,message = "Invalid Name !!")
    private String name;
@Email(message = "Email Not valid !!")
    private String email;
    @NotBlank(message = "Password Is Required !!")
    private String password;
    @Size(min=4,max=6,message = "invalid Gender")
    private String gender;
@NotBlank(message = "*Necessary")
    private String about;
@ImageNameValid
    private String UserImage;
}



