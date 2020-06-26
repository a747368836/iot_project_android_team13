package top.bilibililike.iot.widget;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Collections;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.bilibililike.iot.ClientHolder;
import top.bilibililike.iot.R;
import top.bilibililike.iot.base.BaseActivity;
import top.bilibililike.iot.bean.ReportHistoryBean;
import top.bilibililike.iot.bean.ReportResultBean;
import top.bilibililike.iot.bean.WarningBean;
import top.bilibililike.iot.http.ReportService;

public class HistoryActivity extends BaseActivity {

    RecyclerView recyclerView;
    HistoryAdapter adapter;
    ReportService reportService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.recycker_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initNetWorkUtil();

        adapter = new HistoryAdapter(R.layout.item_history);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        getHistory(intent.getStringExtra("userId"));

    }

    private void initNetWorkUtil(){
        reportService = ClientHolder.getInstance().create(ReportService.class);
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
                        if (reportResultBean.getCode() == 200){
                            Collections.reverse(reportResultBean.getData());
                            adapter.addData(reportResultBean.getData());
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


}
