package com.taoxue.ui.module.search.bean;

import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model.City;

import java.util.List;

/**
 * Created by User on 2017/6/20.
 */

public class SearchSupplierBean extends BaseResultModel {

    private List<City> item;

    public List<City> getItem() {
        return item;
    }

    public void setItem(List<City> item) {
        this.item = item;
    }

   
}
