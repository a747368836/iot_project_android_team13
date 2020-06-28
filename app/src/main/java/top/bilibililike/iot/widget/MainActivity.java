package top.bilibililike.iot.widget;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.litepal.LitePal;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.bilibililike.iot.ClientHolder;
import top.bilibililike.iot.R;
import top.bilibililike.iot.base.BaseActivity;
import top.bilibililike.iot.bean.PostBackBean;
import top.bilibililike.iot.bean.ReportBean;
import top.bilibililike.iot.bean.ReportHistoryBean;
import top.bilibililike.iot.bean.ReportHistoryBean.DataBean;
import top.bilibililike.iot.bean.ReportResultBean;
import top.bilibililike.iot.bean.UserBean;
import top.bilibililike.iot.bean.WarningBean;
import top.bilibililike.iot.http.LoginService;
import top.bilibililike.iot.http.ReportService;
import top.bilibililike.iot.utils.NotificationBindHelper;
import top.bilibililike.iot.utils.NotificationBroadcastReceiver;
import top.bilibililike.iot.utils.ToastUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    TextView idTextView;
    TextView nameTextView;
    EditText phoneEditText;
    EditText detailLocEditText;
    TextView temperatureSelectTextView;
    TextView patientSelectTextView;
    EditText extraEditText;
    CardView reportContainer;
    TextView reportTextView;
    TextView historyTextView;

    RelativeLayout phoneContainer;
    RelativeLayout detailLocContainer;
    RelativeLayout extrasContainer;

    TextView deadLineTextView;

    ReportBean reportBean;

    ReportService reportService;

    NotificationManager mNotificationManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        //Bugly.init(this, "32d45c257c", true);
        initView();
        initOldData();
        initTimeText();
        initNetWorkUtil();
        checkHasReported();
    }

    private void initView(){
        idTextView = findViewById(R.id.id_textView);
        nameTextView = findViewById(R.id.name_textView);
        phoneEditText = findViewById(R.id.phoneNum_Edittext);
        detailLocEditText = findViewById(R.id.detail_location_Edittext);
        temperatureSelectTextView = findViewById(R.id.temperature_select_textView);
        patientSelectTextView = findViewById(R.id.patient_select_textView);
        extraEditText = findViewById(R.id.extras_edittext);
        reportContainer = findViewById(R.id.report_container_cardView);
        reportTextView = findViewById(R.id.report_textView);
        historyTextView = findViewById(R.id.history_textView);
        deadLineTextView = findViewById(R.id.deadline_textView);
        phoneContainer = findViewById(R.id.phoneNum_container);
        detailLocContainer = findViewById(R.id.detail_location_container);
        extrasContainer = findViewById(R.id.extras_container);




        phoneContainer.setOnClickListener( v -> showInputMethod(phoneEditText));
        detailLocContainer.setOnClickListener( v -> showInputMethod(detailLocEditText));
        extrasContainer.setOnClickListener( v -> showInputMethod(extraEditText));
        historyTextView.setOnClickListener( v -> turn2History());

        temperatureSelectTextView.setOnClickListener(this);
        patientSelectTextView.setOnClickListener(this);
        reportContainer.setOnClickListener(this);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private void turn2History(){
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        intent.putExtra("userId",idTextView.getText().toString());
        startActivity(intent);
    }

    private void showInputMethod(EditText editText){
        editText.requestFocus();
        editText.setSelection(editText.getText().toString().length());
        InputMethodManager inputManager = (InputMethodManager)editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null){
            inputManager.showSoftInput(editText,0);
        }
    }

    private void initTimeText(){
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        deadLineTextView.setText(t1);
    }

    private void initOldData(){
        reportBean = LitePal.find(ReportBean.class,1);
        if (reportBean != null && reportBean.userId != null){
            idTextView.setText(reportBean.getUserId());
            nameTextView.setText(reportBean.getUserName());
            if (!reportBean.isSick()){
                temperatureSelectTextView.setText(commonSelectItems[2]);
            }
            if (!reportBean.isFamilySick()){
                patientSelectTextView.setText(commonSelectItems[2]);
            }
            if (reportBean.getAddress() != null){
                detailLocEditText.setText(reportBean.getAddress());
            }
            if (reportBean.getPhoneNum() != null){
                phoneEditText.setText(reportBean.getPhoneNum());
            }
        }
    }


    private void initIntent() {
       /* Intent intent = getIntent();
        userBean = new UserBean.DataBean();
        String nickname = intent.getStringExtra("nickname");
        String avatar = intent.getStringExtra("avatar");
        if (nickname != null && !nickname.isEmpty() && avatar != null && !avatar.isEmpty()){
            userBean.setNickname(nickname);
            userBean.setAvatar(avatar);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private String[] commonSelectItems = new String[]{"待选择", "是", "否"};

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.temperature_select_textView) {
            showSingleChoiceDialog("", commonSelectItems, (pos, items) -> {
                temperatureSelectTextView.setText(items[pos]);
                if (pos == 2) {
                    temperatureSelectTextView.setTextColor(Color.RED);
                } else {
                    temperatureSelectTextView.setTextColor(Color.parseColor("#CA0f1f0f"));
                }
            });
        } else if (v.getId() == R.id.patient_select_textView) {
            showSingleChoiceDialog("", commonSelectItems, (pos, items) -> {
                patientSelectTextView.setText(items[pos]);
                if (pos == 1) {
                    patientSelectTextView.setTextColor(Color.RED);
                } else {
                    patientSelectTextView.setTextColor(Color.parseColor("#CA0f1f0f"));
                }
            });
        } else if (v.getId() == R.id.report_container_cardView) {
            if (TextUtils.isEmpty(phoneEditText.getText().toString()) || TextUtils.isEmpty(detailLocEditText.getText().toString())
            || TextUtils.equals("请选择",temperatureSelectTextView.getText().toString())
                    || TextUtils.equals("请选择",patientSelectTextView.getText().toString())){
                ToastUtil.show("请填写完整再打卡");
                return;
            }
            saveBeanAndReport();
        } else if (v.getId() == R.id.history_textView) {

        }
    }

    private void saveBeanAndReport(){
        ReportBean reportBean = LitePal.find(ReportBean.class,1);
        if (reportBean == null){
            reportBean = new ReportBean();
        }
        reportBean.setUserId(idTextView.getText().toString());
        reportBean.setUserName(nameTextView.getText().toString());
        reportBean.setAddress(detailLocEditText.getText().toString());
        reportBean.setFamilySick(patientSelectTextView.getText().toString().equals("是"));
        reportBean.setSick(temperatureSelectTextView.getText().toString().equals("否"));
        reportBean.setTime(System.currentTimeMillis()+"");
        reportBean.setPhoneNum(phoneEditText.getText().toString());
        reportBean.save();
        reportBean.update(1);
        report(reportBean);
    }

    private void report(ReportBean reportBean){
        reportService.report(reportBean.getUserId(),
                reportBean.getUserName(),reportBean.getTime(),
                reportBean.isSick(),reportBean.getAddress())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBackBean postBackBean) {
                        if (postBackBean.getCode() == 200){
                            ToastUtil.show("打卡成功！");
                            setReportState(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getHistory(String userId){
        reportService.getReportHistory(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReportHistoryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReportHistoryBean reportResultBean) {
                        System.out.println("TAGG getHistort|onNext");
                        if (reportResultBean.getCode() == 200 && reportResultBean.getData().size() > 0){
                            Collections.reverse(reportResultBean.getData());

                            DataBean dataBean = reportResultBean.getData().get(0);
                            long latestTime = Long.parseLong(dataBean.getTime());
                            System.out.println("TAGG latestTime = " + latestTime);
                            Date date = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String timeStr = simpleDateFormat.format(date);

                            long time = (new SimpleDateFormat("yyyy-MM-dd")).parse(timeStr,new ParsePosition(0)).getTime();
                            if (latestTime > time){
                                setReportState(true);
                            }
                            System.out.println("TAGG time = " + time);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void checkHasReported(){
        System.out.println("TAGG checkHasReported");
        getHistory(idTextView.getText().toString());
        Disposable disposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> reportService.getReportInfo()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<WarningBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(WarningBean warningBean) {
                                if (warningBean.getCode() == 200){
                                    WarningBean.DataBean dataBean = warningBean.getData();
                                    List absentList = dataBean.getAbsentList();
                                    List patientList = dataBean.getSickList();
                                    startWarningService(absentList.size(),patientList.size());

                                    /*for (int i = 0; i < absentList.size(); i++) {
                                        String id = (String) ((List) absentList.get(i)).get(0);
                                        if (idTextView.getText().toString().equals(id)){
                                            setReportState(true);
                                            break;
                                        }
                                    }
                                    for (int i = 0; i < patientList.size(); i++) {
                                        String id = (String) ((List) patientList.get(i)).get(0);
                                        if (idTextView.getText().toString().equals(id)){
                                            setReportState(true);
                                            break;
                                        }
                                    }*/
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }


    private void startWarningService(int absentSize,int patientSize){
        if (absentSize == ForegroundService.getUnReportSum() && patientSize == ForegroundService.getErrorReportSum()){
            return;
        }
        ForegroundService.setUnReportSum(absentSize);
        ForegroundService.setErrorReportSum(patientSize);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBindBuilder = new NotificationCompat.Builder(this, NotificationBindHelper.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSmallIcon(R.mipmap.report_logo) //设置通知图标
                .setContentTitle("当前打卡信息通报")//设置通知标题
                .setContentText("目前未打卡人数："+absentSize+"人\n当前异常人数："+patientSize+"人")//设置通知内容
                .setSound(defaultSoundUri)
                .setContentIntent(getPendingIntent("notification_bing_clicked", NotificationBindHelper.NOTIFICATION_ID))
                .setDeleteIntent(getPendingIntent("notification_bing_cancelled", NotificationBindHelper.NOTIFICATION_ID));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NotificationBindHelper.CHANNEL_ID, "绑定通知", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("description");
            mNotificationManager.createNotificationChannel(channel);
        }else {
            mNotificationManager.notify(ForegroundService.NOTIFICATION_ID, notificationBindBuilder.build());
        }
        startService(new Intent(MainActivity.this, ForegroundService.class));
        ForegroundService.notifyUpdate(mNotificationManager,this);
    }

    private PendingIntent getPendingIntent(String action, int type) {
        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        intent.setAction(action);
        intent.putExtra(NotificationBroadcastReceiver.TYPE, type);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

    }

    private void initNetWorkUtil(){
        reportService = ClientHolder.getInstance().create(ReportService.class);
    }

    private void setReportState(boolean reported){
        if (reported){
            reportContainer.setCardBackgroundColor(Color.GRAY);
            reportContainer.setClickable(false);
            reportTextView.setText("已完成今日打卡");
        }else {
            reportContainer.setCardBackgroundColor(Color.parseColor("#FF66EEB9"));
            reportContainer.setClickable(true);
            reportTextView.setText("打卡");
        }
    }

    private void showSingleChoiceDialog(String title, String[] items, SelectCallBack callBack) {
        int[] pos = new int[]{0};
        final AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle(title);
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                (dialog, which) -> pos[0] = which);
        singleChoiceDialog.setPositiveButton("确定",
                (dialog, which) -> callBack.onSelected(pos[0], items));
        singleChoiceDialog.show();
    }

    interface SelectCallBack {
        void onSelected(int pos, String[] items);
    }


}
