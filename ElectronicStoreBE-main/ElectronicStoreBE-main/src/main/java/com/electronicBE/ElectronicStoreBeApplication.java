package com.electronicBE;

import com.electronicBE.entities.Role;
import com.electronicBE.entities.User;
import com.electronicBE.repositories.RoleRepository;
import com.electronicBE.repositories.UserRepository;
import com.electronicBE.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = "com.electronicBE")
public class ElectronicStoreBeApplication implements ApplicationRunner {


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${normal.role.id}")
    private String role_normal_id;
    @Value("${admin.role.id}")
    private String role_admin_id;

    @Autowired(required = true)
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ElectronicStoreBeApplication.class);
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {


        try {
            Role roleAdmin = roleRepository.findByroleName("ROLE_ADMIN");
            if(roleAdmin==null){
              roleAdmin = Role.builder()
                        .roleId(role_admin_id)
                        .roleName("ROLE_ADMIN")
                        .build();
              this.roleRepository.save(roleAdmin);
            }

            Role roleNormal = roleRepository.findByroleName("ROLE_NORMAL");
            if(roleNormal==null){
                roleAdmin = Role.builder()
                        .roleId(role_normal_id)
                        .roleName("ROLE_NORMAL")
                        .build();
                this.roleRepository.save(roleAdmin);
            }

//            User adminUser = new User();
//
//            Optional<User> byEmailOptional = this.userService.findByEmailOptional(adminUser.getEmail());
//
//
//            if (!byEmailOptional.isPresent()) {
//
//                Role roleAdmin1 = this.roleRepository.findByroleName("ROLE_ADMIN");
//                Role roleNormal1 = this.roleRepository.findByroleName("ROLE_NORMAL");
//
//                adminUser.setName("Admin");
//                adminUser.setEmail("admin@gmail.com");
//                adminUser.setPassword(passwordEncoder.encode("12345"));
//                adminUser.setAbout("Hi im admin");
//                adminUser.setGender("male");
//                adminUser.addRole(roleAdmin1);
//
//                adminUser.addRole(roleNormal1);
//                User saveUser = this.userRepository.save(adminUser);
//                System.out.println(saveUser.toString());
//
//
//            } else {
//                System.out.println("Liner working");
//                throw new RuntimeException("User is already present");
//
//            }

            if(userRepository.findByName("admin")==null){
                Role roleAdminUser = this.roleRepository.findByroleName("ROLE_ADMIN");
                Role roleNormalUser = this.roleRepository.findByroleName("ROLE_NORMAL");
                User adminUser = new User();
                adminUser.setName("Admin");
                adminUser.setEmail("admin@gmail.com");
                adminUser.setPassword(passwordEncoder.encode("test"));
                adminUser.setAbout("Hi im admin");
                adminUser.setGender("male");
                adminUser.addRole(roleAdminUser);
                adminUser.addRole(roleNormalUser);
                User saveUser = this.userRepository.save(adminUser);
                System.out.println(saveUser.toString());
            }
//         else {
//            System.out.println("Liner working");
////                throw new RuntimeException("User is already presents");
//
//        }
            if(userRepository.findByName("Chaitanya")==null){

                Role roleNormal1 = this.roleRepository.findByroleName("ROLE_NORMAL");
                User normalUser = new User();
                normalUser.setName("Chaitanya");

                normalUser.setEmail("chaitanya@gmail.com");
                normalUser.setAbout("hi im chaitanya");
                normalUser.setPassword(passwordEncoder.encode("test"));
                normalUser.addRole(roleNormal1);
                userRepository.save(normalUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
