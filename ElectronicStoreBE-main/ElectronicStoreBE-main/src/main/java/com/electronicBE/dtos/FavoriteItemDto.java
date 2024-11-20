package com.electronicBE.dtos;

import com.electronicBE.entities.Favorite;
import com.electronicBE.entities.Product;
import lombok.*;

import javax.persistence.*;



@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteItemDto {


    private Long id;


    private ProductDto product;



}
