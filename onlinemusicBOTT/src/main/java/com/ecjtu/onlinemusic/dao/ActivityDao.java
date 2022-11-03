package com.ecjtu.onlinemusic.dao;


import com.ecjtu.onlinemusic.entity.Activity;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface ActivityDao {

   int deleteById(Integer consumerId);
   int insert(Activity activity);
   int update(Activity activity);

   List selectByPage(@Param("activity") Activity activity, @Param("page")PageBean pageBean);

}
