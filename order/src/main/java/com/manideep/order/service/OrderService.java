package com.manideep.order.service;


import com.manideep.order.dto.OrderRequest;
import com.manideep.order.model.Order;
import com.manideep.order.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    public String placeOrder(OrderRequest input){

        // before placing the order we have to check if they are in inventory or not
        String on = UUID.randomUUID().toString();
        List<Order> items = input.getOrderItems().stream()
                .map(x-> new Order(null,on,x.getSkuCode(),x.getQuantity(),x.getPrice())
                ).toList();
        orderRepo.saveAll(items);
        return "Order Placed Successfully";
    }


    public List<Order> allOrders(){
        return orderRepo.findAll();
    }
}
