package top.bilibililike.iot.widget.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.bilibililike.iot.R;
import top.bilibililike.iot.adapter.DeviceRecyAdapter;
import top.bilibililike.iot.adapter.InfoRecyAdapter;
import top.bilibililike.iot.base.BaseFragment;
import top.bilibililike.iot.bean.ClimateBean;
import top.bilibililike.iot.bean.DeviceBean;
import top.bilibililike.iot.bean.PostBackBean;
import top.bilibililike.iot.http.DeviceService;
import top.bilibililike.iot.utils.ControlCallback;
import top.bilibililike.iot.utils.ToastUtil;

public class MainFragment extends BaseFragment implements ControlCallback {
    @BindView(R.id.device_recycler_view)
    RecyclerView deviceRecyclerView;
    @BindView(R.id.progress_bar)
    LinearLayout progressBar;
    @BindView(R.id.recycler_info)
    RecyclerView recyclerInfo;


    private DeviceRecyAdapter deviceAdapter;
    private InfoRecyAdapter infoAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;



    private ClimateBean.DataBean climateTrueBean;

    private DeviceService deviceService;

    @Override
    public void finishCreateView(Bundle state) {
        progressBar.setVisibility(View.VISIBLE);
        initToolbar();
        initInfoRecyclerView();
        initData();
        getClimateData();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    public void notifyDataSetChanged(Object[] t) {

    }

    private void initDeviceRecyclerView(List<DeviceBean.DataBean> dataBeans) {
        deviceAdapter = new DeviceRecyAdapter(getActivity(), dataBeans, this);
        deviceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        deviceRecyclerView.setAdapter(deviceAdapter);
    }

    private void initInfoRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        recyclerInfo.setLayoutManager(gridLayoutManager);
        infoAdapter = new InfoRecyAdapter(getActivity(),climateTrueBean);
        recyclerInfo.setAdapter(infoAdapter);

    }

    private void initToolbar() {
        toolbar.setTitle("我的家");
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
                        List<DeviceBean.DataBean> dataBean = new ArrayList<>();
                        for (int i = 0; i < dataBeans.size(); i++) {
                            if (!dataBeans.get(i).getType().equals("climate")){
                                dataBean.add(dataBeans.get(i));
                            }
                        }
                        initDeviceRecyclerView(dataBean);
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
                        deviceAdapter = new DeviceRecyAdapter(getActivity(), deviceBean.getData(), MainFragment.this);
                        deviceRecyclerView.setAdapter(deviceAdapter);
                        //deviceAdapter.refreshData(deviceBean);
                        deviceRecyclerView.setVisibility(View.VISIBLE);

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

    private void getClimateData(){
        deviceService.getClimate()
                .compose(bindToLifecycle())
                .repeat(5000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClimateBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ClimateBean climateBean) {
                        ClimateBean.DataBean dataBean = climateBean.getData().get(0);
                        if (climateTrueBean == null) {
                            climateTrueBean = dataBean;
                            infoAdapter.refreshData(dataBean);
                            return;
                        }
                        if (climateTrueBean.getHumidity().equals(dataBean.getHumidity())
                            && climateTrueBean.getTemperature().equals(dataBean.getTemperature())
                            && climateTrueBean.getSmoke().equals(dataBean.getSmoke())){

                        }else {
                            infoAdapter.refreshData(dataBean);
                            climateTrueBean = dataBean;
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
        deviceService.postCache("put", object, "flash").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBackBean postBackBean) {
                        if (postBackBean.getCode() == 200) {
                            ToastUtil.show("LED闪烁成功");
                        } else {
                            ToastUtil.show(postBackBean.getMessage());
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
        deviceService.postCache("put", object, "switch")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBackBean postBackBean) {
                        if (postBackBean.getCode() == 200) {
                            ToastUtil.show("LED切换成功");
                        } else {
                            ToastUtil.show(postBackBean.getMessage());
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
        deviceService.postCache("put", "current", order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBackBean postBackBean) {
                        if (postBackBean.getCode() == 200) {
                            ToastUtil.show("窗帘"+order+"成功");
                        } else {
                            ToastUtil.show(postBackBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void fanControl(int id, String order) {

        if (id == 0) {
            deviceService.postCache("put", "fan0", order)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PostBackBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PostBackBean postBackBean) {
                            if (postBackBean.getCode() == 200) {
                                ToastUtil.show("排风扇"+order+"成功");
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
        } else {
            deviceService.postCache("put", "fan1", order)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PostBackBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PostBackBean postBackBean) {
                            if (postBackBean.getCode() == 200) {
                                ToastUtil.show("散热器"+order+"成功");
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

    @Override
    public void alarmControl(String order) {
        deviceService.postCache("put", "alarm", order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBackBean postBackBean) {
                        if (postBackBean.getCode() == 200) {
                            ToastUtil.show("报警器"+order+"成功");
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
