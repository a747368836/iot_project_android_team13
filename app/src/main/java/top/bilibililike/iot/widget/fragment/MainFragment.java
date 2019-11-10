package top.bilibililike.iot.widget.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;


import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.bilibililike.iot.R;
import top.bilibililike.iot.adapter.DeviceRecyAdapter;
import top.bilibililike.iot.base.BaseFragment;
import top.bilibililike.iot.bean.ClimateBean;
import top.bilibililike.iot.bean.DeviceBean;
import top.bilibililike.iot.bean.PostBackBean;
import top.bilibililike.iot.http.DeviceService;
import top.bilibililike.iot.utils.ControlCallback;

public class MainFragment extends BaseFragment implements ControlCallback {
    @BindView(R.id.device_recycler_view)
    RecyclerView deviceRecyclerView;
    @BindView(R.id.progress_bar)
    LinearLayout progressBar;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_device_count)
    TextView tvDeviceCount;

    private DeviceRecyAdapter adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.img_background)
    ImageView imgBackground;
    @BindView(R.id.info_container)
    RelativeLayout infoContainer;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private ClimateBean.DataBean climateTrueBean;

    private DeviceService deviceService;

    @Override
    public void finishCreateView(Bundle state) {
        progressBar.setVisibility(View.VISIBLE);
        initToolbar();
        initData();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    public void notifyDataSetChanged(Object[] t) {

    }

    private void initRecyclerView(List<DeviceBean.DataBean> dataBeans) {
        adapter = new DeviceRecyAdapter(getActivity(), dataBeans,this);
        deviceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        deviceRecyclerView.setAdapter(adapter);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(this::getDeviceData);
        getClimateData();
    }

    private void initToolbar() {
        toolbar.setTitle("常用设备");
        //collapsingToolbar.setExpandedTitleTextAppearance(R.style.TextAppearance_AppCompat);
        //collapsingToolbar.setExpandedTitleTextAppearance(R.style.TextAppearance_MaterialComponents_Subtitle2);
        /*appbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开
                    infoContainer.setVisibility(View.VISIBLE);
                    imgBackground.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);

                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    infoContainer.setVisibility(View.INVISIBLE);
                    imgBackground.setVisibility(View.INVISIBLE);

                    toolbar.setVisibility(View.VISIBLE);
                    toolbar.setTitle("常用设备");

                } else {
                    //中间状态
                    infoContainer.setVisibility(View.INVISIBLE);
                    imgBackground.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.VISIBLE);


                }
            }
        });*/
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.bilibililike.top")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        deviceService = retrofit.create(DeviceService.class);

        deviceService.getDevices()
                .map(DeviceBean::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DeviceBean.DataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<DeviceBean.DataBean> dataBeans) {
                        tvDeviceCount.setText("当前可用设备数："+dataBeans.size());
                        initRecyclerView(dataBeans);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void getDeviceData() {
        deviceService.getDevices()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeviceBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DeviceBean deviceBean) {
                        progressBar.setVisibility(View.INVISIBLE);
                        adapter = new DeviceRecyAdapter(getActivity(),deviceBean.getData(),MainFragment.this);
                        deviceRecyclerView.setAdapter(adapter);
                        //adapter.refreshData(deviceBean);
                        deviceRecyclerView.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                        climateTrueBean = null;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getClimateData() {

        deviceService.getClimate()
                .compose(bindToLifecycle())
                .repeat(2000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClimateBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ClimateBean climateBean) {
                        if (tvTemperature != null && tvHumidity != null){
                            tvTemperature.setText(climateBean.getData().get(0).getTemperature());
                            tvHumidity.setText(climateBean.getData().get(0).getHumidity());
                        }
                        if (climateBean.getCode() == 200) {
                            if (climateTrueBean == null) {
                                climateTrueBean = climateBean.getData().get(0);
                                adapter.refreshData(climateBean.getData().get(0));
                            } else {
                                if (climateTrueBean.getHumidity().equals(climateBean.getData().get(0).getHumidity())
                                        && climateTrueBean.getTemperature().equals(climateBean.getData().get(0).getTemperature())) {

                                } else {
                                    climateTrueBean = climateBean.getData().get(0);
                                    adapter.refreshData(climateBean.getData().get(0));
                                }
                            }


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

    @Override
    public void ledTwinkle(String object) {
        Toast.makeText(getActivity(),"ledTwinkle "+object,Toast.LENGTH_SHORT).show();
        deviceService.postCache("put",object,"flash") .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBackBean postBackBean) {
                        if (postBackBean.getCode() == 200){
                            Toast.makeText(getActivity(),"LED闪烁成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),postBackBean.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void ledChange(String object) {
        Toast.makeText(getActivity(),"ledChange "+object,Toast.LENGTH_SHORT).show();

        deviceService.postCache("put",object,"switch")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBackBean postBackBean) {
                        if (postBackBean.getCode() == 200){
                            Toast.makeText(getActivity(),"LED切换成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),postBackBean.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void currentControl(String order) {
        deviceService.postCache("put","current",order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBackBean postBackBean) {
                        if (postBackBean.getCode() == 200){
                            Toast.makeText(getActivity(),"窗帘控制成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),postBackBean.getMessage(),Toast.LENGTH_SHORT).show();
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

    @Override
    public void fanControl(int id, String order) {

        if (id == 0){
            deviceService.postCache("put","fan0",order)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PostBackBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PostBackBean postBackBean) {
                            if (postBackBean.getCode() == 200){
                                Toast.makeText(getActivity(),"排风扇控制成功",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else {
            deviceService.postCache("put","fan1",order)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PostBackBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PostBackBean postBackBean) {
                            if (postBackBean.getCode() == 200){
                                Toast.makeText(getActivity(),"散热器控制成功",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

    }
}
