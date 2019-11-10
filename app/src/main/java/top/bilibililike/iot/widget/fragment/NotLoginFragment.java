package top.bilibililike.iot.widget.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import top.bilibililike.iot.R;
import top.bilibililike.iot.base.BaseFragment;
import top.bilibililike.iot.widget.LoginActivity;

public class NotLoginFragment extends BaseFragment {

    @BindView(R.id.container_relative)
    RelativeLayout containerRelative;

    @Override
    public void finishCreateView(Bundle state) {
        containerRelative.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginActivity.class)));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_unlogin;
    }

    @Override
    public void notifyDataSetChanged(Object[] t) {

    }
}
