package com.sale.controller;


import com.sale.dto.OrderFormDTO;
import com.sale.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @ApiOperation("生成订单")
    @PostMapping("/create")
    public Long createOrder(@RequestBody OrderFormDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @ApiOperation("标记订单已支付")
    @PutMapping("{orderID}")
    public void markOrderPaySuccess(@PathVariable("orderId") Long orderId) {
        orderService.markOrderPaySuccess(orderId);
    }
}
