package com.ecjtu.onlinemusic.dao;


import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface SubscribeCollectDao {

   int deleteById(Integer subscribeCollectId);
   int insert(SubscribeCollect subscribeCollect);
   int update(SubscribeCollect subscribeCollect);

   List selectByPage(@Param("subscribeCollect")SubscribeCollect subscribeCollect, @Param("page")PageBean pageBean);

   //   两表查询,能通过用户订阅查到音乐
   List selectMusicByPage(@Param("subscribeCollect")SubscribeCollect subscribeCollect, @Param("page")PageBean pageBean);
   //   两表查询,能通过音乐查到用户
   List selectConsumerByPage(@Param("subscribeCollect")SubscribeCollect subscribeCollectId, @Param("page")PageBean pageBean);


   List<Music> queryColRankBypage(PageBean pageBean);
   List<Music> querySubRankBypage(PageBean pageBean);

}
