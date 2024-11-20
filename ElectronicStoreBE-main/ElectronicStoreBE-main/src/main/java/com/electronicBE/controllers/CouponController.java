package com.electronicBE.controllers;


import com.electronicBE.dtos.CouponDto;
import com.electronicBE.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coupons")
public class CouponController {


    @Autowired
    private CouponService couponService;

    @PostMapping("/")

    public ResponseEntity<CouponDto> createCoupon(@RequestBody CouponDto couponDto) {

        CouponDto coupon = this.couponService.createCoupon(couponDto);

        return new ResponseEntity<>(coupon, HttpStatus.CREATED);
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<CouponDto> updatedCoupon(@RequestBody CouponDto couponDto, @PathVariable("couponId") int couponId) {

        CouponDto coupon = this.couponService.updatedCoupon(couponDto, couponId);

        return new ResponseEntity<>(coupon, HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<List<CouponDto>> getAllCoupon() {
        List<CouponDto> allCoupons = this.couponService.getAllCoupons();

        return new ResponseEntity<>(allCoupons, HttpStatus.OK);
    }

    @GetMapping("/active/list")
    public ResponseEntity<List<CouponDto>> getAllActiveCoupon() {
        List<CouponDto> allCoupons = this.couponService.getAllActivatedCoupons();

        return new ResponseEntity<>(allCoupons, HttpStatus.OK);
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable("couponId") int couponId) {

        this.couponService.deleteCoupon(couponId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CouponDto> getCouponByName(@PathVariable("code") String code) {
        CouponDto coupon = this.couponService.getCouponByCode(code);

        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }


    @PostMapping("/applyCoupon/{couponCode}/{orderAmount}")
    public ResponseEntity<Map<String, Object>> applyCoupon(@PathVariable("couponCode") String couponCode, @PathVariable("orderAmount") int orderAmount) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            double discountedPrice = this.couponService.applyCoupon(couponCode, orderAmount);
            response.put("discountedTotal", discountedPrice);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put(e.getMessage(), Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }


    }


}
