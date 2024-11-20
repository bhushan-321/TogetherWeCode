package com.electronicBE.controllers;

import com.electronicBE.dtos.JwtRequest;
import com.electronicBE.dtos.JwtResponse;
import com.electronicBE.dtos.RoleDto;
import com.electronicBE.dtos.UserDto;
import com.electronicBE.entities.Role;
import com.electronicBE.entities.User;
import com.electronicBE.exceptions.BadApiException;
import com.electronicBE.repositories.RoleRepository;
import com.electronicBE.security.JwtFilter;
import com.electronicBE.security.JwtHelper;
import com.electronicBE.services.UserService;
import com.electronicBE.services.impl.UserDetailService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private ModelMapper mapper;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;

    @Value("${googleClientId}")
    private String googleClientId;
    @Value("${newPassword}")
    private String newPassword;

    @Autowired
    private RoleRepository roleRepository;



//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @GetMapping("/current-user")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        String name = principal.getName();
        return new ResponseEntity<>(mapper.map(userDetailService.loadUserByUsername(name), UserDto.class), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {




        this.doAuthentication(jwtRequest.getEmail(), jwtRequest.getPassword());



        UserDetails userDetails = this.userDetailService.loadUserByUsername(jwtRequest.getEmail());


        String token = jwtHelper.generateToken(userDetails);



        UserDto userDto = mapper.map(userDetails, UserDto.class);

        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(token)
                .userDto(userDto)
                .tokenExpired(this.jwtFilter.tokenExpired)
                .build();

        System.out.println("jwtResponse "+jwtResponse);


        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private void doAuthentication(String email, String password) {



        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);


        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException ex) {
            throw new BadApiException("Invalid username and password !!");

        }

    }

    @PostMapping("/google")
    public ResponseEntity<JwtResponse> google(@RequestBody Map<String, Object> data) throws IOException {
//get the id token from request

        try{
        String idToken = data.get("idToken").toString();

        NetHttpTransport netHttpTransport = new NetHttpTransport();

        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClientId));


        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), idToken);


        GoogleIdToken.Payload payload = googleIdToken.getPayload();

//        logger.info("Payload : {}", payload);

        String email = payload.getEmail();

        User user = null;

        user = userService.findByEmailOptional(email).orElse(null);



        if (user == null) {
            //create new user
            user = this.saveUser(email, data.get("name").toString(), data.get("photoUrl").toString(),newPassword);
        }
        ResponseEntity<JwtResponse> jwtResponseResponseEntity = this.login(JwtRequest.builder().email(user.getEmail()).password(newPassword).build());
        return jwtResponseResponseEntity;
        }catch (Exception e){

            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }


    }

    private User saveUser(String email, String name, String photoUrl,String password) {

        Role normalRole = roleRepository.findById("2").get();
        RoleDto roleDto = mapper.map(normalRole, RoleDto.class);
        Set<RoleDto> roleSet = new HashSet<>();


        roleSet.add(roleDto);

        UserDto userDto = UserDto.builder().
                name(name)
                .email(email)
                .password(password)
                .roles(new HashSet<>())
                .imageName(photoUrl)
                .build();

        this.userService.createUser(userDto);

        return mapper.map(userDto, User.class);

    }


}
