package com.electronicBE.services;

import com.electronicBE.dtos.CouponDto;
import com.electronicBE.entities.Coupon;

import java.util.List;

public interface CouponService {

    CouponDto createCoupon(CouponDto couponDto);

    List<CouponDto>getAllCoupons();

    CouponDto getCouponByCode(String couponCode);

    List<CouponDto>getAllActivatedCoupons();

    void deleteCoupon(int couponId);

    CouponDto updatedCoupon(CouponDto couponDto, int couponId);

    double applyCoupon(String couponCode,int orderAmount);
}
