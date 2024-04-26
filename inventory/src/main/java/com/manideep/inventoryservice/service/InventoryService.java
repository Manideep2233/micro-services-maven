package com.manideep.inventoryservice.service;

import com.manideep.inventoryservice.dto.StockAvailabilityResponse;
import com.manideep.inventoryservice.dto.StockInput;
import com.manideep.inventoryservice.model.Inventory;
import com.manideep.inventoryservice.repository.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    public boolean isStockAvailable(String skuCode){
        return inventoryRepo.findBySkuCode(skuCode).isPresent();
    }

    public String addStock(StockInput input){
        Optional<Inventory> inventory = inventoryRepo.findBySkuCode(input.getSkuCode());
        if (inventory.isPresent()){
            Long prev = inventory.get().getQuantity();
            inventory.get().setQuantity(prev+input.getQuantity());
            inventoryRepo.save(inventory.get());
            return "Updated Stock Successfully";
        }
        else {
            Inventory newInventory = new Inventory();
            newInventory.setSkuCode(input.getSkuCode());
            newInventory.setQuantity(input.getQuantity());
            inventoryRepo.save(newInventory);
            return "New Product added to Inventory";
        }
    }



    public List<StockAvailabilityResponse> getAvailabilityInfo(List<String> skuCodes){
        System.out.println(skuCodes);
        List<Inventory> inventoryList = inventoryRepo.findBySkuCodeIn(skuCodes);
        System.out.println(inventoryList.size());
        return inventoryList.stream().map(inventory -> StockAvailabilityResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isPresent(inventory.getQuantity()>0)
                .build()
        ).toList();
    }

}
