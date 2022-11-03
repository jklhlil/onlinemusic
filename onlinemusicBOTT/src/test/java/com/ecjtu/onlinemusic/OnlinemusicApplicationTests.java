package com.ecjtu.onlinemusic;


import com.ecjtu.onlinemusic.dao.ConsumerDao;
import com.ecjtu.onlinemusic.dao.SubscribeCollectDao;
import com.ecjtu.onlinemusic.entity.ActivityConsumer;
import com.ecjtu.onlinemusic.entity.Admin;
import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;
import com.ecjtu.onlinemusic.service.impl.ConsumerServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@SpringBootTest
class OnlinemusicApplicationTests {
    @Autowired
    SubscribeCollectDao subColDao;
    @Autowired
    ConsumerDao consumerDao;

    @Test
    void tim() {
//        Date a = new Date();
//        a.getTime();
//        System.err.println(a.getTime());
    }
}
