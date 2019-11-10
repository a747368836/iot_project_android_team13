package top.bilibililike.iot.utils;

import android.app.Application;
import android.widget.Toast;

public class ToastUtil {
    public static void show(String content){
        Toast toast = Toast.makeText(MyApp.getContext(),null,Toast.LENGTH_SHORT);
        toast.setText(content);
        toast.show();
    }

}
