package com.electronicBE.Controller;

import com.electronicBE.dtos.UserDto;
import com.electronicBE.entities.User;
import com.electronicBE.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserController {

//
//    @MockBean
//    private UserService userService;
//
//    private User user;
//
//    @Autowired
//    private ModelMapper mapper;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void init() {
//
//
//         user = User.builder()
//                .name("Chaitanya")
//                .email("Chaitanya@gmail.com")
//                .password("ABC")
//                .gender("Male")
//                .imageName("CR.png")
//
//                .build();
//
//    }
//
//
//    @Test
//    public void createUser() throws Exception {
//
//        UserDto dto = mapper.map(user, UserDto.class);
//
//        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);
//
//        //actual request for url
//
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(convertObjectToJsonString(user))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                 .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").exists());
//    }
//
//
//
//
//    private String convertObjectToJsonString(Object user) {
//
//      try{
//
//          return  new ObjectMapper().writeValueAsString(user);
//      }catch (Exception e){
//          e.printStackTrace();
//          return null;
//      }
//
//
//    }


}
