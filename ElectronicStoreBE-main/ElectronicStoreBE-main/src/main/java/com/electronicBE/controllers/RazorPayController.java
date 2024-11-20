package com.electronicBE.controllers;


import com.electronicBE.dtos.OrderDto;
import com.electronicBE.exceptions.ResourceNotFoundException;
import com.electronicBE.repositories.OrderRepository;
import com.electronicBE.services.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/razorpay")
public class RazorPayController {


    @Value("${razorPayKeyId}")
    private String keyId;

    @Value("${razorPayKeySecret}")
    private String keySecret;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;


    @PostMapping("/initiatePayment/{orderId}")
    public ResponseEntity<Map<String, Object>> initiatePayment(@PathVariable(value = "orderId") String orderId) {

//        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order is not present by given id"));

        OrderDto orderDto = orderService.getOrderById(orderId);

        Map<String, Object> resp = new LinkedHashMap<>();

        try {
            RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);

            JSONObject orderRequest = new JSONObject();

            orderRequest.put("amount", orderDto.getOrderAmount() * 500);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "receipt");

            Order order = razorpayClient.orders.create(orderRequest);

            orderDto.setRazorPayOrderId(order.get("id"));
            orderDto.setPaymentStatus(order.get("status").toString().toUpperCase());
            this.orderService.updateOrder(orderDto.getOrderId(), orderDto);

            resp.put("orderId", orderDto.getOrderId());
            resp.put("razorPayOrderId", orderDto.getRazorPayOrderId());
            resp.put("amount", orderDto.getOrderAmount());
            resp.put("status", orderDto.getPaymentStatus());

            return new ResponseEntity<>(resp, HttpStatus.CREATED);

        } catch (RazorpayException e) {
            e.printStackTrace();
            resp.put("Error while creating payment", Boolean.FALSE);
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        }


    }


    @PostMapping("/verify-payment/{orderId}")
    public ResponseEntity<Map<String, Object>> verifyPayment(@RequestBody Map<String, Object> data, @PathVariable("orderId") String orderId) {

        String razorpayPaymentId = data.get("razorpay_payment_id").toString();
        String razorpayOrderId = data.get("razorpay_order_id").toString();
        String razorpaySignature = data.get("razorpay_signature").toString();

        Map<String, Object> resp = new LinkedHashMap<>();

        try {
            RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", razorpayOrderId);
            options.put("razorpay_payment_id", razorpayPaymentId
            );
            options.put("razorpay_signature", razorpaySignature);

            boolean b = Utils.verifyPaymentSignature(options, keySecret);
            if (b) {
                resp.put("success", Boolean.TRUE);
                resp.put("message", "Payment verify successfully");
                OrderDto orderDto = this.orderService.getOrderById(orderId);
                orderDto.setPaymentStatus("PAID");
                orderDto.setRazorPayOrderId(razorpayOrderId);
                orderService.updateOrder(orderId,orderDto);
                return new ResponseEntity<>(resp, HttpStatus.OK);
            } else {
                resp.put("success", Boolean.FALSE);
                resp.put("message", "Payment verify failed");
                return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }


}
