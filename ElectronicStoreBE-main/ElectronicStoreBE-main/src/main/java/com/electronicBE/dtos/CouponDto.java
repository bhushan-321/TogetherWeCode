package com.electronicBE.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CouponDto {

    private int id;

    private String code;

    private int discount;

    private boolean active;
}
