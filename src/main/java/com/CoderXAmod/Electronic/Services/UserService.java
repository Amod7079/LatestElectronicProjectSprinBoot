package com.CoderXAmod.Electronic.Services;

import com.CoderXAmod.Electronic.Entities.User;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import com.CoderXAmod.Electronic.dtos.UserDto;

import java.util.List;

public interface UserService {


    // create user
    UserDto CreateUder(UserDto userDto);


    // update
    UserDto UpdateUser(UserDto userDto, String user_iD);
    //delete

    void deleteUser(String user_id);

    // getAllUser
   PageableResponce<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    // get Single user by id 😒
    UserDto getUserById(String userId);

    //get user by email
    UserDto getUserByEmai(String email);

    //serch user🔍
    List<UserDto> searchUser(String keyword);
    // etc 👍


}
