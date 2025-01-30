package com.sale.service;


import cc.siyecao.uid.core.UidGenerator;
import com.sale.dto.PayDto;
import com.sale.enums.PayBillStatus;
import com.sale.mapper.PayBillMapper;
import com.sale.model.PayBill;
import com.sale.pay.PayResult;
import com.sale.pay.PayStrategyHandler;
import com.sale.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PayServiceTest {

    @Mock
    private PayBillMapper payBillMapper;

    @Mock
    private Map<String, PayStrategyHandler> payStrategyHandlerMap;

    @Mock
    private PayStrategyHandler payStrategyHandler;

    @Mock
    private UidGenerator uidGenerator;

    @InjectMocks
    private PayService payService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void commonPay_OrderExistsAndStatusNotNoPay_ThrowsException() {
        PayDto payDto = new PayDto();
        payDto.setOrderNumber(String.valueOf(12345L));
        payDto.setChannel("alipay");

        PayBill existingPayBill = new PayBill();
        existingPayBill.setOutOrderNo("12345");
        existingPayBill.setPayBillStatus(PayBillStatus.PAY.getCode());

        when(payBillMapper.selectOne(any())).thenReturn(existingPayBill);

        assertThrows(RuntimeException.class, () -> payService.commonPay(payDto));
    }

    @Test
    public void commonPay_OrderDoesNotExistOrStatusIsNoPay_InsertsPayBill() {
        PayDto payDto = new PayDto();
        payDto.setOrderNumber(String.valueOf(12345L));
        payDto.setChannel("alipay");
        payDto.setPrice(BigDecimal.valueOf(100.00));
        payDto.setSubject("Test Subject");
        payDto.setNotifyUrl("http://notify.url");
        payDto.setReturnUrl("http://return.url");

        when(payBillMapper.selectOne(any())).thenReturn(null);
        when(payStrategyHandlerMap.get("alipay")).thenReturn(payStrategyHandler);
        when(payStrategyHandler.pay(anyString(), any(BigDecimal.class), anyString(), anyString(), anyString()))
                .thenReturn(new PayResult(true, "Success"));
        when(uidGenerator.getUID()).thenReturn(1L);

        String result = payService.commonPay(payDto);

        assertEquals("Success", result);
        verify(payBillMapper, times(1)).insert(any(PayBill.class));
    }

    @Test
    public void commonPay_PaySuccess_InsertsPayBill() {
        PayDto payDto = new PayDto();
        payDto.setOrderNumber(String.valueOf(12345L));
        payDto.setChannel("alipay");
        payDto.setPrice(BigDecimal.valueOf(100.00));
        payDto.setSubject("Test Subject");
        payDto.setNotifyUrl("http://notify.url");
        payDto.setReturnUrl("http://return.url");

        when(payBillMapper.selectOne(any())).thenReturn(null);
        when(payStrategyHandlerMap.get("alipay")).thenReturn(payStrategyHandler);
        when(payStrategyHandler.pay(anyString(), any(BigDecimal.class), anyString(), anyString(), anyString()))
                .thenReturn(new PayResult(true, "Success"));
        when(uidGenerator.getUID()).thenReturn(1L);

        String result = payService.commonPay(payDto);

        assertEquals("Success", result);
        verify(payBillMapper, times(1)).insert(any(PayBill.class));
    }

    @Test
    public void commonPay_PayFailure_DoesNotInsertPayBill() {
        PayDto payDto = new PayDto();
        payDto.setOrderNumber(String.valueOf(12345L));
        payDto.setChannel("alipay");
        payDto.setPrice(BigDecimal.valueOf(100.00));
        payDto.setSubject("Test Subject");
        payDto.setNotifyUrl("http://notify.url");
        payDto.setReturnUrl("http://return.url");

        when(payBillMapper.selectOne(any())).thenReturn(null);
        when(payStrategyHandlerMap.get("alipay")).thenReturn(payStrategyHandler);
        when(payStrategyHandler.pay(anyString(), any(BigDecimal.class), anyString(), anyString(), anyString()))
                .thenReturn(new PayResult(false, "Failure"));

        String result = payService.commonPay(payDto);

        assertEquals("Failure", result);
        verify(payBillMapper, times(0)).insert(any(PayBill.class));
    }
}
