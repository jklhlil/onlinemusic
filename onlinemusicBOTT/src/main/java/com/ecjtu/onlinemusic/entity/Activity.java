package com.ecjtu.onlinemusic.entity;

import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class Activity implements Serializable {
    @NotNull(message = "活动id不能为空。",groups = {update.class})
    private Integer id;
    private Integer adminId;
    @NotNull(message = "活动标题name不能为空。",groups = {insert.class})  // 前端数据验证。   @Validated分组验证。
    private String name;
    private String content;
    private Date startTime;
    private Date deadLine;
    private String pictureUrl;
    private Integer score;

    @ApiModelProperty(hidden = true)    //在swagger页面隐藏
    private Date createTime;
}

//  CREATE TABLE `activity`  (
//        `activity_id` decimal(8, 0) NOT NULL,
//        `admin_id` decimal(8, 0) NULL DEFAULT NULL,
//         activity_name
//        `activity_content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
//        `activity_starttime` datetime NULL DEFAULT NULL,
//        `activity_deadline` datetime NULL DEFAULT NULL,
//        `activity_createtime` datetime NULL DEFAULT NULL,
//        `picture_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
//        `activity_score` float NULL DEFAULT NULL,
