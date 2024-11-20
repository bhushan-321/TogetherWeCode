package com.electronicBE.services.impl;

import com.electronicBE.dtos.CouponDto;
import com.electronicBE.entities.Coupon;
import com.electronicBE.exceptions.BadApiException;
import com.electronicBE.exceptions.ResourceNotFoundException;
import com.electronicBE.repositories.CouponRepository;
import com.electronicBE.services.CouponService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {


    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CouponDto createCoupon(CouponDto couponDto) {
        Coupon coupon = mapper.map(couponDto, Coupon.class);
        Coupon savedCoupon = this.couponRepository.save(coupon);
        CouponDto savedCouponDto = mapper.map(savedCoupon, CouponDto.class);
        return savedCouponDto;
    }

    @Override
    public List<CouponDto> getAllCoupons() {
        List<Coupon> listOfCoupons = this.couponRepository.findAll();

        List<CouponDto> getListOfCouponDto = listOfCoupons.stream().map((object) -> mapper.map(object, CouponDto.class)).collect(Collectors.toList());
        return getListOfCouponDto;
    }

    @Override
    public CouponDto getCouponByCode(String couponCode) {
        Coupon coupon = this.couponRepository.findByCode(couponCode).orElseThrow(() -> new ResourceNotFoundException("The coupon you entered does not exist. Please check the code and try again."));
        return mapper.map(coupon, CouponDto.class);
    }

    @Override
    public List<CouponDto> getAllActivatedCoupons() {
        List<Coupon> getAllActiveCoupons = this.couponRepository.findByActive(true);

        List<CouponDto> getListOfCouponDto = getAllActiveCoupons.stream().map((object) -> mapper.map(object, CouponDto.class)).collect(Collectors.toList());
        return getListOfCouponDto;
    }

    @Override
    public void deleteCoupon(int couponId) {

        this.couponRepository.deleteById(couponId);

    }

    @Override
    public CouponDto updatedCoupon(CouponDto couponDto, int couponId) {

        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new ResourceNotFoundException("Coupon not found with given id !!!"));
        coupon.setActive(couponDto.isActive());
        coupon.setCode(couponDto.getCode());
        coupon.setDiscount(couponDto.getDiscount());
        Coupon savedCoupon = this.couponRepository.save(coupon);
        CouponDto savedCouponDto = this.mapper.map(savedCoupon, CouponDto.class);
        return savedCouponDto;
    }

    @Override
    public double applyCoupon(String couponCode, int orderAmount) {

        Coupon coupon = this.couponRepository.findByCode(couponCode).orElseThrow(() -> new ResourceNotFoundException("Coupon not found with given name"));

        if (!coupon.isActive()) {

            throw new BadApiException("Coupon is inActive");

        }

        double discountedAmount=(coupon.getDiscount()/100.0) * orderAmount;

        double discountedTotal=orderAmount-discountedAmount;

        return discountedTotal;
    }
}
