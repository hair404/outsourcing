package com.utils.alipay;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayeeInfo {

    @JSONField(name = "identity")
    private String identity;

    @JSONField(name = "identity_type")
    private String identityType = "ALIPAY_USER_ID";

    public void setAccount(String account) {
        identityType = "ALIPAY_USER_ID";
        identity = account;
    }

    public void setUID(String uid) {
        identityType = "ALIPAY_LOGON_ID";
        identity = uid;
    }

}
