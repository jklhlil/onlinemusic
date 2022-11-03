package com.ecjtu.onlinemusic.entity;

import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Music implements Serializable{
  @NotNull(message = "音乐id不能为空。",groups = {update.class})
  private Integer id;
  private Integer adminId;
  private String url;
  private String pictureUrl;
  private String singer;
  @NotNull(message = "音乐名name不能为空。",groups = {insert.class})
  private String name;
  private Integer score;

//  @NotNull(message = "查询的音乐是否通过审核。isCheck必填，0没通过，1通过",groups = {select.class})
  private Integer isCheck;

}
//  create table music
//        (
//        music_id             numeric(8,0) not null,
//        admin_id             numeric(8,0),
//        music_url            varchar(512) not null,
//        music_picture_url    varchar(512),
//        singer               varchar(20),
//        music_name           varchar(20),
//        music_score          float,
//        music_is_check       bool,


//        primary key (music_id)
//        );