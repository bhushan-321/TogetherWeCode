package com.electronicBE.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderItemDto {



    private Long orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;


}
