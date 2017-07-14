package com.umeng.soexample;

import android.app.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;



public class HomeActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.umeng_blue));

        }
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.umeng_title)).setText(R.string.umeng_home_title);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.auth).setOnClickListener(this);
        findViewById(R.id.getinfo).setOnClickListener(this);
        findViewById(R.id.check).setOnClickListener(this);
        findViewById(R.id.umeng_menu).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.share) {
            Intent shareintent = new Intent(HomeActivity.this, ShareActivity.class);
            startActivity(shareintent);

        } else if (i == R.id.auth) {
            Intent authintent = new Intent(HomeActivity.this, AuthActivity.class);
            startActivity(authintent);

        } else if (i == R.id.getinfo) {
            Intent getintent = new Intent(HomeActivity.this, GetInfoActivity.class);
            startActivity(getintent);

        } else if (i == R.id.check) {
            Intent checkintent = new Intent(HomeActivity.this, CheckActivity.class);
            startActivity(checkintent);

        } else if (i == R.id.umeng_menu) {
            Intent menuintent = new Intent(HomeActivity.this, ShareMenuActivity.class);
            startActivity(menuintent);

        }
    }
}
