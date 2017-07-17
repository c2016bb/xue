package com.taoxue.ui.module.login.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taoxue.MainActivity;
import com.taoxue.R;

/**
 * Created by Administrator on 2016/11/19.
 * @author yysleep
 */

public class WelcomeFragment extends Fragment {
    private View parentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (parentView != null) {
            return parentView;
        }
        init(inflater,container);
        return parentView;
    }

    public void init(LayoutInflater inflater, @Nullable ViewGroup container) {
        int currentItem = getArguments().getInt(WelcomeActivity.CURRENT_ITEM, -1);
        int layoutId ;
        switch (currentItem) {
            case WelcomeActivity.FRIST_FRAGMENT:
                layoutId = R.layout.fragment_welcome_frist;
                break;
            case WelcomeActivity.CENTER_FRAGMENT:
                layoutId = R.layout.fragment_welcome_center;
                break;
            case WelcomeActivity.LAST_FRAGMENT:
                layoutId = R.layout.fragment_welcome_last;
                break;
            default:
                layoutId = R.layout.fragment_welcome_frist;
                break;
        }
        parentView = inflater.inflate(layoutId, container, false);
        if (currentItem == WelcomeActivity.LAST_FRAGMENT) {
            Button btnStart = (Button) parentView.findViewById(R.id.f_welcome_start_btn);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        parentView = null;
    }
}
