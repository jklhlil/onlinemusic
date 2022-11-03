package com.ecjtu.onlinemusic.dao;


import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface ConsumerDao {

   int deleteById(Integer consumerId);
   int insert(Consumer consumer);
   int update(Consumer consumer);

   List selectByPage(@Param("consumer") Consumer consumer, @Param("page") PageBean pageBean);
   Consumer selectById(Integer id);

}
