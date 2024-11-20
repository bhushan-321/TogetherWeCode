package com.electronicBE.controllers;

import com.electronicBE.dtos.ApiResponseMessage;
import com.electronicBE.dtos.FileResponse;
import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.dtos.UserDto;
import com.electronicBE.services.FileSerivce;
import com.electronicBE.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")


public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private FileSerivce fileSerivce;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;


    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // create

    @PostMapping("/")
//    @Api
    public ResponseEntity<UserDto> CreateUser(@Valid @RequestBody UserDto userDto) {

        UserDto user = this.userService.createUser(userDto);


        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }


    // update

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> UpdateUser(@Valid
                                              @RequestBody UserDto userDto, @PathVariable("userId") Long userId) throws IOException {


        UserDto updateUser = userService.updateUser(userDto, userId);

        return new ResponseEntity<>(updateUser, HttpStatus.OK);

    }


    // delete

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> DeleteUser(@PathVariable("userId") Long userId) throws IOException {

        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("User is deleted successfully").success(true).httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    // get all

    @GetMapping("/All")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                                 @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
                                                                 @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<UserDto> allUsers = userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    // get single

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> GetSingleUser(@PathVariable Long userId) {

        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }


    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> GetSingleUserByEmail(@PathVariable String email) {

        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    // Search

    @GetMapping("/search/{keywords}")
    public ResponseEntity<PageableResponse<UserDto>> SearchUser(@PathVariable String keywords, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        return new ResponseEntity<>(userService.searchUser(keywords, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


    // upload  image

    @PostMapping("/image/{userId}")
    public ResponseEntity<FileResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image,
                                                        @PathVariable Long userId)
            throws IOException {
        String imageName = fileSerivce.uploadImage(image, imageUploadPath);
        UserDto user = userService.getUser(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);
        FileResponse imageResponse = FileResponse.builder().Filename(imageName).success(true).message("image is uploaded successfully ").httpStatus(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);

    }


    // read image

    @GetMapping("/file/{userId}")
    public void GetFile(@PathVariable("userId") Long userId, HttpServletResponse response) throws IOException {

        UserDto user = this.userService.getUser(userId);

        InputStream resource = this.fileSerivce.getResource(imageUploadPath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());


    }

//    @PostMapping("/forgot-password/{email}")
    @RequestMapping(value = "/forgot-password/{email}",method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>>forgotPassword(@PathVariable("email")String email){
        Map<String, String> response = new HashMap<>();


        String result = this.userService.forgotPassword(email);

        response.put("message", result);



        return new ResponseEntity<>(response,HttpStatus.OK);
    }




}
