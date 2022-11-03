package com.ecjtu.onlinemusic.service;

import com.ecjtu.onlinemusic.entity.SubscribeCollect;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;

import java.util.*;


public interface SubscribeCollectService {

   Integer deleteById(Integer subscribeCollectId) throws Exception;
   Integer insert(SubscribeCollect subscribeCollect) throws Exception;
   Integer update(SubscribeCollect subscribeCollect) throws Exception;

   RespPageBean selectByPage(SubscribeCollect subscribeCollect, Integer page, Integer pageSize) throws Exception;

   RespPageBean selectMusicByPage(SubscribeCollect subscribeCollect, Integer page, Integer pageSize) throws Exception;
   RespPageBean selectConsumerByPage(SubscribeCollect subscribeCollect, Integer page, Integer pageSize) throws Exception;

}
