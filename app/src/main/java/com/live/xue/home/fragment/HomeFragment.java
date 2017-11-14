package com.live.xue.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.smart.androidutils.fragment.BaseFragment;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import com.live.xue.R;
import com.live.xue.home.adapter.MainViewPagerAdapter;
import com.live.xue.lean.ConversationListActivity;
import com.live.xue.own.userinfo.ContributionAllActivity;
import com.live.xue.search.SearchActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by fengjh on 16/7/19.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private ArrayList<String> mTopTabTitle;
    private ArrayList<Fragment> mFragments;
    private MainViewPagerAdapter mAdapter;

    @OnClick(R.id.image_home_search)
    public void homeSearch(View view) {
        openActivity(SearchActivity.class);
    }

    @OnClick(R.id.image_home_message)
    public void homeMessage(View view) {
        //openActivity(MessageActivity.class);
        openActivity(ConversationListActivity.class);
    }

    @OnClick(R.id.image_home_rank)
    public void imageHomeRank(View v) {
        // openActivity(ContributionAllActivity.class);
        new Thread() {
            public void run() {
                // 使用getEntity方法获得返回结果
                try {
                    HttpPost request = new HttpPost("http://xuebao.xingyuanzuqiu.com/Api/SiSi/getlive");
                    HttpResponse response = new DefaultHttpClient().execute(request);
                    if (response.getStatusLine().getStatusCode() == 200) {

                        String str = EntityUtils.toString(response.getEntity());
                        System.out.println(str);



                    } else {
                        System.out.print("charushibai1");

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }.start();






    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        }
        mFragments.clear();
        if (mTopTabTitle == null) {
            mTopTabTitle = new ArrayList<>();
        }
        mTopTabTitle.clear();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTopTabTitle.add("热门");
        mTopTabTitle.add("关注");
        mTopTabTitle.add("VIP");

        mTopTabTitle.add("游戏");
//        mTopTabTitle.add("龙虎斗");

        for (int i = 0; i < mTopTabTitle.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        HomeHotFragment fragment1 = HomeHotFragment.getInstance(0);

        HomeFollowFragment fragment2 = HomeFollowFragment.getInstance(1);

        HomeLatestFragment fragment3 = HomeLatestFragment.getInstance(2);
        HomeVipFragment fragment4 = HomeVipFragment.getInstance(3);
        HomeVipFragment fragment5 = HomeVipFragment.getInstance(4);

        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        mFragments.add(fragment4);
//        mFragments.add(fragment5);

        mAdapter = new MainViewPagerAdapter(getChildFragmentManager(), mFragments, mTopTabTitle);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_home;
    }
}
