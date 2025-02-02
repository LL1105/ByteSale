package com.sale.controller;

import com.sale.dto.InventoryDTO;
import com.sale.service.InventoryService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @DubboReference
    private InventoryService inventoryService;

    @GetMapping("/{productId}")
    public InventoryDTO getInventory(@PathVariable Long productId) {
        return inventoryService.getInventory(productId);
    }

    @PostMapping("/reduce")
    public String reduceInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
        inventoryService.reduceInventory(productId, quantity);
        return "Inventory reduced successfully!";
    }
}