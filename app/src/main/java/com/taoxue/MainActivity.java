package com.taoxue;

import android.content.Intent;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;

import com.taoxue.base.BaseActivity;
import com.taoxue.ui.module.classification.AudioPlayerService;
import com.taoxue.ui.module.classification.ClassificationFragment;
import com.taoxue.ui.module.home.HomeFragment;
import com.taoxue.ui.module.mine.MineFragment;
import com.taoxue.ui.module.special.SpecialFragment;
import com.taoxue.ui.view.EasyRadioGroup;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilTools;
import com.taoxue.utils.update.UtilUpdate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.home_bottom)
    EasyRadioGroup easyRadioGroup;

    ClassificationFragment destinationFragment;
    HomeFragment homeFragment;
    MineFragment mineFragment;
    SpecialFragment oneKeyFragment;
    List<Fragment> fragments;
    Fragment currentFragment;
    private long mkeyTime;

    private  Intent service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UtilUpdate.judgeUpdate(this);
        //注册音频Service
        if(service==null) {
            LogUtils.D("开始绑定service");
            service = new Intent(this, AudioPlayerService.class);
            startService(service);
        }
    }

    @Override
    protected void onInit() {
        fragments = new ArrayList<>();
        //初始化Fragment
        fragments.add(homeFragment = (HomeFragment) Fragment.instantiate(this, HomeFragment.class.getName()));
        fragments.add(destinationFragment = (ClassificationFragment) Fragment.instantiate(this, ClassificationFragment.class.getName()));
        fragments.add(oneKeyFragment = (SpecialFragment) Fragment.instantiate(this, SpecialFragment.class.getName()));
        fragments.add(mineFragment = (MineFragment) Fragment.instantiate(this, MineFragment.class.getName()));
        currentFragment = homeFragment;
        switchFragment(null, homeFragment);
        easyRadioGroup.setOnTabSelectListener(new EasyRadioGroup.OnTabSelectListener() {
            @Override
            public void onSelect(View view, int position) {
                if(fragments.size()==(position+1)&& !UtilTools.judgeIsLogin(mActivity)){
                    easyRadioGroup.setPosition(0);
                    switchFragment(currentFragment, fragments.get(0));
                    currentFragment = fragments.get(0);
                }else{
                    switchFragment(currentFragment, fragments.get(position));
                    currentFragment = fragments.get(position);
                }
            }
        });

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            CardView cardView = (CardView) easyRadioGroup.getParent();
//            reflectBg(cardView);
//        }
    }

    private void reflectBg(final CardView cardView) {

        try {
            Field field = cardView.getClass().getDeclaredField("mCardViewDelegate");
            field.setAccessible(true);
            Object object = field.get(cardView);
            Method method = object.getClass().getDeclaredMethod("setShadowPadding", int.class, int.class, int.class, int.class);
            method.invoke(object, 0, 0, 0, 0);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        cardView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = cardView.getBackground();
                try {
                    Field field = drawable.getClass().getDeclaredField("mCardBounds");
                    field.setAccessible(true);
                    RectF rectF = (RectF) field.get(drawable);
                    rectF.set(cardView.getLeft(), cardView.getTop(), cardView.getRight(), cardView.getBottom());
                    drawable.invalidateSelf();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }, 100);
    }

    /**
     * 切换Fragment
     *
     * @param from
     * @param to
     */
    private void switchFragment(Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            transaction.add(R.id.frag_container, to);
        } else {
            if (!getSupportFragmentManager().getFragments().contains(to)) {
                transaction.add(R.id.frag_container, to);
            }
            transaction.hide(from);
            transaction.show(to);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        stopAudioService();//结束音频线程
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                showToast("再按一次退出程序");
            } else {
               stopAudioService();//结束音频线程
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void stopAudioService(){
        if (service!=null) {
            stopService(service);
        }
    }
}
