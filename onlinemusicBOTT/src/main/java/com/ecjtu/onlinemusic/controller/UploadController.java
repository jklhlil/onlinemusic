package com.ecjtu.onlinemusic.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
 * @description: 文件上传
 * @author: 何江涛
 * @create: 2022-06-15
 * @version: 1.0
 */
@Controller
public class UploadController {
    @Value("${spring.uploadUrl}")
    private  String  FILE_UPLOAD_PATH;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return "上传失败";
        }
        String fileName = file.getOriginalFilename();//获取文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//获取文件后缀
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");//指定日期格式
        Random r = new Random();//以当前时间为种子,创建一个随机数生成器
        StringBuilder tempName = new StringBuilder();//调用内置函数(append,insert等)用于拼接字符串的对象,和+操作没什么区别
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();

//        newFileName = fileName;

        try {
            // 新建目录
            File files = new File(FILE_UPLOAD_PATH);
            if (!files.exists()) { //如果不存在
                boolean dr = files.mkdirs(); //创建目录
            }

            // 新建文件
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FILE_UPLOAD_PATH + newFileName);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功，图片地址为：/files/" + newFileName;
    }
}
