package top.bilibililike.iot.widget;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import top.bilibililike.iot.R;

public class ForegroundService extends Service {
    public static final String CHANNEL_ID = "1145141919810";
    public static final int NOTIFICATION_ID = 114514;

    private static int unReportSum = 0;

    private static int errorReportSum = 0;

    private static Notification notification;
    static NotificationCompat.Builder builder;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //处理任务
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("foreground", "onCreate");
        //如果API在26以上即版本为O则调用startForefround()方法启动服务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setForegroundService();
        } else {
            //setForegroundService2();
        }
    }



    /**
     * 通过通知启动服务
     */
    public void setForegroundService() {
        //设定的通知渠道名称
        String channelName = "ReportApp";
        //设置通知的重要程度
        int importance = NotificationManager.IMPORTANCE_DEFAULT;


        //构建通知渠道
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
        channel.setDescription("description");
        //在创建的通知渠道上发送通知
        builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.report_logo) //设置通知图标
                .setContentTitle("当前打卡信息通报")//设置通知标题
                .setContentText("目前未打卡人数："+unReportSum+"人\n当前异常人数："+errorReportSum+"人")//设置通知内容
        ;
        //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        notification = builder.build();
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(NOTIFICATION_ID, notification);
    }


    public static void notifyUpdate(NotificationManager manager,Context context){

        if (manager != null && notification != null){
            //设定的通知渠道名称
            String channelName = "ReportApp";
            //设置通知的重要程度
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //构建通知渠道
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription("description");
            //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            builder.setSmallIcon(R.mipmap.report_logo)
                    .setContentTitle("当前打卡信息通报")
                    .setContentText("目前未打卡人数："+unReportSum+"人\n当前异常人数："+errorReportSum+"人");
            manager.notify(NOTIFICATION_ID,builder.build());
        }else {
            System.out.println("TAGG 通知参数有误");
        }

    }


    public void setForegroundService2() {
        //在创建的通知渠道上发送通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher) //设置通知图标
                .setContentTitle("前台Service")//设置通知标题
                .setContentText("前台Service");//设置通知内容
                //.setAutoCancel(true) //用户触摸时，自动关闭
                //.setOngoing(true);//设置处于运行状态
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(NOTIFICATION_ID, builder.build());
    }

    public void close(){
        stopSelf();
    }

    public static void setUnReportSum(int unReportSum) {
        ForegroundService.unReportSum = unReportSum;
    }

    public static void setErrorReportSum(int errorReportSum) {
        ForegroundService.errorReportSum = errorReportSum;
    }

    public static int getUnReportSum() {
        return unReportSum;
    }

    public static int getErrorReportSum() {
        return errorReportSum;
    }
}

