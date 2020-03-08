package com.teachpmp.server.entity.base;

public class BasePage {
    private Integer pageIndex;

    private Integer pageSize;

    public Integer getPageIndex() {
        if(pageIndex == null){
            pageIndex = 0;
        }
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        if(pageSize == null){
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
