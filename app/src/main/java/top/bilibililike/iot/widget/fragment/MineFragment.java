package top.bilibililike.iot.widget.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import top.bilibililike.iot.R;
import top.bilibililike.iot.adapter.DeviceRecyAdapter;
import top.bilibililike.iot.base.BaseFragment;
import top.bilibililike.iot.utils.Status;
import top.bilibililike.iot.widget.LoginActivity;

public class MineFragment extends BaseFragment {

/*    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_device_count)
    TextView tvDeviceCount;*/

    //@BindView(R.id.device_recycler_view)
    RecyclerView deviceRecyclerView;
    //@BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;



    @Override
    public void finishCreateView(Bundle state) {
        if (Status.getLoginStatus()){
            deviceRecyclerView = getActivity().findViewById(R.id.device_recycler_view);
            initRecycler();
        }else {
            RelativeLayout relativeLayout = getActivity().findViewById(R.id.container_relative);
            relativeLayout.setOnClickListener( v -> {
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            });
        }

    }

    @Override
    protected int getLayoutID() {
        if (Status.getLoginStatus()){
            return R.layout.fragment_mine;
        }else {
            return R.layout.fragment_unlogin;
        }

    }

    @Override
    public void notifyDataSetChanged(Object[] t) {

    }

    private void initRecycler(){
        DeviceRecyAdapter adapter = new DeviceRecyAdapter(getActivity());
        deviceRecyclerView.setAdapter(adapter);
        deviceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }
}
