package com.electronicBE.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private String orderId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate;
    private Date deliveredDate;
    //private UserDto user;

    private String  paymentId;

    private String razorPayOrderId;
    private List<OrderItemDto> orderItems = new ArrayList<OrderItemDto>();

    private CouponDto coupon;


}
