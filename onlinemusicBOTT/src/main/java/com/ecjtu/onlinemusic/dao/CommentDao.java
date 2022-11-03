package com.ecjtu.onlinemusic.dao;


import com.ecjtu.onlinemusic.entity.Comment;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface CommentDao {

   int deleteById(Integer commentId);
   int insert(Comment comment);
   int update(Comment comment);

   List selectByPage(@Param("comment")Comment comment,@Param("page") PageBean pageBean);

//   两表查询,能通过用户查到音乐
   List selectMusicByPage(@Param("comment")Comment comment,@Param("page") PageBean pageBean);
//   两表查询,能通过音乐查到用户
   List selectConsumerByPage(@Param("comment")Comment comment,@Param("page") PageBean pageBean);

   List<Music> queryComRankBypage(PageBean pageBean);
}
