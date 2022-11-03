package com.ecjtu.onlinemusic.entity;


import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class SubscribeCollect implements Serializable {

    private Integer id;
    @NotNull(message = "音乐musicId不能为空。",groups = {insert.class,update.class})
    private Integer musicId;
    @NotNull(message = "用户consumerId不能为空。",groups = {insert.class,update.class})
    private Integer consumerId;
    private Integer isCollect;
    private Integer isSubscribe;
    private  Date modifyTime;

    @ApiModelProperty(hidden = true)    //在swagger页面隐藏
    private Consumer consumer;
    @ApiModelProperty(hidden = true)    //在swagger页面隐藏
    private Music music;
}

//    CREATE TABLE `subscribe_collect`  (
//
//        `subscribe_collect_id` decimal(8,0)NOT NULL,
//        `music_id` decimal(8,0)NULL DEFAULT NULL,
//        `consumer_id` decimal(8,0)NULL DEFAULT NULL,
//        `is_collect` tinyint(1)NULL DEFAULT NULL,
//        `is_subscribe` tinyint(1)NULL DEFAULT NULL,

