package com.manideep.order.controller;

import com.manideep.order.dto.OrderRequest;
import com.manideep.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
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
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
//    @TimeLimiter(name = "inventory")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest input) {
        try {
            return new ResponseEntity<>(
                    orderService.placeOrder(input),
                    HttpStatus.CREATED
            );
        } catch (Exception ex) {
            return new ResponseEntity<>(
                    ex.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public ResponseEntity<?> fallBackMethod(OrderRequest input, Throwable exception) {
        return new ResponseEntity<>(
                "Oops! something went wrong, try later after some time",
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }
//    @PostMapping("/placeOrder")
//    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
//    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest input) {
//       try {
//           return new ResponseEntity<>(
//                   orderService.placeOrder(input),
//                   HttpStatus.CREATED
//           );
//       }
//       catch (Exception ex){
//           return new ResponseEntity<>(
//                  ex.getMessage(),
//                   HttpStatus.BAD_REQUEST
//           );
//       }
//
//    }
//
//    public ResponseEntity<?> fallBackMethod(OrderRequest input, Throwable exception){
//        return new ResponseEntity<>(
//                "Oops! something went wrong, try later after some time",
//                HttpStatus.SERVICE_UNAVAILABLE
//        );
//    }



    @GetMapping("/getAll")
    public ResponseEntity<?> getAllOrders(){
        return new ResponseEntity<>(
                orderService.allOrders(),
                HttpStatus.OK
        );
    }
}
