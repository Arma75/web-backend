package com.buds.foodNutrient.dto;

public class PageRequest {
    protected int page = 1;
    protected int size = 10;
    protected String sort;

    public int getPage() {
        return this.page;
    }
    public int getSize() {
        return this.size;
    }
    public String getSort() {
        return this.sort;
    }
    public int getOffset() {
        return (this.page - 1) * this.size;
    }

    public void setPage(int page) {
        this.page = Math.max(page, 1);
    }
    public void setSize(int size) {
        this.size = Math.max(size, 1);
    }
    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "PageRequest[" +
            "page=" + page + "," +
            "size=" + size + "," +
            "sort=" + sort + "]";
    }
}