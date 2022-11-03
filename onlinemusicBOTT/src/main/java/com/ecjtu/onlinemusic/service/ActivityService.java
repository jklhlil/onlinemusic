package com.ecjtu.onlinemusic.service;


import com.ecjtu.onlinemusic.entity.Activity;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;

import java.util.*;


public interface ActivityService {

   Integer deleteById(Integer activityId) throws Exception;
   Integer insert(Activity activity) throws Exception;
   Integer update(Activity activity) throws Exception;

   RespPageBean selectByPage(Activity activity, Integer page, Integer pageSize) throws Exception;

}
