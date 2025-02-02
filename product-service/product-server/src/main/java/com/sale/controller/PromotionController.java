package com.sale.controller;

import com.sale.dto.PromotionDTO;
import com.sale.service.PromotionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @DubboReference
    private PromotionService promotionService;

    @PostMapping("/apply-discount")
    public String applyDiscount(@RequestBody PromotionDTO promotionDTO) {
        promotionService.applyDiscount(promotionDTO);
        return "Discount applied successfully!";
    }

    @PostMapping("/apply-coupon")
    public String applyCoupon(@RequestBody PromotionDTO promotionDTO) {
        promotionService.applyCoupon(promotionDTO);
        return "Coupon applied successfully!";
    }
}