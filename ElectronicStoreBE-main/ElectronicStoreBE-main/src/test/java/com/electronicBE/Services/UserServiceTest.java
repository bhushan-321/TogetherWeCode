package com.electronicBE.Services;

import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.dtos.UserDto;
import com.electronicBE.entities.User;
import com.electronicBE.repositories.UserRepository;
import com.electronicBE.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {

//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ModelMapper  mapper;
//
//    User user;
//
//
//    @BeforeEach
//    public void init(){
//    user=    User.builder()
//                .name("Chaitanya")
//                .email("Chaitanya@gmail.com")
//                .password("ABC")
//                .gender("Male")
//                .imageName("CR.png")
//
//                .build();
//    }
//
//    // Create user
//    @Test
//    public void CreateUser(){
//        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
//
//        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
//        Assertions.assertEquals("Chaitanya",user1.getName());
//
//    }
//
//
//    // Update user
//
//    @Test
//    public void updateUser(){
//
//        Long userId =10L;
//
//         user= User.builder()
//                .name("Chaitanya Renuse")
//                .email("Chaitanya@gmail.com")
//                .password("ABC")
//                .gender("Male")
//                .imageName("CR.png")
//
//                .build();
//
//
//
//        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
//
//        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
//
//        UserDto user2 = userService.updateUser(mapper.map(user,UserDto.class), userId);
//
//        Assertions.assertNotNull(user2);
//        Assertions.assertEquals(user.getName(),user2.getName());
//        System.out.println("Update user: "+user2.getName());
//
//
//    }
//
//    // delete user test
//
//    @Test
//    public void deleteUser() throws IOException {
//        Long userId=10L;
//
//        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
//        userService.deleteUser(userId);
//
//           Mockito.verify(userRepository,Mockito.times(1)).delete(user);
//
//    }
//
//    // get all users test
//
//    @Test
//    public void getAllUaers(){
//
//
//      User  user1= User.builder()
//                .name("Shambhu Renuse")
//                .email("Shambhu@gmail.com")
//                .password("ABC")
//                .gender("Male")
//                .imageName("CR.png")
//
//                .build();
//      User  user2= User.builder()
//                .name("Shivam Renuse")
//                .email("Shivam@gmail.com")
//                .password("ABC")
//                .gender("Male")
//                .imageName("CR.png")
//
//                .build();
//
//        List<User>userList= Arrays.asList(user,user1,user2);
//
//        Page<User>page=new PageImpl<>(userList);
//
//        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
//
//
//        PageableResponse<UserDto> allUsers = userService.getAllUsers(1, 2, "name", "asc");
//        Assertions.assertEquals(userList.size(),allUsers.getContent().size());
//
//    }
//
//    // get userById
//
//    @Test
//    public void getUserById(){
//
//        Long userId=10L;
//
//        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
//
//        UserDto user1 = userService.getUser(userId);
//
//        System.out.println(user1.getName());
//        Assertions.assertEquals("Chaitanya",user1.getName(),"User did not match");
//
//    }
//
//
//    //  get user by email test
//
//    @Test
//    public void GetByEmail(){
//
//        String emailId="Chaitanya@gmail.com";
//
//        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
//
//        UserDto userByEmail = this.userService.getUserByEmail(emailId);
//
//        Assertions.assertEquals("Chaitanya@gmail.com",userByEmail.getEmail(),"Email not match");
//
//    }
//
//    // search user by keyword  test
//
//    @Test
//    public void searchUser(){
//
//
//        User  user1= User.builder()
//                .name("Shambhu Renuse")
//                .email("Shambhu@gmail.com")
//                .password("ABC")
//                .gender("Male")
//                .imageName("CR.png")
//
//                .build();
//        User  user2= User.builder()
//                .name("Shivam Renuse")
//                .email("Shivam@gmail.com")
//                .password("ABC")
//                .gender("Male")
//                .imageName("CR.png")
//
//                .build();
//
//        User  user3= User.builder()
//                .name("AMol Renuse")
//                .email("Amol@gmail.com")
//                .password("ABC")
//                .gender("Male")
//                .imageName("CR.png")
//
//                .build();
//        User  user4= User.builder()
//                .name("Akash Renuse")
//                .email("Akash@gmail.com")
//                .password("ABC")
//                .gender("Male")
//                .imageName("CR.png")
//
//                .build();
//
//        String keyword="Renuse";
//
//        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(Arrays.asList(user,user1,user2,user3,user4));
//
//        List<UserDto> userDtos = userService.searchUser(keyword);
//        Assertions.assertEquals(5,userDtos.size(),"Size not match");
//
//
//    }



}
