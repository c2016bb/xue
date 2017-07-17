package com.taoxue.ui.module.search.bean;

import com.taoxue.ui.model.BaseResultModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 2017/6/20.
 */

public class ResoureHotSearchKeyBean extends BaseResultModel {

    private List<ItemBean> item;

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean  implements Serializable {
        /**
         * keyword : 奥特曼
         */

        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
