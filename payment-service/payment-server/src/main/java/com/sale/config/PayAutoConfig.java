package com.sale.config;

import com.alipay.api.*;
import com.sale.pay.alipay.AlipayProperties;
import com.sale.pay.alipay.AlipayStrategyHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(AlipayProperties.class)
public class PayAutoConfig {

    @Bean
    public AlipayClient alipayClient(AlipayProperties aliPayProperties) throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(aliPayProperties.getGatewayUrl());
        alipayConfig.setAppId(aliPayProperties.getAppId());
        alipayConfig.setPrivateKey(aliPayProperties.getMerchantPrivateKey());
        alipayConfig.setFormat(AlipayConstants.FORMAT_JSON);
        alipayConfig.setCharset(AlipayConstants.CHARSET_UTF8);
        alipayConfig.setAlipayPublicKey(aliPayProperties.getAlipayPublicKey());
        alipayConfig.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
        //构造client
        return new DefaultAlipayClient(alipayConfig);
    }


    @Bean(name = "alipay")
    public AlipayStrategyHandler alipayCall(AlipayClient alipayClient, AlipayProperties aliPayProperties) {
        return new AlipayStrategyHandler(alipayClient, aliPayProperties);
    }
}
