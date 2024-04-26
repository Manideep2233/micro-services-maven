package com.manideep.inventoryservice.controller;

import com.manideep.inventoryservice.dto.StockInput;
import com.manideep.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/isAvailable/{sc}")
    public ResponseEntity<?> isStockAvailable(@PathVariable("sc") String skuCode){
        return new ResponseEntity<>(
                inventoryService.isStockAvailable(skuCode),
                HttpStatus.OK
        );
    }

    @PostMapping("/addStock")
    public ResponseEntity<?> addStock(@RequestBody StockInput input){
        return new ResponseEntity<>(
                inventoryService.addStock(input),
                HttpStatus.OK
        );
    }


    @GetMapping("/getAvailabilityInfo")
    public ResponseEntity<?> getAvailabilityInfo(@RequestParam List<String> skuCodes){
        return new ResponseEntity<>(
                inventoryService.getAvailabilityInfo(skuCodes),
                HttpStatus.OK
        );
    }



}
