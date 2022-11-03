package com.ecjtu.onlinemusic.dao;


import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.vo.PageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface MusicDao {

   int deleteById(Integer musicId);
   int insert(Music music);
   int update(Music music);

   List<Music> selectByPage(Music music,@Param("page") PageBean pageBean);
   Music selectById(Integer id);
}
