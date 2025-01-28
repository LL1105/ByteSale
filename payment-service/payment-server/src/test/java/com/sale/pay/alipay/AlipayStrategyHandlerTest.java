package com.sale.pay.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.sale.enums.PayBillStatus;
import com.sale.pay.TradeResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AlipayStrategyHandlerTest {

    @Mock
    private AlipayClient alipayClient;

    @InjectMocks
    private AlipayStrategyHandler alipayStrategyHandler;

    /**
     * 测试查询交易成功的情况，验证返回的 TradeResult 是否正确。
     *
     * @throws Exception 可能抛出的异常
     */
    @Test
    public void queryTrade_SuccessfulResponse_ShouldReturnTradeResult() throws Exception {
        // 模拟成功的支付宝交易查询响应
        AlipayTradeQueryResponse response = mock(AlipayTradeQueryResponse.class);
        when(response.isSuccess()).thenReturn(true);
        when(response.getBody()).thenReturn("{\"alipay_trade_query_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"out_trade_no\":\"12345\",\"total_amount\":\"100.00\",\"trade_status\":\"TRADE_SUCCESS\"}}");
        when(alipayClient.execute(any(AlipayTradeQueryRequest.class))).thenReturn(response);

        // 调用查询交易方法并获取结果
        TradeResult tradeResult = alipayStrategyHandler.queryTrade("12345");

        // 验证返回的结果是否符合预期
        assertTrue(tradeResult.isSuccess());
        assertEquals("12345", tradeResult.getOutTradeNo());
        assertEquals(new BigDecimal("100.00"), tradeResult.getTotalAmount());
        assertEquals(PayBillStatus.PAY.getCode(), tradeResult.getPayBillStatus());
    }

    /**
     * 测试查询交易失败的情况，验证返回的 TradeResult 是否表示失败。
     *
     * @throws Exception 可能抛出的异常
     */
    @Test
    public void queryTrade_FailedResponse_ShouldReturnFailedTradeResult() throws Exception {
        // 模拟失败的支付宝交易查询响应
        AlipayTradeQueryResponse response = mock(AlipayTradeQueryResponse.class);
        when(response.isSuccess()).thenReturn(false);
        when(alipayClient.execute(any(AlipayTradeQueryRequest.class))).thenReturn(response);

        // 调用查询交易方法并获取结果
        TradeResult tradeResult = alipayStrategyHandler.queryTrade("12345");

        // 验证返回的结果是否表示失败
        assertFalse(tradeResult.isSuccess());
    }

    /**
     * 测试查询交易时发生异常的情况，验证返回的 TradeResult 是否表示失败。
     *
     * @throws Exception 可能抛出的异常
     */
    @Test
    public void queryTrade_ExceptionThrown_ShouldReturnFailedTradeResult() throws Exception {

        // 模拟执行支付宝交易查询时抛出异常
        when(alipayClient.execute(any(AlipayTradeQueryRequest.class))).thenThrow(new AlipayApiException("Error"));

        // 调用查询交易方法并获取结果
        TradeResult tradeResult = alipayStrategyHandler.queryTrade("12345");

        // 验证返回的结果是否表示失败
        assertFalse(tradeResult.isSuccess());
    }
}
