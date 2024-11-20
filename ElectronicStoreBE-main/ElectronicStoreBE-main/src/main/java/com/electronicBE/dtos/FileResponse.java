package com.electronicBE.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse {


    private String Filename;

    private String message;

    private boolean success;

    private HttpStatus httpStatus;

}
