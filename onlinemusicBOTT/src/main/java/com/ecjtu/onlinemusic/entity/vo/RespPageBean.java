package com.ecjtu.onlinemusic.entity.vo;

import com.ecjtu.onlinemusic.entity.Music;
import com.ecjtu.onlinemusic.entity.validatedgroup.select;


import java.util.List;

public class RespPageBean<T> {
    private Integer total;
    private Integer totalPage;
    private List<T> dataList;

    public RespPageBean() { }

    public RespPageBean(Integer total, List<T> dataList) {
        this.total = total;
        this.dataList = dataList;
        // 计算总页数
        calTotalPage();
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        // 计算总页数
        calTotalPage();
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        // 计算总页数
        calTotalPage();
    }


    // 计算总页数
    private void calTotalPage() {
        //  total或者dataList为null时，totalPage为空
        if (this.total != null && this.dataList != null && this.dataList.size() == 0) {
            //  没搜索到
            this.total = 0;
            this.totalPage = 0;
        } else {
            if (total % dataList.size() == 0) { //搜索的数量是一页的整数倍
                this.totalPage = total / dataList.size();
            } else {
                this.totalPage = total / dataList.size() + 1;
            }
        }

    }



    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public List<T> getDataList() {
        return dataList;
    }

    @Override
    public String toString() {
        return "RespPageBean{" +
                "total=" + total +
                ", totalPage=" + totalPage +
                ", dataList=" + dataList +
                '}';
    }

}
