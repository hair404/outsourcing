package com.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class MessageTools {

    private static String ACCESS_ID = "LTAI4FpwjngawSEnW7fe5NxX";
    private static String ACCESS_SECRET = "PyQPK27YcIuiIsyQV5fJsI9XPrLbqb";
    private static DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_ID, ACCESS_SECRET);
    private static IAcsClient client = new DefaultAcsClient(profile);

    /**
     * 发送验证码短信
     * @param phoneNumber 手机号码
     * @param verifyCode 验证码
     * @throws ClientException
     */
    public static void sendVerifyCode(String phoneNumber, String verifyCode) throws ClientException {
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "人力外包服务平台");
        request.putQueryParameter("TemplateCode", "SMS_177554785");
        request.putQueryParameter("TemplateParam", "{\"code\":" + verifyCode + "}");
        CommonResponse response = client.getCommonResponse(request);
    }
}
