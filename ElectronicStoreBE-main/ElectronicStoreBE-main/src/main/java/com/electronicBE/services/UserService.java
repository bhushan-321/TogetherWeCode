package com.electronicBE.services;

import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.dtos.UserDto;
import com.electronicBE.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    // create

    UserDto createUser(UserDto userDto);

    // update

    UserDto updateUser(UserDto userDto,Long userId) throws IOException;

    // delete

    void deleteUser(Long userID) throws IOException;



    // get all users

    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String  sortBy, String sortDir);

    // get single by id

    UserDto getUser(Long userId);

    // get single user by email

    UserDto  getUserByEmail(String email);

    // search user

    PageableResponse<UserDto> searchUser(String keyword, int pageNumber,int pageSize, String sortBy, String sortDir );

    // other user specific features
    
    // file upload

    Optional<User> findByEmailOptional(String email);

    // create admin user
    void adminUser();

    void normalUser();

    // forgot password


    String forgotPassword(String email);



}
