package com.ecjtu.onlinemusic;

import com.ecjtu.onlinemusic.entity.vo.MailRequest;
import com.ecjtu.onlinemusic.service.impl.SendMailServiceImpl;
import com.ecjtu.onlinemusic.util.Upload;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import java.io.File;
import java.util.*;

@SpringBootApplication
@EnableTransactionManagement
public class OnlinemusicApplication {

    public static void main(String[] args) {
      SpringApplication.run(OnlinemusicApplication.class, args);

    }

}
