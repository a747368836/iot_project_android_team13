package top.bilibililike.iot.widget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tencent.bugly.Bugly;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import top.bilibililike.iot.R;
import top.bilibililike.iot.base.BaseActivity;
import top.bilibililike.iot.base.BaseFragment;
import top.bilibililike.iot.bean.UserBean;
import top.bilibililike.iot.utils.Status;
import top.bilibililike.iot.utils.ViewPagerAdapter;
import top.bilibililike.iot.view.NotMoveViewPager;
import top.bilibililike.iot.widget.fragment.MainFragment;
import top.bilibililike.iot.widget.fragment.MineFragment;
import top.bilibililike.iot.widget.fragment.NotLoginFragment;
import top.bilibililike.iot.widget.fragment.SituationFragment;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fragment_container)
    NotMoveViewPager viewPager;
    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNav;

    List<BaseFragment> fragmentList ;

    MineFragment mineFragment ;
    UserBean.DataBean userBean;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bugly.init(this,"32d45c257c",true);
        initIntent();
        initViewpager();
    }

    private void initViewpager(){
        UserBean.DataBean userBean = LitePal.findLast(UserBean.DataBean.class);
        if (userBean != null && userBean.getAvatar() != null){
            mineFragment = new MineFragment();
            fragmentList = new ArrayList<>();
            fragmentList.add(new MainFragment());
            fragmentList.add(mineFragment);
        }else {
            fragmentList = new ArrayList<>();
            fragmentList.add(new NotLoginFragment());
            fragmentList.add(new NotLoginFragment());
        }
        Log.d("MainActivity",(userBean == null ? "userBean == null":"userBean != null") + "\ncount = "+LitePal.count(UserBean.DataBean.class));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setNoScroll(true);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int id = position == 0 ? R.id.nav_home : position == 1
                        ? R.id.nav_mine :R.id.nav_home;
                bottomNav.setSelectedItemId(id);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    viewPager.setCurrentItem(0,false);
                    break;
                case R.id.nav_mine:
                    viewPager.setCurrentItem(1,false);
                    break;
            }
            return true;
        });

    }

    private void initIntent(){
        Intent intent = getIntent();
        userBean = new UserBean.DataBean();
        String nickname = intent.getStringExtra("nickname");
        String avatar = intent.getStringExtra("avatar");
        if (nickname != null && !nickname.isEmpty() && avatar != null && !avatar.isEmpty()){
            userBean.setNickname(nickname);
            userBean.setAvatar(avatar);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
