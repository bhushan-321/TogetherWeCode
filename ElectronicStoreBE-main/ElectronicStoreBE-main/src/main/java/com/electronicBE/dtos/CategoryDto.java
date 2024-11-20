package com.electronicBE.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CategoryDto {

    private Long categoryId;

    private String title;

    private String description;

    private String coverImage;

  

}
