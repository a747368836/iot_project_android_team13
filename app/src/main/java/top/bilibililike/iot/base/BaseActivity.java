package top.bilibililike.iot.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        doBeforeSetContent();
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initViews(savedInstanceState);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);



    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


    protected void doBeforeSetContent(){

    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }
}

