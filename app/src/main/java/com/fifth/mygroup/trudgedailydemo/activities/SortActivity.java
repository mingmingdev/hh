package com.fifth.mygroup.trudgedailydemo.activities;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.fifth.mygroup.trudgedailydemo.Fragment.NotificationAssistFragment;
import com.fifth.mygroup.trudgedailydemo.Fragment.NotificationCommentFragment;
import com.fifth.mygroup.trudgedailydemo.Fragment.NotificationInformFragment;
import com.fifth.mygroup.trudgedailydemo.R;
import com.fifth.mygroup.trudgedailydemo.adapteres.CommonFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SortActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private Toolbar mToolbar;

    private ViewPager mPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initView();
    }

    private void initView() {
        //ToolBar的设置
        mToolbar = (Toolbar) findViewById(R.id.sort_toolbar);

        mToolbar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mToolbar.setTitle("排行榜");
        mToolbar.setBackgroundResource(R.color.mytoolbarColor);
        mToolbar.setTitleTextColor(Color.WHITE);

        mToolbar.inflateMenu(R.menu.sortmenu);//设置右上角的填充菜单
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                switch (menuItemId) {
                    case R.id.sortToday:
                        Toast.makeText(SortActivity.this,"今天",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sortWeek:
                        Toast.makeText(SortActivity.this,"7天",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sortMonth:
                        Toast.makeText(SortActivity.this,"30天",Toast.LENGTH_SHORT).show();
                        break;


                }
                return true;
            }
        });


        /**
         * 对三个Fragment进行设置
         */
        mPager = (ViewPager) findViewById(R.id.sort_viewpager);
        //// TODO:准备tab的切换内容
        TabLayout tabBar = (TabLayout) findViewById(R.id.sort_tab_bar);
        List<Fragment> subFragments = new ArrayList<>();
        // TODO: 添加 Fragment

        Fragment fragment = new NotificationCommentFragment();
        subFragments.add(fragment);

        fragment = new NotificationAssistFragment();
        subFragments.add(fragment);

        fragment = new NotificationInformFragment();
        subFragments.add(fragment);

        FragmentManager manager = getSupportFragmentManager();
        FragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(manager, subFragments);
        mPager.setAdapter(adapter);

        //设置ViewPager的切换和滚动。TabLayout的横线指示器跟随；
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabBar));

        //TabLayout必须提前设置监听器
        tabBar.setOnTabSelectedListener(this);
        //创建新的Tab
        TabLayout.Tab tab = tabBar.newTab();
        tab.setText("阅读");
        tabBar.addTab(tab);

        tab = tabBar.newTab();
        tab.setText("赞");
        tabBar.addTab(tab);

        tab = tabBar.newTab();
        tab.setText("评论");
        tabBar.addTab(tab);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //TODO:切换ViewPager
        //!因为TabLayout会自动选中第一个Tab，会自动回调，因此必须初始化ViewPager,否则会出现一个奇怪的空指针异常

        mPager.setCurrentItem(tab.getPosition());
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {


    }
}
