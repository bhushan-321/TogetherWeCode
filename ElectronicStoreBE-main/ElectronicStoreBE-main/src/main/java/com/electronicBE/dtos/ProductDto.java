package com.electronicBE.dtos;

import com.electronicBE.entities.Category;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDto {


    private Long productId;

    private String title;

    private int price;

    private String description;

    private int discountedPrice;

    private int quantity;

    private String productImage;

    private String addedDate;

    private Boolean  live;

    private Boolean inStocked;
    



    private CategoryDto category;


}
