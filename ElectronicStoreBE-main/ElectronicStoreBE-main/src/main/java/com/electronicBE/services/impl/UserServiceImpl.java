package com.electronicBE.services.impl;

import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.dtos.UserDto;
import com.electronicBE.entities.Role;
import com.electronicBE.entities.User;
import com.electronicBE.exceptions.BadApiException;
import com.electronicBE.exceptions.ResourceNotFoundException;
import com.electronicBE.exceptions.UserFoundException;
import com.electronicBE.helper.Helper;
import com.electronicBE.repositories.RoleRepository;
import com.electronicBE.repositories.UserRepository;
import com.electronicBE.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired

    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${normal.role.id}")
    private String roleNormal;

    @Value("${admin.role.id}")
    private String role_admin;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToEntity(userDto);

        Optional<User> user1 = this.userRepository.findByEmail(user.getEmail());

        if (user1.isPresent()) {


            throw new UserFoundException("User with this email already present !!!");
        }


        Role normalRole = roleRepository.findById("2").get();


        user.getRoles().add(normalRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        // entity -> dto
        UserDto userDto1 = entityToDto(savedUser);


        return userDto1;
    }


    @Override
    public UserDto updateUser(UserDto userDto, Long userId) throws IOException {
        // dto -> entity

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given  id"));

        user.setName(userDto.getName());
//        if (user.getImageName() != null) {
//
//            String fullPath = imageUploadPath + user.getImageName();
////
//            Path path = Paths.get(fullPath);
//            if (path != null) {
//                Files.delete(path);
//            }


//            File previousImage = new File(fullPath);
//            if (previousImage.exists()) {
//                previousImage.delete();
//                user.setImageName(userDto.getImageName());
//            }

//        }

        user.setImageName(userDto.getImageName());
        System.out.println(user.getImageName());
        user.setImageName(userDto.getImageName());

        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        User updatedUser = this.userRepository.save(user);

        UserDto updatedDto = entityToDto(updatedUser);

        return updatedDto;
    }


    @Override
    public void deleteUser(Long userID) throws IOException {

        User user = this.userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        String fullpath = imageUploadPath + user.getImageName();

        try {
            Path p = Paths.get(fullpath);

            Files.delete(p);
        } catch (NoSuchFileException ex) {
            ex.printStackTrace();
        } catch (IOException x) {
            x.printStackTrace();
        }

        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy)).descending() : (Sort.by(sortBy)).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = this.userRepository.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);


        return response;
    }

    @Override
    public UserDto getUser(Long userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User no found with given id"));


        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found given email !!"));

        return entityToDto(user);
    }

    @Override
    public PageableResponse<UserDto> searchUser(String keyword , int pageNumber,int pageSize, String sortBy, String sortDir ) {


        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);

        Page<User> byNameContaining = userRepository.findByNameContaining(keyword, pageable);


        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(byNameContaining, UserDto.class);

        return pageableResponse;
    }

    @Override
    public Optional<User> findByEmailOptional(String email) {

        return this.userRepository.findByEmail(email);


    }

    private UserDto entityToDto(User user) {

//        UserDto userDto = UserDto.builder().userId(user.getUserId()).name(user.getName()).about(user.getName()).email(user.getEmail()).gender(user.getGender()).imageName(user.getImageName()).password(user.getPassword()).build();


        return mapper.map(user, UserDto.class);


    }

    private User dtoToEntity(UserDto userDto) {

//        User user = User.builder().userId(userDto.getUserId()).about(userDto.getAbout()).password(userDto.getPassword()).email(userDto.getEmail()).gender(userDto.getGender()).name(userDto.getName()).imageName(userDto.getImageName()).build();


        return mapper.map(userDto, User.class);
    }

    // Create admin user

    @Override
    public void adminUser() {

        User adminUser = new User();

        Role adminRole = roleRepository.findById("1").get();

        Optional<User> byEmail = this.findByEmailOptional(adminUser.getEmail());
        if (byEmail.isPresent()) {
            throw new RuntimeException("User is found");
        } else {
            adminUser.setName("admin");

            adminUser.setEmail("admin@gmail.com");
            adminUser.setAbout("hi im admin");
            adminUser.setPassword(passwordEncoder.encode("12345"));
            adminUser.getRoles().add(adminRole);
            adminUser.setImageName("default.png");


            User savedUser = userRepository.save(adminUser);
            System.out.println(savedUser.toString());

        }


    }

    @Override
    public void normalUser() {

        User normalUser = null;


        Role normalRole = roleRepository.findById("2").get();
        Optional<User> email = this.findByEmailOptional(normalUser.getEmail());
        if (email.isPresent()) {
            throw new RuntimeException("User is found");
        } else {
            normalUser.setName("Chaitanya");

            normalUser.setEmail("chaitanya@gmail.com");
            normalUser.setAbout("hi im chaitanya");
            normalUser.setPassword(passwordEncoder.encode("12345"));
            normalUser.getRoles().add(normalRole);


            userRepository.save(normalUser);
            System.out.println(normalUser.toString());

        }

    }

    @Override
    @Async
    public String forgotPassword(String email) {

        String resp=null;

        try {

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email !!!"));

        String subject="Request for forgot password";

        String body="";


            String newPassword = generateRandomString(20);



        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
            body = "Dear User,\n\n"
                    + "Your password has been updated successfully. For security reasons, we have generated a temporary password for you.\n\n"
                    + "Your new temporary password is: " + newPassword + "\n\n"
                    + "Please note that this password is automatically generated and may not be secure. "
                    + "We strongly recommend that you log in to your account and change your password immediately.\n\n"
                    + "To ensure the security of your account, please choose a strong password that includes a combination of uppercase and lowercase letters, numbers, and special characters.\n\n"
                    + "If you did not request this change or if you have any concerns, please contact our support team immediately.\n\n"
                    + "Thank you for your attention to this important matter.\n\n"
                    + "Best regards,\n"
                    + "Ecommerce Name Support Team";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("Chaitanya.renuse17@gmail.com");

        mailSender.send(simpleMailMessage);
        resp="Mail send successfully please check mail";

        }catch (Exception e){


            throw new BadApiException(e.getMessage());
        }


        return resp;
    }




    private static String generateRandomString(int length){

        Random random=new Random();

        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ@$abcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder sb = new StringBuilder(length);


        for(int i=0;i<length;i++){
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        
        
        return  sb.toString();


    }




}
//