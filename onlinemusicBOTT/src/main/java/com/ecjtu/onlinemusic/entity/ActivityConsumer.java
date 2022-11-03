package com.ecjtu.onlinemusic.entity;

import com.ecjtu.onlinemusic.entity.validatedgroup.insert;
import com.ecjtu.onlinemusic.entity.validatedgroup.update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class ActivityConsumer implements Serializable {
    @NotNull(message = "用户活动id不能为空。",groups = {update.class})
    private Integer id;
    @NotNull(message = "活动activityId不能为空。",groups = {insert.class,update.class})  // 前端数据验证。    @Valid默认类验证。@Validated分组验证。
    private Integer activityId;
    @NotNull(message = "用户consumerId不能为空。",groups = {insert.class,update.class})  // 前端数据验证。
    private Integer consumerId;
    private Date joinTime;

    @ApiModelProperty(hidden = true)    //在swagger页面隐藏
    private Activity activity;
    @ApiModelProperty(hidden = true)    //在swagger页面隐藏
    private Consumer consumer;


//    @Override
//    public String toString() {
//        if(activity.getScore()==null&&activity.getContent()==null&&activity.getName()==null){
//            return "ActivityConsumer{" +
//                    "id=" + id +
//                    ", activityId=" + activityId +
//                    ", consumerId=" + consumerId;
//        }
//        return "ActivityConsumer{" +
//                "id=" + id +
//                ", activityId=" + activityId +
//                ", consumerId=" + consumerId +
//                ", activity=" + activity +
//                ", consumer=" + consumer +
//                '}';
//    }
}

//  CREATE TABLE `activity_consumer`  (
//        `activity_consumer_id` decimal(8, 0) NOT NULL,
//        `activity_id` decimal(8, 0) NULL DEFAULT NULL,
//        `consumer_id` decimal(8, 0) NULL DEFAULT NULL,
//        PRIMARY KEY (`activity_consumer_id`) USING BTREE,