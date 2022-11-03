package com.ecjtu.onlinemusic.entity.vo;

//VO:value object值对象。在基础之上附加。

import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//不加T，类里面会找不到T
public class ResultVO<T> {

    private Integer code;
    private String msg;
    private T data;

    /**
     * 更新操作成功
     */
    public ResultVO(){
        this.code= ResultEnum.SUCCESS.getCode();
        this.msg= ResultEnum.SUCCESS.getMsg();
    }

    /**
     * 查询操作成功
     */
    public ResultVO(T data){
        if(data==null){
            this.code= ResultEnum.SUCCESS.getCode();
            this.msg= ResultEnum.SUCCESS.getMsg();
        }else{
            this.code= ResultEnum.SUCCESS.getCode();
            this.msg= ResultEnum.SUCCESS.getMsg();
            this.data=data;
        }
    }

    /**
     * 发生异常
     */
    public ResultVO(ResultEnum resultEnum){
        this.code= resultEnum.getCode();
        this.msg= resultEnum.getMsg();
    }


    /**
     * 数据校验错误
     */
    public ResultVO(ResultEnum resultEnum, T data){
        this.code= resultEnum.getCode();
        this.msg= resultEnum.getMsg();
        this.data=data;
    }

    /**
     * 输出json 响应信息
     * @param response
     * @throws IOException
     */
    public void out(HttpServletResponse response)throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(this.toString());
    }

    public ResultVO(String msg){
            this.code= ResultEnum.FAILED.getCode();
            this.msg= msg;

    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
