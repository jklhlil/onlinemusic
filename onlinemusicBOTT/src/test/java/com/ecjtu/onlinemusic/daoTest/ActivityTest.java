package com.ecjtu.onlinemusic.daoTest;

import com.ecjtu.onlinemusic.dao.ActivityDao;
import com.ecjtu.onlinemusic.entity.Activity;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ActivityTest {

    @Autowired
    ActivityDao activityDao;

    @Test
    void deleteByActivityId() {
        System.out.println(activityDao.deleteById(6));
        //  输出红色显眼
        System.err.println("1. delete成功");

    }

    @Test
    void insertByActivity() {
        Activity activity = new Activity();
        activity.setName("hh");
        activity.setContent("hhjkhkhh");

        System.out.println(activityDao.insert(activity));
        System.err.println("2. insert成功");
    }

    @Test
    void updateByActivity() {
        Activity activity = new Activity();
        activity.setId(5);
        activity.setName("fdasss");

        System.out.println(activityDao.update(activity));
        System.err.println("3. update成功");

    }

    //    分页查询
    @Test
    void selectActivityByPage() {
        Activity activity = new Activity();
        activity.setName("国");

        System.out.println(activityDao.selectByPage(activity, null));
        System.err.println("4.1 select实体 成功");

        System.out.println(activityDao.selectByPage(null, new PageBean(1, 5)));
        System.err.println("4.2 select分页 成功");

        List<Activity> activitys = activityDao.selectByPage(activity, new PageBean(1, 5));
//  for( Activity activity1:activitys ){
//      System.out.println(activity1);
//
//  }
        System.out.println(activitys);
        System.err.println("4.3 select实体+分页 成功");
    }


}
