package top.bilibililike.iot.widget.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.litepal.LitePal;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import top.bilibililike.iot.R;
import top.bilibililike.iot.base.BaseFragment;
import top.bilibililike.iot.bean.UserBean;
import top.bilibililike.iot.view.UserFragmentItem;
import top.bilibililike.iot.widget.MainActivity;


public class MineFragment extends BaseFragment {

    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.item_detail_information)
    UserFragmentItem itemDetailInformation;
    @BindView(R.id.item_change_password)
    UserFragmentItem itemChangePassword;
    @BindView(R.id.item_connect_developer)
    UserFragmentItem itemConnectDeveloper;
    @BindView(R.id.item_exit_login)
    UserFragmentItem itemExitLogin;
    @BindView(R.id.mine_main_setting)
    UserFragmentItem mineMainSetting;

    private UserBean.DataBean userBean;


    @Override
    public void finishCreateView(Bundle state) {
        initData();
        initView();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    public void notifyDataSetChanged(Object[] t) {

    }


    private void initData() {
        UserBean.DataBean dataBean = LitePal.findLast(UserBean.DataBean.class);
        if (dataBean != null) {
            this.userBean = dataBean;
        }
    }


    private void initView() {
        if (userBean != null) {
            Glide.with(this)
                    .load(userBean.getAvatar())
                    .into(avatar);
            tvNickname.setText(userBean.getNickname());
        }

        itemExitLogin.setOnClickListener( v -> {
            LitePal.deleteAll(UserBean.DataBean.class);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getActivity().startActivity(intent);
            getActivity().finish();
        });

    }
}
