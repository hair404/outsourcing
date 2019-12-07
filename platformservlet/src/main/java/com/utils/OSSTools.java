package com.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;

import java.io.ByteArrayInputStream;

public class OSSTools {

    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String accessKeyId = "LTAI4FpwjngawSEnW7fe5NxX";
    private static final String accessKeySecret = "PyQPK27YcIuiIsyQV5fJsI9XPrLbqb";
    private static final String bucket = "zhongbaowuyou";
    private static final OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    public static final String url = "http://zhongbaowuyou.oss-cn-hangzhou.aliyuncs.com/";

    public static void uploadFile(String path, byte[] content) {
        ossClient.putObject(bucket, path, new ByteArrayInputStream(content));
    }
}
