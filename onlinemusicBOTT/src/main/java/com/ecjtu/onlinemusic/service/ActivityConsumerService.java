package com.ecjtu.onlinemusic.service;


import com.ecjtu.onlinemusic.entity.ActivityConsumer;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;

import java.util.*;


public interface ActivityConsumerService {

   Integer deleteById(Integer activityConsumerId) throws Exception;
   Integer insert(ActivityConsumer activityConsumer) throws Exception;
   Integer update(ActivityConsumer activityConsumer) throws Exception;

   RespPageBean selectByPage(ActivityConsumer activityConsumer, Integer page, Integer pageSize) throws Exception;

   RespPageBean selectConsumerByPage(ActivityConsumer activityConsumer, Integer page, Integer pageSize) throws Exception;
   RespPageBean selectActivityByPage(ActivityConsumer activityConsumer, Integer page, Integer pageSize) throws Exception;

}
