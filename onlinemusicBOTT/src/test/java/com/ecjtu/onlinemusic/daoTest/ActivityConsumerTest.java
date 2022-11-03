package com.ecjtu.onlinemusic.daoTest;

import com.ecjtu.onlinemusic.dao.ActivityConsumerDao;
import com.ecjtu.onlinemusic.entity.ActivityConsumer;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ActivityConsumerTest {
    @Autowired
    ActivityConsumerDao activityConsumerDao;

    @Test
    void deleteByActivityConsumerId() {
        System.out.println(activityConsumerDao.deleteById(10));
        // 输出红色显眼
        System.err.println("1. delete成功");
    }

    @Test
    void insertByActivityConsumer() {
        ActivityConsumer activityConsumer = new ActivityConsumer();
        activityConsumer.setConsumerId(1);

        System.out.println(activityConsumerDao.insert(activityConsumer));
        System.err.println("2. insert成功");
    }

    @Test
    void updateByActivityConsumer() {
        ActivityConsumer activityConsumer = new ActivityConsumer();
        activityConsumer.setId(15);
        activityConsumer.setActivityId(11);

        System.out.println(activityConsumerDao.update(activityConsumer));
        System.err.println("3. update成功");
    }

    //    分页查询
    @Test
    void selectActivityConsumerByPage() {
        ActivityConsumer activityConsumer = new ActivityConsumer();
        activityConsumer.setConsumerId(2);

        System.out.println(activityConsumerDao.selectByPage(activityConsumer, null));
        System.err.println("4.1 select实体 成功");

        System.out.println(activityConsumerDao.selectByPage(null, new PageBean(3, 5)));
        System.err.println("4.2 select分页 成功");

        List<ActivityConsumer> activityConsumers = activityConsumerDao.selectByPage(activityConsumer, new PageBean(1, 5));
//  for( ActivityConsumer activityConsumer1:activityConsumers ){
//      System.out.println(activityConsumer1);
//
//  }
        System.out.println(activityConsumers);
        System.err.println("4.3 select实体+分页 成功");
    }


    @Test
    void selectSubColMusicByPage() {
        ActivityConsumer activityConsumer = new ActivityConsumer();
        activityConsumer.setConsumerId(2);

        System.out.println(activityConsumerDao.selectConsumerByPage(activityConsumer, null));
        System.err.println("5.1 select实体 成功");

        System.out.println(activityConsumerDao.selectConsumerByPage(null, new PageBean(3, 5)));
        System.err.println("5.2 select分页 成功");

        List<ActivityConsumer> activityConsumers = activityConsumerDao.selectConsumerByPage(activityConsumer, new PageBean(1, 5));
//  for( ActivityConsumer activityConsumer1:activityConsumers ){
//      System.out.println(activityConsumer1);
//
//  }
        System.out.println(activityConsumers);
        System.err.println("5.3 select实体+分页 成功");
    }

    @Test
    void selectSubColConsumerByPage() {
        ActivityConsumer activityConsumer = new ActivityConsumer();
        activityConsumer.setConsumerId(2);

        System.out.println(activityConsumerDao.selectActivityByPage(activityConsumer, null));
        System.err.println("6.1 select实体 成功");

        System.out.println(activityConsumerDao.selectActivityByPage(null, new PageBean(3, 5)));
        System.err.println("6.2 select分页 成功");

        List<ActivityConsumer> activityConsumers = activityConsumerDao.selectActivityByPage(activityConsumer, new PageBean(1, 5));
//  for( ActivityConsumer activityConsumer1:activityConsumers ){
//      System.out.println(activityConsumer1);
//
//  }
        System.out.println(activityConsumers);
        System.err.println("6.3 select实体+分页 成功");
    }


}
