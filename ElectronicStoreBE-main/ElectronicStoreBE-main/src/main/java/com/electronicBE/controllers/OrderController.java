package com.electronicBE.controllers;

import com.electronicBE.dtos.CreateOrderRequest;
import com.electronicBE.dtos.OrderDto;
import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // create order



    @PostMapping("/")
    public ResponseEntity<OrderDto> CreateOrder(@RequestBody CreateOrderRequest createOrderRequest){

        OrderDto order = this.orderService.createOrder(createOrderRequest);

        return  new ResponseEntity<>(order, HttpStatus.OK);

    }


    // list orders of user

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDto>>GetOrderListOfUser(@PathVariable Long  userId){

        List<OrderDto> orderResponse = this.orderService.GetOrderListOfUser(userId);

        return  new ResponseEntity<>(orderResponse,HttpStatus.OK);

    }

    // get all orders

    @GetMapping("/")
    public ResponseEntity<PageableResponse<OrderDto>> GetAllOrders(
            @RequestParam( value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "billingName",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){

        PageableResponse<OrderDto> allOrders = this.orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(allOrders,HttpStatus.OK);

    }


    // update order

@PutMapping("/")
    public ResponseEntity<OrderDto>updateOrder(@RequestBody OrderDto orderDto){

        OrderDto updateOrder = this.orderService.updateOrder(orderDto.getOrderId(), orderDto);

        return new ResponseEntity<>(updateOrder,HttpStatus.OK);

    }





}
