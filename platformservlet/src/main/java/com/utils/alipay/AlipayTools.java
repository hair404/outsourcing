package com.utils.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;

import java.io.File;
import java.text.DecimalFormat;

public class AlipayTools {

    private final static String certPath = new File("payment/appCertPublicKey_2016101700705840.crt").getAbsolutePath();
    private final static String alipayPublicCertPath = new File("payment/alipayCertPublicKey_RSA2.crt").getAbsolutePath();
    private final static String rootCertPath = new File("payment/alipayRootCert.crt").getAbsolutePath();
    private final static String getway = "https://openapi.alipaydev.com/gateway.do";
    private final static String appid = "2016101700705840";
    private final static String privateId = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCNrHJcGz38MTVy9N7Vw7j4Y/8SKddn1pQh71iAa1NYym6GYTPo34JAzafKgFQ0yeZOEsnQO+7YVvaZGV9NPCRWq7lyZJt+sZwHmgqBy4nWGE5Fz0dUZj7gnfi4bnLLmyy+jgSOwMxjnE+lhn/wIXFz+XniABHd2ImwUk9rvi+nyOod8Xeymd/CSFX8J6MFCkRU6qh3rZfMc+zaeeqdcffnX7kCjK7ABnHF1rrLnUR1xBVSvyZFPSRsOI0sFVkcNV+44V+VGYNFzyimpXYBOG6QyV6fxC6FJUtfqjBBh40jZzJwMLOQJSaWQUAdWSxLosJJ2CrmVhZnoTeadk84s+I/AgMBAAECggEBAI0JvvN431VgO8CMjAYtCcil6/lOFDY4hVHfL7Dwp5G+PRekTwQm9knDMqZC6RGAabkRtT2LXLScpnQ1sQ13sQljfYOnKrGO6TEcJhee5EJyAgI3XuBjxXbhbQlWUwvlGD8/rMPcpd0IcLXtVxJciNCW7MKTpTXAaxpQZuZw+IpD0C0B5S7aEiH4zMpvGEHw4Vmo6ps8sZ/A+ksRwpU38HZjUKApcN54eWC2p/c5iD4GnetS6x+1FB4Atl7vdgAlDblVj0DWTrkgGFRSvN/dn0Jh6YNCgvzf1p1QahC5lflvnzZRcXa1XyZcAXF6Xu9QKDMPDFt4TbVOctCSPVoIXTkCgYEA85TWEk5z1yWCrPR74GVsBo35pnQw6P7En/J8AiY6lBfzsBedqoGdjxQNye68oJHFe+CP0inXEGmbbOFuZfGZn2aQiBHkcNn1qtrzJUk6NlY6o2SwEMfEM6l8orKK/0gaYdD/dokvTT6Vb3cAafLq6QHHtEiFBhONisc+sgX3lZUCgYEAlOWJHMZKGumsvsBML4s+yTwDvC5ogdnJjOcw8DgcpIQnHARRAFwNeDe7NniqcmXKS6MwlBcA2FI3Z35/dQeiq17zBIpYrp4wSa6h+9Q7Crd2hJ9n62hi/WTngQF68dCvEW1snzYvD/d1irrWZbsIR9Nep7F35Rf7vU8QTkklO4MCgYEA6s5nLm6CLLv0JFXptH8Qi6EBL/yByZkOYkGWWVwQzAHq361kR6F7CRoD0M1a+E6NWMU43xeGQkQLmAIYMh2cQZTVbtYQqNjzgShfi2nzu72T+T5umz53XHXdt9j1NPGEvRaDrPjBqGzUEQdeVsPcv5D/ukLOOPVQVJ+NSH+IE6ECgYB6yZM5rHfeG8e+AlyRdJYRYzF9VRQPyScB0IG2B7/vF6Ac5Z5a2o6tDeCQOg/tiO27VFcyOIoU2jAgY9v+CY7nHcTwgJpDXeZaTO39+3W5D7RoCACtzAaeCkEzTKIzmjFVANTrssY6t4oyHoPBnuxkNvW++oXx4cZSxkmfFYjjuwKBgQDvFN+1J77lCEyQ9xXMA2qZzLKv7O+lnAuXQ0EkJ0JIyjS3jx/T4j3evlrJaDQQ4/YSDHOPKsKolWIeIpFKUJ7WomxejuG1L2fRz24xRYt21rV4xTDFVjgzBAIjmYo62deiemM8AzhSDNeVLoj/kqbC74vxH0tay9LCOKhB7TTfVw==";

    public static String pay(PayInfo payInfo, String notifyURL, String returnURL) {
        try {
            DefaultAlipayClient alipayClient = getDefualtClient();

            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();// 创建API对应的request
            alipayRequest.setNotifyUrl(notifyURL);// 在公共参数中设置回跳和通知地址
            alipayRequest.setReturnUrl(returnURL);
            alipayRequest.setBizContent(payInfo.toJson());// 填充业务参数
            String form = "";
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            return form;
        } catch (AlipayApiException e1) {
            e1.printStackTrace();
            return "";
        }
    }

    public static boolean transfer(String account, double amount) throws AlipayApiException {
        DefaultAlipayClient alipayClient = getDefualtClient();
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();

        //设置转账信息
        TransferInfo transferInfo = new TransferInfo();
        transferInfo.setAccount(account);
        transferInfo.generateRandomOutBizNo();
        transferInfo.setTransAmount(new DecimalFormat("#.00").format(amount));

        request.setBizContent(transferInfo.toJsonString());
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
        return response.isSuccess();
    }

    private static DefaultAlipayClient getDefualtClient() throws AlipayApiException {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(getway);
        certAlipayRequest.setAppId(appid);
        certAlipayRequest.setPrivateKey(privateId);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset("utf-8");
        certAlipayRequest.setSignType("RSA2");
        certAlipayRequest.setCertPath(certPath);
        certAlipayRequest.setAlipayPublicCertPath(alipayPublicCertPath);
        certAlipayRequest.setRootCertPath(rootCertPath);
        return new DefaultAlipayClient(certAlipayRequest);
    }
}
