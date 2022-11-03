package com.ecjtu.onlinemusic.dao;

import org.apache.ibatis.annotations.Mapper;
import com.ecjtu.onlinemusic.entity.ActivityConsumer;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface ActivityConsumerDao {

   int deleteById(Integer activityConsumerId);
   int insert(ActivityConsumer activityConsumer);
   int update(ActivityConsumer activityConsumer);

   List selectByPage(@Param("activityConsumer")ActivityConsumer activityConsumer, @Param("page")PageBean pageBean);

   //   两表查询,能通过活动查到用户
   List selectConsumerByPage(@Param("activityConsumer")ActivityConsumer activityConsumer, @Param("page")PageBean pageBean);
   //   两表查询,能通过用户查到活动
   List selectActivityByPage(@Param("activityConsumer")ActivityConsumer activityConsumer,@Param("page") PageBean pageBean);

}
