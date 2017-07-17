package com.taoxue.ui.module.search;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.module.home.RecordSQLiteOpenHelper;
import com.taoxue.ui.view.PopWinMenu;
import com.taoxue.utils.UtilStatusBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by User on 2017/4/1.
 */

public abstract class SearchActivity extends Activity {

    @BindView(R.id.et_search)
    public EditText et_search;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.tv_clear)
    TextView tv_clear;
    @BindView(R.id.listView)
    TagContainerLayout listView;
    @BindView(R.id.fa_home_edt_search_selector)
    public TextView mTvSearchSelector;
    @BindView(R.id.tv_tag_view)
    TagContainerLayout tvTagView;

    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private SQLiteDatabase db;
    //    private BaseAdapter adapter;
    private ArrayList<String> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_search_main);
        ButterKnife.bind(this);
        new PopWinMenu(this, mTvSearchSelector);//初始化popwinMenu
        setSelectorTasks();
        initView();

    }

    public abstract void setSelectorTasks();

    /**
     * 点击搜索的业务
     *
     * @param name
     */
    public abstract void onclickSearch(String name);

    /**
     * 初始化标签控件
     */
    public void initTagView(final List<String> tags) {
        tvTagView.setTags(tags);
        tvTagView.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                onclickSearch(tags.get(position) + "");
            }

            @Override
            public void onTagLongClick(int position, String text) {
            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }


    private void initView() {
        // 初始化控件
        UtilStatusBar.setMiuiStatusBarDarkMode(this, true);
        // 清空搜索历史
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        // 搜索框的键盘搜索键点击回调
        et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    searchResult();
                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_tip.setText("搜索历史");
                    ivSearchClear.setVisibility(View.GONE);
                } else {
                    ivSearchClear.setVisibility(View.VISIBLE);
                }

                String tempName = et_search.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });


        listView.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                String name = list.get(position);
                et_search.setText(name);
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
                onclickSearch(name);
            }

            @Override
            public void onTagLongClick(int position, String text) {
            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
        queryData("");
    }

    private void searchResult() {
        // 先隐藏键盘
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
        boolean hasData = hasData(et_search.getText().toString().trim());
        if (!hasData) {
            insertData(et_search.getText().toString().trim());
            queryData("");
        }
        // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
        onclickSearch(et_search.getText().toString().trim());
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor;
        if (tempName.equals("")) {
            cursor = helper.getReadableDatabase().rawQuery(
                    "select id as _id,name from records where name like '%" + tempName + "%' order by id desc limit 9", null);
        } else {
            cursor = helper.getReadableDatabase().rawQuery(
                    "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        }
        list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {

            do {
                list.add(cursor.getString(cursor.getColumnIndex("name")));
            } while (cursor.moveToNext());

        }
        listView.setTags(list);
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext() || tempName.equals("");
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }


    @OnClick({
            R.id.iv_search,
            R.id.back,
            R.id.iv_search_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_search_clear:
                et_search.setText("");
                ivSearchClear.setVisibility(View.GONE);
                break;
            case R.id.iv_search:
                onclickSearch(et_search.getText().toString().trim() + "");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        et_search.setText("");
    }
}