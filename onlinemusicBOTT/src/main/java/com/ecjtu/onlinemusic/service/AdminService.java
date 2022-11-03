package com.ecjtu.onlinemusic.service;


import com.ecjtu.onlinemusic.entity.Admin;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;



public interface AdminService {

   Integer deleteById(Integer musicId) throws Exception;
   Integer insert(Admin admin) throws Exception;
   Integer update(Admin admin) throws Exception;

   RespPageBean selectByPage(Admin admin, Integer page, Integer pageSize) throws Exception;

}
