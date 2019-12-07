package com.utils.alipay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayInfo {

    //订单号
    private String outTradeNo;

    //销售产品码
    private String productCode = "FAST_INSTANT_TRADE_PAY";

    //价格
    private double totalAmount;

    //订单标题
    private String subject;

    public String toJson() {
        return "{"
                + "\"out_trade_no\":\"" + outTradeNo + "\","
                + "\"product_code\":\"" + productCode + "\","
                + "\"total_amount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\""
                + "}";
    }
}
