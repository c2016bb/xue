package com.taoxue.ui.module.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.taoxue.R;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.adapter.CityListAdapter;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.adapter.ResultListAdapter;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.db.DBManager;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model.City;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model.LocateState;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.utils.StringUtils;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.view.SideLetterBar;
import com.taoxue.ui.module.search.bean.SearchSupplierBean;
import com.taoxue.ui.view.PopWinMenu;
import com.taoxue.utils.AppUtils;
import com.taoxue.utils.UtilIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *图书馆搜索
 */
public class SearchLibActivity extends Activity implements View.OnClickListener {
    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ImageView backBtn;
    private ViewGroup emptyView;
    @BindView(R.id.fa_home_edt_search_selector)
    TextView mTvSearchSelector;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private DBManager dbManager;

    private AMapLocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);
        ButterKnife.bind(this);
        new PopWinMenu(this, mTvSearchSelector);//初始化popwinMenu

        dbManager = new DBManager(this);
        initView();
        mCityAdapter = new CityListAdapter(this, dbManager.getAllCities(), true);
        mResultAdapter = new ResultListAdapter(this, null);
        initLocation();
        netWork("");
    }

    /**
     * 网络请求
     */
    private void netWork(String cityName) {
        HttpAdapter.getService().searchList(cityName)
                .enqueue(new OnResponseListener<SearchSupplierBean>(this) {
                    @Override
                    protected void onSuccess(SearchSupplierBean bean) {
                        getStrings((ArrayList)bean.getItem());
                        initData();
                    }
                });
    }
    /**
     * 网络请求
     */
    private void netWork2(String cityName) {
        HttpAdapter.getService().searchList(cityName)
                .enqueue(new OnResponseListener<SearchSupplierBean>(this) {
                    @Override
                    protected void onSuccess(SearchSupplierBean bean) {
                        getStrings((ArrayList)bean.getItem());
                        initData();
                    }
                });
    }


    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        String location = StringUtils.extractLocation(city, district);
                        mCityAdapter.updateLocateState(LocateState.SUCCESS, location);
                        netWork(city);
                    } else {
                        //定位失败
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                        netWork("");
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    private void initData() {

        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(City name) {
                back(name);
            }

            @Override
            public void onLocateClick(String cityName) {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                mLocationClient.startLocation();
            }

            @Override
            public void onQueryAllResultClick() {
                netWork("");
            }


        });

        mListView.setAdapter(mCityAdapter);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                back(mResultAdapter.getItem(position));
            }
        });

    }

    @NonNull
    private void getStrings(ArrayList<City> mAllCities) {
        dbManager.insertAndUpdateData(mAllCities);
    }

    private void initView() {

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        backBtn = (ImageView) findViewById(R.id.back);
        mListView = (ListView) findViewById(R.id.listview_all_city);
        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        searchBox = (EditText) findViewById(R.id.et_search);

        mTvSearchSelector.setText("图书馆");
        searchBox.setHint("请输入图书馆名称");
        mLetterBar.setOverlay(overlay);

        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }


    private void back(City city) {
        AppUtils.showToast(city.toString());
        UtilIntent.intentDIY(this,LibraryHomeActivity.class);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            mResultListView.setVisibility(View.GONE);
        } else if (i == R.id.back) {
            finish();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
    }
}
