package com.javier.file.service;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/11/13 16:19
 * @Version 1.0
 */
public interface FileURLService {
    String getFileURL(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String objectName);
}
