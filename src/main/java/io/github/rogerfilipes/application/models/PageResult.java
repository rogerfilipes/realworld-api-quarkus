package io.github.rogerfilipes.application.models;

import java.util.List;

public class PageResult<T> {
    private List<T> result;
    private Integer total;

    public PageResult() {
    }

    public PageResult(List<T> result, Integer total) {
        this.result = result;
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public PageResult<T> setResult(List<T> result) {
        this.result = result;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public PageResult<T> setTotal(Integer total) {
        this.total = total;
        return this;
    }
}
