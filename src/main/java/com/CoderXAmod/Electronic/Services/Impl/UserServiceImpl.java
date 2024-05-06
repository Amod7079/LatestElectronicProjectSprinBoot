package com.CoderXAmod.Electronic.Services.Impl;

import com.CoderXAmod.Electronic.Entities.User;
import com.CoderXAmod.Electronic.Exception.ResourseNotFoundException;
import com.CoderXAmod.Electronic.Services.UserService;
import com.CoderXAmod.Electronic.dtos.PageableResponce;
import com.CoderXAmod.Electronic.dtos.UserDto;
import com.CoderXAmod.Electronic.helper.Helper;
import com.CoderXAmod.Electronic.reposoteries.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired

    private ModelMapper mapper;
    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto CreateUder(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = DtoToEentity(userDto);
        User savedUser = userRepository.save(user);
        UserDto newDto = EntityToDto(savedUser);
        return newDto;
    }


//    public UserDto UpdateUser(UserDto userDto, String userId) {
//
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("UserController Not Find With Given Id "));
//        user.setName(userDto.getName());
//        user.setAbout(userDto.getAbout());
//        user.setGender(userDto.getGender());
//        user.setUserImage(userDto.getUserImage());
//        user.setPassword(userDto.getPassword());
//        User updatedUser = userRepository.save(user);
//        UserDto updatedUserDto = EntityToDto(updatedUser);
//        return updatedUserDto;
//    }
//    public UserDto UpdateUser(UserDto userDto, String userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourseNotFoundException("UserController Not Find With Given Id "));
//        // Perform mapping using AutoMapper
//       // Mapper.map(userDto, user);
//
//        // Save the updated user
//        User updatedUser = userRepository.save(user);
//
//        // Convert the updated user back to UserDto
//        UserDto updatedUserDto = EntityToDto(updatedUser);
//
//        return updatedUserDto;
//    }

    @Override
    public UserDto UpdateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourseNotFoundException("UserController Not Find With Given Id "));
        mapper.map(userDto, user);
        // Save the updated user
        User updatedUser = userRepository.save(user);
        // Convert the updated user back to UserDto
        UserDto updatedUserDto = EntityToDto(updatedUser);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new ResourseNotFoundException("UserController Not found with given id "));
        // delete user profile image
        String fullPath = imagePath + user.getUserImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //delete user
        userRepository.delete(user);
    }

    @Override
    public PageableResponce<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortBy.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponce<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);
        return pageableResponse;
    }
    //upr wala baar baar use me aane wala haiso better hoga ki aap ek package k nandr rakh lo


    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("UserController Not found With Given Module"));
        //ArrayList arrayList=new ArrayList();

        return EntityToDto(user);


    }

    @Override
    public UserDto getUserByEmai(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourseNotFoundException("User Not Found with Given Email !"));
        return EntityToDto(user);

    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().
                map(user -> EntityToDto(user)).
                collect(Collectors.toList());

        return dtoList;
    }

    private UserDto EntityToDto(User savedUser) {
        // UserDto userDto = UserDto.builder().userId(savedUser.getUserId()).name(savedUser.getName()).email(savedUser.getEmail()).UserImage(savedUser.getUserImage()).gender(savedUser.getGender()).password(savedUser.getPassword()).build();
        return mapper.map(savedUser, UserDto.class);
    }

    private User DtoToEentity(UserDto userDto) {
       /* UserController user = UserController.builder().
                userId(userDto.getUserId()).
                name(userDto.getName()).

                UserImage(userDto.getUserImage()).
                about(userDto.getAbout()).
                gender(userDto.getGender()).
                email(userDto.getEmail()).
                password(userDto.getPassword()).build();*/

        return mapper.map(userDto, User.class);
    }
}
