package com.ecjtu.onlinemusic.service.impl;


import com.ecjtu.onlinemusic.dao.CommentDao;
import com.ecjtu.onlinemusic.entity.Admin;
import com.ecjtu.onlinemusic.entity.Comment;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.vo.*;
import com.ecjtu.onlinemusic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    //    查看音乐的所有评论
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findConsumerByMusicId(RequestBean<Integer> requestBean) throws Exception {
        // 构建评论，后查询
        Comment comment = new Comment();
        comment.setIsCheck(1);
        comment.setMusicId(requestBean.getData());
        RespPageBean respPageBean = selectConsumerByPage(comment, requestBean.getPage(), requestBean.getPageSize());

        return respPageBean;
    }

    //    查看音乐的所有评论数
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Integer findConsumerNumByMusicId(Integer musicId) throws Exception {
        // 构建评论，后查询
        Comment comment = new Comment();
        comment.setIsCheck(1);
        comment.setMusicId(musicId);
        List<Comment> commentList = commentDao.selectConsumerByPage(comment, null);

        return commentList.size();
    }

    //    查看用户的所有评论
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findMusicByConsumerId(RequestBean<Integer> requestBean) throws Exception {
        // 构建评论，后查询
        Comment comment = new Comment();
        comment.setIsCheck(1);
        comment.setConsumerId(requestBean.getData());
        RespPageBean respPageBean = selectMusicByPage(comment, requestBean.getPage(), requestBean.getPageSize());

        return respPageBean;
    }

    //    查看没通过审核的评论
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean findNoCheck(RequestBean<Comment> requestBean) throws Exception {
        // 将isCheck设为0
        if (requestBean.getData() == null) {
            requestBean.setData(new Comment());
        }
        requestBean.getData().setIsCheck(0);
        RespPageBean respPageBean = selectMusicByPage(requestBean.getData(), requestBean.getPage(), requestBean.getPageSize());
        return respPageBean;
    }

    // 批量通过审核
    public Integer batchCheckByids(List<Integer> idList) throws Exception {
        Comment comment = new Comment();
        for (Integer id : idList) {
            // 有一个删除失败，就失败
            comment.setId(id);
            comment.setIsCheck(1);
            if (commentDao.update(comment) == 0) {
                return 0;
            }
        }
        return 1;
    }

    //    查看评论排行榜
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean queryComRankBypage(PageBean pageBean) throws Exception {
        // 总数 和 数据
        Integer total = commentDao.queryComRankBypage(null).size();
        List<Music> musicList = commentDao.queryComRankBypage(pageBean);

        return new RespPageBean( total,musicList);
    }

    @Override
    public Integer deleteById(Integer commentId) throws Exception {
        return commentDao.deleteById(commentId);
    }

    //新增评论
    @Override
    public Integer insert(Comment comment) throws Exception {
        // 无论谁新增comment，都要审核
        comment.setIsCheck(0);
        return commentDao.insert(comment);
    }

    @Override
    public Integer update(Comment comment) throws Exception {
        return commentDao.update(comment);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectByPage(Comment comment, Integer page, Integer pageSize) throws Exception {
        //  总数
        int total = commentDao.selectByPage(comment, null).size();
        //  一页的
        List<Comment> commentList = commentDao.selectByPage(comment, new PageBean(page, pageSize));

        for (Comment comment1 : commentList) {
            comment1.setConsumer(null);
            comment1.setMusic(null);
        }
        return new RespPageBean(total, commentList);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectMusicByPage(Comment comment, Integer page, Integer pageSize) throws Exception {
        //  总数
        int total = commentDao.selectMusicByPage(comment, null).size();
        //  一页的
        List<Comment> commentList = commentDao.selectMusicByPage(comment, new PageBean(page, pageSize));
        for (Comment comment1 : commentList) {
            comment1.setConsumer(null);
        }
        return new RespPageBean(total, commentList);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RespPageBean selectConsumerByPage(Comment comment, Integer page, Integer pageSize) throws Exception {
        //  总数

        int total = commentDao.selectConsumerByPage(comment, null).size();
        //  一页的
        List<Comment> commentList = commentDao.selectConsumerByPage(comment, new PageBean(page, pageSize));
        for (Comment comment1 : commentList) {
            comment1.setMusic(null);
        }
        return new RespPageBean(total, commentList);
    }


    @Override
    public int approveComment(Comment comment) throws Exception {
//      //  总数
//      int total=commentDao.selectByPage(comment,null).size();
//      //  一页的
//      List<Admin> commentList=commentDao.selectByPage(comment,new PageBean(page,pageSize));
//
        return 0;
    }
}
