package com.electronicBE.dtos;

import com.electronicBE.Validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {


    private Long userId;

    @Size(min = 3 ,max = 25,message = "Invalid name")
    private String name;

//    @Email(message = "Invalid User email")
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{1,63}$",message = "Invalid user email !!")
    @NotBlank(message = "Email required !!")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(min = 4 ,max = 6 , message = "Invalid gender!!")
    private String gender;

    @NotBlank(message = "Write something about yourself !! ")
    private String about;

//    @ImageNameValid
    private String imageName;

    private Set<RoleDto> roles=new HashSet<>();
}
