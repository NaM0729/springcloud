package com.demo.device_server.util;

import java.util.List;

public class PageSizeDataGrid {
    private int total;
    private List<?> rows;
    private int totalPage;
    private String comomUrl;

    // 状态码
    private Integer status;
    // 状态描述
    private String message;

    public String getComomUrl() {
        return comomUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setComomUrl(String comomUrl) {
        this.comomUrl = comomUrl;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

}
