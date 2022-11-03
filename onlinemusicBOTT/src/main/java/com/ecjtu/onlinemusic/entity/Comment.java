package com.ecjtu.onlinemusic.entity;


import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {
    @NotNull(message = "评论id不能为空。", groups = {update.class})
    private Integer id;
    private Integer adminId;
    @NotNull(message = "用户consumerId不能为空。",groups = {insert.class,update.class})
    private Integer consumerId;
    @NotNull(message = "音乐musicId不能为空。",groups = {insert.class,update.class})
    private Integer musicId;
    @NotNull(message = "评论内容content不能为空。", groups = {insert.class})
    private String content;

//    @NotNull(message = "查询的评论是否通过审核。isCheck必填，0没通过，1通过",groups = {select.class})
    private Integer isCheck;

    @ApiModelProperty(hidden = true)    //在swagger页面隐藏
    private Date time;
    @ApiModelProperty(hidden = true)    //在swagger页面隐藏
    private Consumer consumer;
    @ApiModelProperty(hidden = true)    //在swagger页面隐藏
    private Music music;
}

//  create table comment
//        (
//        comment_id           numeric(8,0) not null,
//        admin_id             numeric(8,0),
//        consumer_id          numeric(8,0),
//        music_id             numeric(8,0),
//        comment_content      varchar(512) not null,
//        comment_time         date,
//        comment_is_check     bool,


//        primary key (comment_id)
//        );


// select *
//         FROM music m,`comment` c
//         WHERE
//         m.music_id=c.music_id AND c.consumer_id=100