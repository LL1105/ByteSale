package com.sale.controller;

import com.sale.dto.*;
import com.sale.response.ApiResponse;
import com.sale.service.PayService;
import com.sale.vo.NotifyVo;
import com.sale.vo.PayBillVo;
import com.sale.vo.TradeCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 支付
     * @param payDto 支付参数
     * @return 支付地址
     */
    @PostMapping(value = "/common/pay")
    public ApiResponse<String> commonPay(@Valid @RequestBody PayDto payDto) {
        return ApiResponse.ok(payService.commonPay(payDto));
    }

    /**
     * 支付后回调通知
     * @param notifyDto 支付回调参数
     * @return 回调结果
     */
    @PostMapping(value = "/notify")
    public ApiResponse<NotifyVo> notify(@Valid @RequestBody NotifyDto notifyDto) {
        return ApiResponse.ok(payService.notify(notifyDto));
    }

    /**
     * 支付状态查询
     * @param tradeCheckDto 查询参数
     * @return 支付结果
     */
    @PostMapping(value = "/trade/check")
    public ApiResponse<TradeCheckVo> tradeCheck(@Valid @RequestBody TradeCheckDto tradeCheckDto) {
        return ApiResponse.ok(payService.tradeCheck(tradeCheckDto));
    }

    /**
     * 退款
     * @param refundDto 退款参数
     * @return 结果
     */
    @PostMapping(value = "/refund")
    public ApiResponse<String> refund(@Valid @RequestBody RefundDto refundDto) {
        return ApiResponse.ok(payService.refund(refundDto));
    }

    /**
     * 账单详情查询
     * @param payBillDto 账单查询参数
     * @return 账单详情
     */
    @PostMapping(value = "/detail")
    public ApiResponse<PayBillVo> detail(@Valid @RequestBody PayBillDto payBillDto) {
        return ApiResponse.ok(payService.detail(payBillDto));
    }
}
