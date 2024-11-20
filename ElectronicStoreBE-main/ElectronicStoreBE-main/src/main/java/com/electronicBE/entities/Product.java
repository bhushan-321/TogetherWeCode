package com.electronicBE.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String title;

    private int price;

    @Column(length = 100000)
    private String description;

    private int discountedPrice;

    private int quantity;

    private String productImage;

    private String addedDate;


    private Boolean live;

    private Boolean inStocked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;


}
