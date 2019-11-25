package com.controller;

import com.service.PayService;
import com.type.PayState;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Resource
    private PayService payService;

    @PostMapping("/callback")
    public void callback(@RequestParam(name = "out_trade_no") String outTradeNo,
                         @RequestParam(name = "trade_status") String tradeStatus) {
        //TODO 回调数据真实性验证
        switch (tradeStatus){
            case "TRADE_SUCCESS":
                payService.updatePayState(outTradeNo, PayState.DONE);
                break;
            case "TRADE_CLOSED":
                payService.updatePayState(outTradeNo,PayState.FAILED);
                break;
            default:
                System.out.println(tradeStatus);
        }
    }
}
