package com.ecjtu.onlinemusic.dao;


import com.ecjtu.onlinemusic.entity.Admin;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface AdminDao {

   //根据管理员id删除管理员
   int deleteById(Integer adminId);
   //   添加管理员
   int insert(Admin admin);
   //   删除管理员
   int update(Admin admin);
   //   查看管理员信息
   List selectByPage(@Param("admin") Admin admin,@Param("page")  PageBean pageBean);

}
