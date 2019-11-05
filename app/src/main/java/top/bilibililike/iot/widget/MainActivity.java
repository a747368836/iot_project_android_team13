package top.bilibililike.iot.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.bilibililike.iot.R;
import top.bilibililike.iot.base.BaseActivity;
import top.bilibililike.iot.base.BaseFragment;
import top.bilibililike.iot.utils.ViewPagerAdapter;
import top.bilibililike.iot.widget.fragment.MainFragment;
import top.bilibililike.iot.widget.fragment.MineFragment;
import top.bilibililike.iot.widget.fragment.SituationFragment;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fragment_container)
    ViewPager viewPager;
    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNav;

    List<BaseFragment> fragmentList ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initViewpage();
        initRecyclerView();

    }

    private void initViewpage(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new SituationFragment());
        fragmentList.add(new MineFragment());
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int id = position == 0 ? R.id.nav_home : position == 1
                        ? R.id.nav_room : position == 2
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
                case R.id.nav_room:
                    viewPager.setCurrentItem(1,false);
                    break;
                case R.id.nav_mine:
                    viewPager.setCurrentItem(2,false);
                    break;
            }
            return true;
        });
    }

    private void initRecyclerView(){

    }


}
