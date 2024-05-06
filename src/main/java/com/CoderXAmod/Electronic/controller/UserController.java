package com.CoderXAmod.Electronic.controller;

import com.CoderXAmod.Electronic.Services.FileService;
import com.CoderXAmod.Electronic.Services.UserService;
import com.CoderXAmod.Electronic.dtos.ApiResponseMessage;
import com.CoderXAmod.Electronic.dtos.ImageResponse;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import com.CoderXAmod.Electronic.dtos.UserDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private UserService userService;
@Autowired
    private FileService fileService;
@Value("${user.profile.image.path}")
private String imageUploadpath;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto userDto1 = userService.CreateUder(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") String userId,
                                              @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.UpdateUser(userDto, userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);

    }
    // delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().Message("User Is Deleted Sucessfully !! 👍").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    // get All
    @GetMapping
    public ResponseEntity<PageableResponce<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue ="10",required = false ) int pageSize,
            @RequestParam(value = "sortBy",defaultValue ="name",required = false ) String sortBy,
            @RequestParam(value = "sortDir",defaultValue ="asc",required = false ) String sortDir
            ) {
//        ArrayList<Integer> al=new ArrayList<>();
//        al.ensureCapacity(10);
//        al.ensureCapacity(20);
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir), HttpStatus.OK);
    }
    // get sinGLE
    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
    // get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmai(email), HttpStatus.OK);
    }
    // search user
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }
    // upload user image
    @SneakyThrows
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,@PathVariable String userId)
    {
        String imageName=fileService.UploadFile(image,imageUploadpath);
        UserDto user = userService.getUserById(userId);
        user.setUserImage(imageName);
        UserDto userDto = userService.UpdateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().ImageName(imageName).Message("Image Upload Sucessfully 👍").success(true).status(HttpStatus.CREATED).build();
        return  new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
    // serve user Image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto userById = userService.getUserById(userId);
        InputStream resourse = fileService.getResourse(imageUploadpath, userById.getUserImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resourse,response.getOutputStream());
    }
}
