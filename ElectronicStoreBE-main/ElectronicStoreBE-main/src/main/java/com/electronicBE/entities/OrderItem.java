package com.electronicBE.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orderItems")
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @OneToOne
    @JoinColumn(name = "product_Id")
    private Product product;

    private int quantity;

    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


}

