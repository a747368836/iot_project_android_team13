package top.bilibililike.iot.utils;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

public class MyApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(getApplicationContext());
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }


}
