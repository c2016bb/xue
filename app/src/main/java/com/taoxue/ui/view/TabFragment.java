package com.taoxue.ui.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.taoxue.R;
import com.taoxue.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public  class TabFragment extends Fragment
{
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private int layoutId=0;

    private RecyclerView mRecyclerView;
    // private TextView mTextView;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            layoutId = getArguments().getInt(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(layoutId, container, false);
        LogUtils.D("R.layout.vp_book_detail--->"+R.layout.vp_book_detail);
//        LogUtils.D("view--->"+view.toString()+"layoutId--->"+layoutId);
        if (onInitView!=null){
            onInitView.showView(view,layoutId);
        }
//        mRecyclerView = (RecyclerView) view
//                .findViewById(R.id.id_stickynavlayout_innerscrollview);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        // mTextView = (TextView) view.findViewById(R.id.id_info);
//        // mTextView.setText(mTitle);
//        for (int i = 0; i < 50; i++)
//        {
//            mDatas.add(mTitle + " -> " + i);
//        }
//        mRecyclerView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.tab_item, mDatas)
//        {
//            @Override
//            public void convert(ViewHolder holder, String o)
//            {
//                holder.setText(R.id.id_info, o);
//            }
//        });

        return view;

    }
    public  interface  OnInitView{
      void   showView(View view,int layoutId);
    } ;

    public OnInitView onInitView;

    public void  setOnInitView(OnInitView onInitView){
        this.onInitView=onInitView;
    }

    public static TabFragment newInstance(int layoutId)
    {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, layoutId);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
}
