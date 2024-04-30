package com.manideep.order.service;


import com.manideep.order.dto.OrderItemsDto;
import com.manideep.order.dto.OrderRequest;
import com.manideep.order.dto.StockAvailabilityResponse;
import com.manideep.order.model.Order;
import com.manideep.order.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest input) throws Exception {

        // before placing the order we have to check if they are in inventory or not
       List<String> codes = input.getOrderItems()
               .stream().map(OrderItemsDto::getSkuCode).toList();

       // using webClient for communication
        System.out.println(codes);
        StockAvailabilityResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory/getAvailabilityInfo",
                        uriBuilder -> uriBuilder.queryParam("skuCodes",codes).build())
                .retrieve()
                .bodyToMono(StockAvailabilityResponse[].class)
                .block();
        System.out.println(inventoryResponseArray.length);

//        boolean allProducts = Arrays.stream(inventoryResponseArray)
//                .allMatch(StockAvailabilityResponse::isPresent);
        boolean allProducts = true;
        for(StockAvailabilityResponse response:inventoryResponseArray){
            allProducts = allProducts && response.isPresent();
        }

        if(inventoryResponseArray.length==codes.size() && allProducts){
            String on = UUID.randomUUID().toString();
            List<Order> items = input.getOrderItems().stream()
                    .map(x-> new Order(null,on,x.getSkuCode(),x.getQuantity(),x.getPrice())
                    ).toList();

            orderRepo.saveAll(items);

            // in real time situation, we can send a put request to update inventory,
//             we don't need to wait for it's response.
            return "Order Placed Successfully";
        }
        else {
            throw new Exception("All items are not present in the Inventory");
        }


    }


    public List<Order> allOrders(){
        return orderRepo.findAll();
    }
}
