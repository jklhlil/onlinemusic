package com.ecjtu.onlinemusic.entity.vo;


public class PageBean {

    private Integer page;
    private Integer pageSize;

    private Integer startIndex;

    public PageBean(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        //计算起始位置
        calStartIndex();
    }


    public void setPage(Integer page) {
        this.page = page;
        //计算起始位置
        calStartIndex();
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        //计算起始位置
        calStartIndex();
    }


    //计算起始位置
    private void calStartIndex() {

        if (this.page != null  && this.page == 0 || this.pageSize != null  && this.pageSize == 0) {
            this.startIndex = 0;
        }
        //  page或者pageSize为负数时，startIndex为空
        if (this.page != null && this.pageSize != null && this.page > 0 && this.pageSize > 0) {
            this.startIndex = (this.page - 1) * this.pageSize;
        }
    }

//不需要setStartIndex
//    public void setStartIndex(Integer startIndex) {
//        this.startIndex = startIndex;
//    }
//
//    public Integer getStartIndex() {
//        return startIndex;
//    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPage() {
        return page;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", startIndex=" + startIndex +
                '}';
    }
}
