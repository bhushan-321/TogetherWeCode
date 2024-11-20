package com.electronicBE.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {


    private String jwtToken;

    private UserDto userDto;

    private boolean tokenExpired;
}
