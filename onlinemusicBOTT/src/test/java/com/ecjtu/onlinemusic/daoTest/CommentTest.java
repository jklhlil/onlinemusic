package com.ecjtu.onlinemusic.daoTest;

import com.ecjtu.onlinemusic.dao.CommentDao;
import com.ecjtu.onlinemusic.entity.Comment;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class CommentTest {

    @Autowired
    CommentDao commentDao;

    @Test
    void deleteByCommentId() {
        System.out.println(commentDao.deleteById(10));
        // 输出红色显眼
        System.err.println("1. delete成功");
    }

    @Test
    void insertByComment() {
        Comment comment = new Comment();
        comment.setId(11);
        comment.setContent("1224");

        System.out.println(commentDao.insert(comment));
        System.err.println("2. insert成功");

    }

    @Test
    void updateByComment() {
        Comment comment = new Comment();
        comment.setId(12);
        comment.setContent("1224");
//        Date date = new Date();
//        comment.setTime(date);
        System.out.println(commentDao.update(comment));
        System.err.println("3. update成功");

    }

    //   分页查询
    @Test
    void selectCommentByPage() {

        Comment comment = new Comment();
        comment.setMusicId(1);

        System.out.println(commentDao.selectByPage(comment, null));
        System.err.println("4.1 select实体 成功");

        System.out.println(commentDao.selectByPage(null, new PageBean(3, 5)));
        System.err.println("4.2 select分页 成功");

        List<Comment> comments = commentDao.selectByPage(comment, new PageBean(1, 5));
//  for( Comment comment1:comments ){
//      System.out.println(comment1);
//
//  }
        System.out.println(comments);
        System.err.println("4.3 select实体+分页 成功");
    }

    //    两表查询测试，查音乐
    @Test
    void selectCommentMusicByPage() {

        Comment comment = new Comment();
        comment.setConsumerId(1);

        System.out.println(commentDao.selectMusicByPage(comment, null));
        System.err.println("5.1 select实体 成功");

        System.out.println(commentDao.selectMusicByPage(null, new PageBean(3, 5)));
        System.err.println("5.2 select分页 成功");

        List<Comment> comments = commentDao.selectMusicByPage(comment, new PageBean(1, 5));
        System.out.println(comments);
        System.err.println("5.3 select实体+分页 成功");
    }

    //    两表查询测试，查用户
    @Test
    void selectCommentConsumerByPage() {

        Comment comment = new Comment();
        comment.setMusicId(1);

        System.out.println(commentDao.selectConsumerByPage(comment, null));
        System.err.println("6.1 select实体 成功");

        System.out.println(commentDao.selectConsumerByPage(null, new PageBean(3, 5)));
        System.err.println("6.2 select分页 成功");

        List<Comment> comments = commentDao.selectConsumerByPage(comment, new PageBean(1, 5));
        System.out.println(comments);
        System.err.println("6.3 select实体+分页 成功");
        for (Comment comment1 : comments) {
            System.out.println(comment1);

        }
    }

    // 评论排行榜
    @Test
    void querySubRankBypage() {
        List<Music> musicList = commentDao.queryComRankBypage(new PageBean(0,null));
        for ( Music music :musicList ){
            System.err.println(music);

        }
    }

}
