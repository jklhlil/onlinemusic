package com.ecjtu.onlinemusic.daoTest;

import com.ecjtu.onlinemusic.dao.SubscribeCollectDao;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class SubscribeCollectTest {

    @Autowired
    SubscribeCollectDao subscribeCollectDao;

    @Test
    void deleteBySubscribeCollectId() {
        System.out.println(subscribeCollectDao.deleteById(10));
        // 输出红色显眼
        System.err.println("1. delete成功");
    }

    @Test
    void insertBySubscribeCollect() {
        SubscribeCollect subscribeCollect = new SubscribeCollect();

        System.out.println(subscribeCollectDao.insert(subscribeCollect));
        System.err.println("2. insert成功");

    }

    @Test
    void updateBySubscribeCollect() {
        SubscribeCollect subscribeCollect = new SubscribeCollect();
        subscribeCollect.setId(12);
        subscribeCollect.setMusicId(11);

        System.out.println(subscribeCollectDao.update(subscribeCollect));
        System.err.println("3. update成功");

    }

    //    分页查询
    @Test
    void selectSubscribeCollectByPage() {

        SubscribeCollect subscribeCollect = new SubscribeCollect();
        subscribeCollect.setMusicId(1);

        System.out.println(subscribeCollectDao.selectByPage(subscribeCollect, null));
        System.err.println("4.1 select实体 成功");

        System.out.println(subscribeCollectDao.selectByPage(null, new PageBean(3, 5)));
        System.err.println("4.2 select分页 成功");

        List<SubscribeCollect> subscribeCollects = subscribeCollectDao.selectByPage(subscribeCollect, new PageBean(1, 5));
//  for( SubscribeCollect subscribeCollect1:subscribeCollects ){
//      System.out.println(subscribeCollect1);
//
//  }
        System.out.println(subscribeCollects);
        System.err.println("4.3 select实体+分页 成功");
    }


    @Test
    void selectSubColMusicByPage() {

        SubscribeCollect subscribeCollect = new SubscribeCollect();
        subscribeCollect.setMusicId(1);

        System.out.println(subscribeCollectDao.selectMusicByPage(subscribeCollect, null));
        System.err.println("5.1 select实体 成功");

        System.out.println(subscribeCollectDao.selectMusicByPage(null, new PageBean(3, 5)));
        System.err.println("5.2 select分页 成功");

        List<SubscribeCollect> subscribeCollects = subscribeCollectDao.selectMusicByPage(subscribeCollect, new PageBean(1, 5));
//  for( SubscribeCollect subscribeCollect1:subscribeCollects ){
//      System.out.println(subscribeCollect1);
//
//  }
        System.out.println(subscribeCollects);
        System.err.println("5.3 select实体+分页 成功");
    }

    @Test
    void selectSubColConsumerByPage() {

        SubscribeCollect subscribeCollect = new SubscribeCollect();
        subscribeCollect.setMusicId(1);
        subscribeCollect.setConsumerId(2);
        System.out.println(subscribeCollectDao.selectConsumerByPage(subscribeCollect, null));
        System.err.println("6.1 select实体 成功");

        System.out.println(subscribeCollectDao.selectConsumerByPage(null, new PageBean(3, 5)));
        System.err.println("6.2 select分页 成功");

        List<SubscribeCollect> subscribeCollects = subscribeCollectDao.selectConsumerByPage(subscribeCollect, new PageBean(1, 5));
//  for( SubscribeCollect subscribeCollect1:subscribeCollects ){
//      System.out.println(subscribeCollect1);
//
//  }
        System.out.println(subscribeCollects);
        System.err.println("6.3 select实体+分页 成功");
    }

    // 收藏排行榜
    @Test
    void queryColRankBypage() {
        List<Music> musicList = subscribeCollectDao.queryColRankBypage(new PageBean(1, 1));
        System.out.println(musicList);
    }

    // 订阅排行榜
    @Test
    void querySubRankBypage() {
        List<Music> musicList = subscribeCollectDao.querySubRankBypage(null);
        System.out.println(musicList);
    }

}
