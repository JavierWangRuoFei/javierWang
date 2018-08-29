package com.javier.file.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/8/29 10:02
 * @Version 1.0
 */
@RestController
@RequestMapping("/javier/")
@Api(value = "fileuploadApi")
public class FileUploadController {
    @RequestMapping(value = "fileUpload", method = RequestMethod.POST)
    @ApiOperation(value = "文件流上传接口",notes = "文件流上传接口")
    public String fileUpload(@RequestParam("file")CommonsMultipartFile file){
        //检测程序运行时间
        long startTime = System.currentTimeMillis();
        System.out.println(startTime);
        //oss客户端
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAIIhjDVEbWU1QW";
        String accessKeySecret = "eQK5vucVZvBaYKdJWweLWjeBUqpKXP";
        OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        String fileName = file.getOriginalFilename();
        System.out.println("filename:"+fileName);

        try {
            //获取输入流CommonsMultipartFile中可以直接得到文件的流
            InputStream is = file.getInputStream();
            int temp;
            ObjectMetadata metadata = new ObjectMetadata();
            PutObjectResult result = ossClient.putObject("youdaobiji", fileName, is, metadata);
            String tag = result.getETag();
            System.out.println(tag);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime);
        return null;
    }
    @RequestMapping(value = "springUpload", method = RequestMethod.POST)
    @ApiOperation(value = "spring文件流上传接口",notes = "spring文件流上传接口")
    public String springUpload(HttpServletRequest request){
        //检测程序运行时间
        long startTime = System.currentTimeMillis();
        System.out.println(startTime);
        //oss客户端
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAIIhjDVEbWU1QW";
        String accessKeySecret = "eQK5vucVZvBaYKdJWweLWjeBUqpKXP";
        OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);

        try {
            //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
            CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //检查form中是否有enctype="multipart/form-data"
            if (resolver.isMultipart(request)){
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iterator = multiRequest.getFileNames();
                while (iterator.hasNext()){
                    //依次遍历所有文件
                    MultipartFile file = multiRequest.getFile(iterator.next().toString());
                    if (file != null){
                        String fileName = file.getName();
                        InputStream is = file.getInputStream();
                        ObjectMetadata metadata = new ObjectMetadata();
                        PutObjectResult result = ossClient.putObject("youdaobiji", fileName, is, metadata);
                        String tag = result.getETag();
                        System.out.println(tag);
                        is.close();
                        /*//如果是保存到本地服务器
                        String path="E:/springUpload"+file.getOriginalFilename();
                        //上传
                        file.transferTo(new File(path));*/
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime);
        return null;
    }

    /**
     * 采用file.Transto 来保存上传的文件
     * 文件保存在本地服务器
     * @param file
     * @return
     */
    @RequestMapping(value = "fileUpload2", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传接口",notes = "文件上传接口")
    public String fileUpload2(@RequestParam("file")CommonsMultipartFile file){
        long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename());

        try {
            String path="E:/"+startTime+file.getOriginalFilename();
            File newFile=new File(path);
            //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long  endTime=System.currentTimeMillis();
        System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");
        return null;
    }
}
