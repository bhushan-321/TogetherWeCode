package com.electronicBE.services.impl;

import com.electronicBE.dtos.CouponDto;
import com.electronicBE.dtos.CreateOrderRequest;
import com.electronicBE.dtos.OrderDto;
import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.entities.*;
import com.electronicBE.exceptions.BadApiException;
import com.electronicBE.exceptions.ResourceNotFoundException;
import com.electronicBE.helper.Helper;
import com.electronicBE.repositories.CartRepository;
import com.electronicBE.repositories.OrderRepository;
import com.electronicBE.repositories.ProductRepo;
import com.electronicBE.repositories.UserRepository;
import com.electronicBE.services.CouponService;
import com.electronicBE.services.OrderService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CouponService couponService;


    @Override
    public OrderDto createOrder(CreateOrderRequest request) {

        User user = this.userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = this.cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("User cart not found"));

        if (cart.getItems().size() <= 0) {

            throw new BadApiException("Cart items is empty");

        }
        String date = new SimpleDateFormat("dd/MM/YYYY, hh.mm.ss.aa").format(Calendar.getInstance().getTime());

        Order order = Order.builder()
                .billingAddress(request.getBillingAddress())
                .billingName(request.getBillingName())
                .orderedDate(new Date())
                .billingPhone(request.getBillingPhone())
                .user(user)
                .orderId(UUID.randomUUID().toString())
                .orderStatus(request.getOrderStatus())
                .paymentStatus(request.getPaymentStatus())
                .deliveredDate(null)

                .build();
        AtomicReference<Integer> totalAmount = new AtomicReference<>(0);


        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            OrderItem orderItem1 = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .order(order)
                    .product(product)
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .build();
            totalAmount.set(totalAmount.get() + orderItem1.getTotalPrice());

            product.setQuantity(product.getQuantity() - orderItem1.getQuantity());
            this.productRepo.save(product);

            return orderItem1;
        }).collect(Collectors.toList());

        Integer finalAmount = totalAmount.get();

        if (request.getCouponCode() != null && !request.getCouponCode().isEmpty()) {
            CouponDto couponDto = this.couponService.getCouponByCode(request.getCouponCode());


            Coupon coupon = mapper.map(couponDto, Coupon.class);

            order.setCoupon(coupon);
            double discountedAmount = this.couponService.applyCoupon(request.getCouponCode(), totalAmount.get());

            finalAmount = (int) Math.round(discountedAmount);

        }

        order.setOrderAmount(finalAmount);
        order.setOrderItems(orderItems);


        cart.getItems().clear();
        cartRepository.save(cart);
        Order SavedOrder = this.orderRepository.save(order);


        return mapper.map(SavedOrder, OrderDto.class);
    }

    @Override
    public OrderDto getOrderById(String orderId) {

        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with given id"));
        return mapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> GetOrderListOfUser(Long userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));


        List<Order> userList = this.orderRepository.findByUser(user);

        List<OrderDto> orderDtos = userList.stream().map((object) -> mapper.map(object, OrderDto.class)).collect(Collectors.toList());


        return orderDtos;


    }

    @Override
    public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy)).descending() : (Sort.by(sortBy)).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> orderList = this.orderRepository.findAll(pageable);
        PageableResponse<OrderDto> pageableResponse = Helper.getPageableResponse(orderList, OrderDto.class);


        return pageableResponse;
    }

    @Override
    public OrderDto updateOrder(String orderId, OrderDto orderDto) {

        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with given id"));

        order.setOrderStatus(orderDto.getOrderStatus());
        order.setRazorPayOrderId(orderDto.getRazorPayOrderId());
        order.setPaymentStatus(orderDto.getPaymentStatus());

        Order savedOrder = orderRepository.save(order);


        return mapper.map(savedOrder, OrderDto.class);
    }


}
