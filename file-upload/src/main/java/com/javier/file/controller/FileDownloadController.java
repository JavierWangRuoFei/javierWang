package com.javier.file.controller;

import com.javier.file.service.FileURLService;
import com.javier.file.service.impl.FileURLServiceImpl;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/11/13 16:18
 * @Version 1.0
 */
public class FileDownloadController {
    public static void main(String[] args) {
        FileURLService fileURLService = new FileURLServiceImpl();
        String path = fileURLService.getFileURL("","","","","");
        System.out.println(path);
    }
}
