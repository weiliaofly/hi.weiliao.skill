package com.hi.weiliao.skill.vo.common;

import java.util.List;

public class PageBean<T> {

    private Integer pageIndex;
    private Integer pageSxize;
    private Long total;
    private Integer pageNum;
    private List<T> datas;

    public PageBean(){

    }

    public PageBean(Integer pageIndex, Integer pageSxize){
        this.pageIndex = pageIndex;
        this.pageSxize = pageSxize;
    }

    public PageBean(Integer pageIndex, Integer pageSxize, Long total){
        this.pageIndex = pageIndex;
        this.pageSxize = pageSxize;
        this.total = total;
        this.pageNum = Integer.parseInt(Math.ceil(total * 1.0/pageSxize) + "");
    }

    public PageBean(Integer pageIndex, Integer pageSxize, Long total, List<T> datas){
        this.pageIndex = pageIndex;
        this.pageSxize = pageSxize;
        this.total = total;
        this.pageNum = Integer.parseInt(Math.ceil(total * 1.0/pageSxize) + "");
        this.datas = datas;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSxize() {
        return pageSxize;
    }

    public void setPageSxize(Integer pageSxize) {
        this.pageSxize = pageSxize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
