package com.ecjtu.onlinemusic.entity.vo;

import org.apache.ibatis.annotations.Insert;

public enum ResultEnum {
    //1000系列  通用错误
    SUCCESS(1000,"操作成功"),
    FAILED(1001,"接口错误"),
    VALIDATE_FAILED(1002,"参数校验失败"),
    ERROR(1003,"未知错误，后端错误信息在data中"),
    FILE_SIZE_OVERFLOW(1004,"上传文件字节被限制"),
    JSON_ERROR(1005,"传入的数据json格式错误。"),


    //2000系列 用户错误
    LOGIN_SUCCESS(2000,"登陆成功"),
    REGISTER_SUCCESS(2001,"注册成功"),
    USER_NOT_EXIST(2002,"用户不存在"),
    USER_LOGIN_FAIL(2004,"用户不存在或密码错误"),
    USER_NOT_LOGIN(2006,"用户还未登录，请先登录"),
    NO_PERMISSION(2008,"权限不足，请联系管理员"),

    //管理员
    ADMIN_LOGIN_FAIL(2500,"管理员不存在或密码错误"),

    //8000 通用
    delete_failed(8001,"删除失败，所删数据不存在或其他原因"),
    insert_failed(8002,"添加失败"),
    update_failed(8003,"更新失败，id不存在或其他原因"),
    select_failed(8004,"查询失败，查到的数据小于等于0条"),
    select_failed2(8004,"查询失败。传值不匹配，请传入page"),

    delete_success(8011,"删除成功"),
    insert_success(8012,"添加成功"),
    update_success(8013,"更新成功"),
    select_success(8014,"查询成功"),

    //520系列 玩
    great(520,"你是傻逼"),
    gege(521,"gege gege");




    private final Integer code;
    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
