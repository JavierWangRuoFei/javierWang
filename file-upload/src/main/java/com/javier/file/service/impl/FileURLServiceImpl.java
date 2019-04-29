package com.javier.file.service.impl;

import com.aliyun.oss.OSSClient;
import com.javier.file.service.FileURLService;

import java.net.URL;
import java.util.Date;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/11/13 16:20
 * @Version 1.0
 */
public class FileURLServiceImpl implements FileURLService {
    @Override
    public String getFileURL(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String objectName) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        accessKeyId = "LTAIk20LehTjbi9c";
        accessKeySecret = "9f6W7FKK46G28JXcxn61ILp85yjRmc";
        bucketName = "youdaobiji";
        objectName = "589595.jpg";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + 60 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);

        // 关闭OSSClient。
        ossClient.shutdown();
        return url.toString();
    }
}
