package com.ecjtu.onlinemusic.service;


import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;




public interface ConsumerService {

   Integer deleteById(Integer consumerId) throws Exception;
   Integer insert(Consumer consumer) throws Exception;
   Integer update(Consumer consumer) throws Exception;

   RespPageBean selectByPage(Consumer consumer, Integer page, Integer pageSize) throws Exception;

}
