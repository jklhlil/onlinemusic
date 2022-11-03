package com.ecjtu.onlinemusic;


import com.ecjtu.onlinemusic.dao.ConsumerDao;
import com.ecjtu.onlinemusic.dao.SubscribeCollectDao;
import com.ecjtu.onlinemusic.entity.Admin;
import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;
import com.ecjtu.onlinemusic.service.impl.ConsumerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ServiceTests {
    @Autowired
    SubscribeCollectDao subColDao;
    @Autowired
    ConsumerDao consumerDao;

    @Autowired
    ConsumerServiceImpl consumerService;

    @Test
    void subColService() {
        SubscribeCollect subscribeCollect=new SubscribeCollect();
        int total=subColDao.selectByPage(subscribeCollect,null).size();
        //  一页的
        List<Admin> subColList=subColDao.selectByPage(subscribeCollect,new PageBean(1,2));

        System.err.println(new RespPageBean(total,subColList));
    }

    @Test
    void consumerService() {
        Consumer consumer=new Consumer();
        consumer.setId(13);
        int total=consumerDao.selectByPage(consumer,null).size();
        //  一页的
        List<Admin> subColList=consumerDao.selectByPage(consumer,new PageBean(0,-1));

        System.err.println(new RespPageBean(total,subColList));
    }

    @Test
    void time() {
        double aa = System.currentTimeMillis();
        Consumer consumer=   new Consumer();
        consumer.setId(5);
        consumer.setName("lll");
        for(int i=0;i<2000;i++){
            try {
                consumerService.update(consumer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        double bb = System.currentTimeMillis();
        System.err.println(bb - aa);
    }
}
