package com.ecjtu.onlinemusic.service;


import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.vo.RespPageBean;

import java.util.*;


public interface MusicService {

   Integer deleteById(Integer musicId) throws Exception;
   Integer insert(Music music) throws Exception;
   Integer update(Music music) throws Exception;

   RespPageBean selectByPage(Music music, Integer page, Integer pageSize) throws Exception;

}
