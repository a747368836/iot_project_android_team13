package top.bilibililike.iot.widget.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;
import top.bilibililike.iot.R;
import top.bilibililike.iot.adapter.DeviceRecyAdapter;
import top.bilibililike.iot.base.BaseFragment;

public class MainFragment extends BaseFragment {
    @BindView(R.id.device_recycler_view)
    RecyclerView deviceRecyclerView;

    DeviceRecyAdapter adapter;
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


    @Override
    public void finishCreateView(Bundle state) {
        initRecyclerView();
        initToolbar();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    public void notifyDataSetChanged(Object[] t) {

    }

    private void initRecyclerView() {
        adapter = new DeviceRecyAdapter(getActivity());
        deviceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        deviceRecyclerView.setAdapter(adapter);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
        });
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
}
