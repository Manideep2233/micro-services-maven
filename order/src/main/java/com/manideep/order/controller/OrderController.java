package com.manideep.order.controller;

import com.manideep.order.dto.OrderRequest;
import com.manideep.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest input) throws Exception {
       try {
           return new ResponseEntity<>(
                   orderService.placeOrder(input),
                   HttpStatus.CREATED
           );
       }
       catch (Exception ex){
           return new ResponseEntity<>(
                  ex.getMessage(),
                   HttpStatus.BAD_REQUEST
           );
       }

    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllOrders(){
        return new ResponseEntity<>(
                orderService.allOrders(),
                HttpStatus.OK
        );
    }
}
