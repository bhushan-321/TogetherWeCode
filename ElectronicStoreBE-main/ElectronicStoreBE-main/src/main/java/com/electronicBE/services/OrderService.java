package com.electronicBE.services;

import com.electronicBE.dtos.CreateOrderRequest;
import com.electronicBE.dtos.OrderDto;
import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.entities.Order;

import java.util.List;

public interface OrderService {


    OrderDto createOrder(CreateOrderRequest request);


    // get order by id


    OrderDto getOrderById(String orderId);


    // get order list of user


    List<OrderDto> GetOrderListOfUser(Long userId);

    // get all Order List

    PageableResponse<OrderDto> getAllOrders(int pageNumber,int  pageSize,String sortBy,String sortDir);


    OrderDto updateOrder(String orderId, OrderDto order);

}
