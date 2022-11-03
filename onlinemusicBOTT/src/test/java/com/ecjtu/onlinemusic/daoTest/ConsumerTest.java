package com.ecjtu.onlinemusic.daoTest;

import com.ecjtu.onlinemusic.dao.ConsumerDao;
import com.ecjtu.onlinemusic.dao.ConsumerDao;
import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ConsumerTest {

    @Autowired
    ConsumerDao consumerDao;

    @Test
    void deleteByConsumerId() {
        System.out.println(consumerDao.deleteById(10));
        // 输出红色显眼
        System.err.println("1. delete成功");
    }

    @Test
    void insertByConsumer() {
        Consumer consumer = new Consumer();
        consumer.setMail("1224");
        consumer.setPassword("1224");

        System.out.println(consumerDao.insert(consumer));
        System.err.println("2. insert成功");

    }

    @Test
    void updateByConsumer() {
        Consumer consumer = new Consumer();
        consumer.setId(12);
        consumer.setName("3333");

        System.out.println(consumerDao.update(consumer));
        System.err.println("3. update成功");

    }

    //    分页查询
    @Test
    void selectConsumerByPage() {

        Consumer consumer = new Consumer();


        System.out.println(consumerDao.selectByPage(consumer, null));
        System.err.println("4.1 select实体 成功");

        System.out.println(consumerDao.selectByPage(null, new PageBean(3, 5)));
        System.err.println("4.2 select分页 成功");

        List<Consumer> consumers = consumerDao.selectByPage(consumer, new PageBean(1, 5));
//  for( Consumer consumer1:consumers ){
//      System.out.println(consumer1);
//
//  }
        System.out.println(consumers);
        System.err.println("4.3 select实体+分页 成功");
    }



}
