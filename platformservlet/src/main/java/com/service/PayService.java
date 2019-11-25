package com.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.utils.alipay.AlipayTools;
import com.utils.alipay.OrderInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PayService {

    @Value("application.url")
    private String url;

    public void pay(OrderInfo orderInfo) {
        AlipayTools.pay(orderInfo, url);
    }

}


