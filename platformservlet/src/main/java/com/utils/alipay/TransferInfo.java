package com.utils.alipay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class TransferInfo {

    @JSONField(name = "out_biz_no")
    private String outBizNo;

    @JSONField(name = "trans_amount")
    private String transAmount;

    @JSONField(name = "product_code")
    private String productCode = "TRANS_ACCOUNT_NO_PWD";

    @JSONField(name = "biz_scene")
    private String bizScene = "DIRECT_TRANSFER";

    @JSONField(name = "payee_info")
    private PayeeInfo payeeInfo;

    /**
     * 随机生成订单ID
     */
    public void generateRandomOutBizNo() {
        this.outBizNo = UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 通过支付宝登陆身份转账
     *
     * @param account 邮箱/手机
     */
    public void setAccount(String account) {
        this.payeeInfo = new PayeeInfo();
        this.payeeInfo.setAccount(account);
    }

    /**
     * 通过支付宝UID转账
     *
     * @param uid 支付宝UID
     */
    public void setUID(String uid) {
        this.payeeInfo = new PayeeInfo();
        this.payeeInfo.setUID(uid);
    }


    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
