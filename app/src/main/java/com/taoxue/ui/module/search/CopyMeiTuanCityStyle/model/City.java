package com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model;

import java.io.Serializable;

/**
 * author zaaach on 2016/1/26.
 */
public class City implements Serializable {
    /**
     * ids : 1
     * index : guojiatushuguanshaoerguan
     * name : 国家图书馆少儿馆
     */
    private String ids;
    private String index;
    private String name;
    private String short_name;

    public City(String name, String index, String ids, String short_name) {
        this.ids = ids;
        this.index = index;
        this.name = name;
        this.short_name = short_name;
    }

    public String getIds() {
        return ids;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    @Override
    public String toString() {
        return "City{" +
                "ids='" + ids + '\'' +
                ", index='" + index + '\'' +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                '}';
    }
}
