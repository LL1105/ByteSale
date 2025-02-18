package com.sale.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sale.dto.Order;
import com.sale.dto.OrderFormDTO;

public interface OrderService extends IService<Order> {
    //创建订单
    Long createOrder(OrderFormDTO orderFormDTO);
    //支付成功
    void markOrderPaySuccess(Long orderId);
    //取消订单
    void cancelOrder(Long orderId);
}