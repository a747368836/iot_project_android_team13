package top.bilibililike.iot.utils;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationBroadcastReceiver extends BroadcastReceiver{
    public static final String TYPE = "type";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra("type", -1);
        System.out.println("TAGG 收到广播 type = " + type + " action = " + action);

        if (type != -1) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
        }

        if ("notification_clicked".equals(action)) {
            Toast.makeText(context,"普通点击",Toast.LENGTH_SHORT).show();
        }

        if ("notification_cancelled".equals(action)) {
            Toast.makeText(context,"普通删除",Toast.LENGTH_SHORT).show();
        }

        if ("notification_bing_cancelled".equals(action)) {
            Toast.makeText(context,"绑定删除",Toast.LENGTH_SHORT).show();
            NotificationBindHelper.getInstance().onClear();
        }

        if ("notification_bing_clicked".equals(action)) {
            Toast.makeText(context,"绑定点击",Toast.LENGTH_SHORT).show();
            NotificationBindHelper.getInstance().onClick();
        }

    }
}
