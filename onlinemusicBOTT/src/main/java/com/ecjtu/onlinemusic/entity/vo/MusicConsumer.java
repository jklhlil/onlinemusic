package com.ecjtu.onlinemusic.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/*
 * @description: 包含音乐id 用户id，用来接收前端参数。
 * @author: 何江涛
 * @create: 2022-06-10
 * @version: 1.0
 */
@Data
public class MusicConsumer {
    @NotNull(message = "音乐id musicId 不能为空")
    private Integer musicId;
    @NotNull(message = "用户id consumerId 不能为空")
    private Integer consumerId;

}
