package com.sale.service.impl;

import com.sale.dto.PromotionDTO;
import com.sale.mapper.PromotionMapper;
import com.sale.model.Promotion;
import com.sale.service.PromotionService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionMapper promotionMapper;

    @Override
    public void applyDiscount(PromotionDTO promotionDTO) {
        Promotion promotion = new Promotion();
        BeanUtils.copyProperties(promotionDTO, promotion);
        promotionMapper.insert(promotion);
    }

    @Override
    public void applyCoupon(PromotionDTO promotionDTO) {
        Promotion promotion = new Promotion();
        BeanUtils.copyProperties(promotionDTO, promotion);
        promotionMapper.insert(promotion);
    }
}