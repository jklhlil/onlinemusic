package com.ecjtu.onlinemusic.entity.vo;

import com.ecjtu.onlinemusic.entity.ActivityConsumer;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;

import javax.validation.constraints.NotNull;

/*
 * @description: 前端传来的对象
 * @author: 何江涛
 * @create: 2022-06-09
 * @version: 1.0
 */
public class RequestBean<T> {
    private Integer page;
    private Integer pageSize;
//    @NotNull(message = "data属性必须添加。当data属性值为空时，搜索全部。",groups = {select.class})
    T data;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestBean{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", data=" + data +
                '}';
    }
}
