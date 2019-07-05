package com.sun.smartbutler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sun.smartbutler.fragment.ButlerFragment;
import com.sun.smartbutler.fragment.GirlFragment;
import com.sun.smartbutler.fragment.UserFragment;
import com.sun.smartbutler.fragment.WeChatFragment;
import com.sun.smartbutler.ui.SettingActivity;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面tabLayout+viewPager+悬浮按钮实现
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //日志
    private static final String TAG = "MainActivity";

    //TabLayout
    private TabLayout mTabLayout;
    //viewTPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //fragment
    private List<Fragment> mFragment;
    //悬浮窗
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化数据
        initData();
        //初始化View
        initView();
        //去掉阴影
        getSupportActionBar().setElevation(0);

    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.text_butler_service));
        mTitle.add(getString(R.string.text_wechat));
        mTitle.add(getString(R.string.text_girl));
        mTitle.add(getString(R.string.text_user_info));

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WeChatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_setting);
        mFloatingActionButton.setOnClickListener(this);
        mFloatingActionButton.setVisibility(View.GONE);

        //预加载viewPager
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //viewPager的滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);

                if (position == 0) {
                    mFloatingActionButton.setVisibility(View.GONE);
                } else {
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //和listView一样需要设置viewPager适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定tabLayout和viewPager
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
    }
}
