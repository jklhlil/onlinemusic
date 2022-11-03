package com.ecjtu.onlinemusic.daoTest;


import com.ecjtu.onlinemusic.dao.MusicDao;
import com.ecjtu.onlinemusic.dao.MusicDao;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MusicTest {

    @Autowired
    MusicDao musicDao;

    @Test
    void deleteByMusicId() {
        System.out.println(musicDao.deleteById(10));
        //  输出红色显眼
        System.err.println("1. delete成功");
    }

    @Test
    void insertByMusic() {
        Music music = new Music();
        music.setId(11);
        music.setUrl("1224");

        System.out.println(musicDao.insert(music));
        System.err.println("2. insert成功");

    }

    @Test
    void updateByMusic() {
        Music music = new Music();
        music.setId(12);
        music.setUrl("1224");
        music.setName("pp");
        System.out.println(musicDao.update(music));
        System.err.println("3. update成功");

    }

    //    分页查询
    @Test
    void selectMusicByPage() {

        Music music = new Music();
        music.setName("明");


        System.out.println(musicDao.selectByPage(music, null));
        System.err.println("4.1 select实体 成功");

        System.out.println(musicDao.selectByPage(null, new PageBean(3, 5)));
        System.err.println("4.2 select分页 成功");

        System.out.println(musicDao.selectByPage(music, new PageBean(1, 5)));
        System.err.println("4.3 select实体+分页 成功");


    }

}
