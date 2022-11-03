package com.ecjtu.onlinemusic.service;


import com.ecjtu.onlinemusic.entity.Comment;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;

import java.util.*;


public interface CommentService {

   Integer deleteById(Integer commentId) throws Exception;
   Integer insert(Comment comment) throws Exception;
   Integer update(Comment comment) throws Exception;

   RespPageBean selectByPage(Comment comment, Integer page, Integer pageSize) throws Exception;

   RespPageBean selectMusicByPage(Comment comment, Integer page, Integer pageSize) throws Exception;
   RespPageBean selectConsumerByPage(Comment comment, Integer page, Integer pageSize) throws Exception;

   int approveComment(Comment comment) throws Exception;

}
