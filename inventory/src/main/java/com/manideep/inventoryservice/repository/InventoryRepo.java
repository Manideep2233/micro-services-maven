package com.manideep.inventoryservice.repository;

import com.manideep.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {

    Optional<Inventory> findBySkuCode(String skuCode);

//    @Query(value = "select * from t_inventory where skucode in ({$skuCodes})",nativeQuery = true)
//    List<Inventory> findBySkuCodeIn(@Param("skuCodes") List<String> skuCodes);


    @Query(value = "SELECT * FROM t_inventory WHERE sku_code IN (:skuCodes)", nativeQuery = true)
    List<Inventory> findBySkuCodeIn(@Param("skuCodes") List<String> skuCodes);

}
