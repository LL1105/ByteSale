package com.sale.service.impl;

import com.sale.dto.InventoryDTO;
import com.sale.mapper.InventoryMapper;
import com.sale.model.Inventory;
import com.sale.service.InventoryService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public InventoryDTO getInventory(Long productId) {
        Inventory inventory = inventoryMapper.selectByProductId(productId);
        InventoryDTO inventoryDTO = new InventoryDTO();
        BeanUtils.copyProperties(inventory, inventoryDTO);
        return inventoryDTO;
    }

    @Override
    public void reduceInventory(Long productId, Integer quantity) {
        inventoryMapper.reduceStock(productId, quantity);
    }
}