package com.ecjtu.onlinemusic.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
 * @description: 上传文件
 * @author: 何江涛
 * @create: 2022-06-19
 * @version: 1.0
 */
@Component
public class Upload {

    private static String FILE_UPLOAD_PATH;
    @Value("${spring.uploadUrl}")  //value注入静态变量的方法
    public void setFILE_UPLOAD_PATH(String FILE_UPLOAD_PATH) {
        this.FILE_UPLOAD_PATH = FILE_UPLOAD_PATH;
    }

    public static String upload(MultipartFile file) {

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

        return "/files/" + newFileName;
    }

    // 删除文件
    public static boolean deleteFile(String filename) {
        File file = new File(FILE_UPLOAD_PATH + filename);

        boolean value = file.delete();
        return value;
//        if (value) {
//            System.out.println(FILE_UPLOAD_PATH+filename+"已成功删除.");
//        } else {
//            System.out.println("文件不存在");
//        }
    }
}
