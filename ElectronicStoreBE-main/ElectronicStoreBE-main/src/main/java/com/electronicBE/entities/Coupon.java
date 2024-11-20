package com.electronicBE.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity (name = "coupon")
@Data
public class Coupon {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String code;

    private int discount;

    private boolean active;


    @OneToMany(fetch = FetchType.LAZY , mappedBy = "coupon")

    private Set<Order> order;


}
