package com.live.xue.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.smart.androidutils.utils.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import com.live.xue.MyApplication;
import com.live.xue.R;
import com.live.xue.base.BaseFragment;
import com.live.xue.home.adapter.LatestRecyclerListAdapter;
import com.live.xue.home.adapter.TopicRecyclerListAdapter;
import com.live.xue.home.intf.OnRecyclerViewItemClickListener;
import com.live.xue.home.model.TopicItem;
import com.live.xue.home.model.VideoItem;
import com.live.xue.home.view.GridLayoutManagerTopic;
import com.live.xue.home.view.SpaceItemDecoration;
import com.live.xue.intf.OnCustomClickListener;
import com.live.xue.intf.OnRequestDataListener;
import com.live.xue.living.LivingActivity;
import com.live.xue.login.LoginActivity;
import com.live.xue.utils.Api;
import com.live.xue.utils.DialogEnsureUtiles;

/**
 * Created by fengjh on 16/7/19.
 */
public class HomeLatestFragment extends BaseFragment implements OnRecyclerViewItemClickListener {

    @Bind(R.id.swipeRefreshLayout_latest)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recyclerView_latest)
    RecyclerView mRecyclerView;
    @Bind(R.id.recyclerView_topic)
    RecyclerView mRecyclerViewTopic;
    @Bind(R.id.noDataLayout_latest_home)
    RelativeLayout mNodataView;
    TopicItem topic = new TopicItem();
    private int mPosition;
    private ArrayList<VideoItem> mVideoItems;
    private LatestRecyclerListAdapter mFollowRecyclerListAdapter;

    private ArrayList<TopicItem> mTopicItems;
    private TopicRecyclerListAdapter mTopicRecycleListAdapter;

    public static HomeLatestFragment getInstance(int pos) {
        HomeLatestFragment fragment = new HomeLatestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fragment.setArguments(bundle);
        return fragment;
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }, 2000);
          //  mVideoItems.clear();
            getData(topic.getName(),0, 20, mSwipeRefreshLayout);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt("pos");
        }
        if (mVideoItems == null) {
            mVideoItems = new ArrayList<>();
        }
        mVideoItems.clear();
        if (mTopicItems == null) {
            mTopicItems = new ArrayList<>();
        }
        mTopicItems.clear();
//        for (int i = 0; i < 10; i++) {
//            VideoItem videoItem = new VideoItem();
//            mVideoItems.add(videoItem);
//        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        final GridLayoutManagerTopic manager1 = new GridLayoutManagerTopic(getActivity(), 2);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
//        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerViewTopic.setLayoutManager(manager1);
        mSwipeRefreshLayout.setRefreshing(true);
        mVideoItems.clear();
        mTopicItems.clear();
        getData(topic.getName(),0, 20, mSwipeRefreshLayout);
        getTopic();
        mFollowRecyclerListAdapter = new LatestRecyclerListAdapter(getActivity(), mVideoItems);
        mRecyclerView.setAdapter(mFollowRecyclerListAdapter);
        mTopicRecycleListAdapter = new TopicRecyclerListAdapter(getActivity(),mTopicItems);
        mRecyclerViewTopic.setAdapter(mTopicRecycleListAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("MainViewPagerFragment", "--------onScrollStateChanged");
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastVisibleItemPosition == (totalItemCount - 1) && isSlidingToLast) {
                        // toast("没有更多数据了~~");
                        getData(topic.getName(),totalItemCount, 10, null);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("MainViewPagerFragment", "--------onScrolled=dx=" + dx + "---dy=" + dy);
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }
        });
        mFollowRecyclerListAdapter.setOnRecyclerViewItemClickListener(this);
        mTopicRecycleListAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View view, int position) {
                TopicItem topic = mTopicItems.get(position);
//                Bundle data = new Bundle();
//                data.putString("title",topic.getName());
//                data.putSerializable("jump",topic.getContent());
//                openActivity(WebviewActivity.class,data);
                topic = mTopicItems.get(position);
                getData(topic.getName(),0, 20, mSwipeRefreshLayout);
            }
        });
    }

    private void getTopic() {
        Api.getTerm(this.getContext(), new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                JSONArray list = data.getJSONArray("data");
                for (int j=0;j<list.size();j++){
                    TopicItem topic = new TopicItem();
                    topic.setContent(list.getJSONObject(j).getString("name"));
                    topic.setName(list.getJSONObject(j).getString("term_id"));
                    mTopicItems.add(topic);
                }
                mTopicRecycleListAdapter.notifyDataSetChanged();
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    private void getData(String term ,int limit_begin, int limit_num, final SwipeRefreshLayout mSwipeRefreshLayout) {
        String token = (String) SharePrefsUtils.get(this.getContext(), "user", "token", "");
        String userId = (String) SharePrefsUtils.get(this.getContext(), "user", "userId", "");
        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(userId)) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("type", 2);
            requestParams.put("limit_begin", limit_begin);
            requestParams.put("limit_num", limit_num);
            requestParams.put("term_id", term);
            Api.getChannelList(this.getContext(), requestParams, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data) {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mVideoItems.clear();
                    }
                    mNodataView.setVisibility(View.GONE);
                    JSONArray list = data.getJSONArray("info");
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        VideoItem videoItem = new VideoItem();
                        videoItem.setRoom_id(item.getString("room_id"));
                        videoItem.setId(item.getString("id"));
                        videoItem.setUser_nicename(item.getString("user_nicename"));
                        videoItem.setAvatar(item.getString("avatar"));
                        videoItem.setChannel_creater(item.getString("channel_creater"));
                        videoItem.setChannel_location(item.getString("location"));
                        videoItem.setChannel_title(item.getString("channel_title"));
                        videoItem.setOnline_num(item.getString("online_num"));
                        videoItem.setSmeta(item.getString("smeta"));
                        videoItem.setChannel_status(item.getString("channel_status"));
                        videoItem.setPrice(item.getString("price"));
                        videoItem.setNeed_password(item.getString("need_password"));
                        videoItem.setMinute_charge(item.getString("minute_charge"));
                        if(null != item.getString("channel_source") && StringUtils.isNotEmpty(item.getString("channel_source"))){
                            videoItem.setPlay_url(item.getString("play_url"));
                        }
                        mVideoItems.add(videoItem);
                    }
                    mFollowRecyclerListAdapter.notifyDataSetChanged();
                }

                @Override
                public void requestFailure(int code, String msg) {
                    if(isActive){
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mVideoItems.clear();
                            mFollowRecyclerListAdapter.notifyDataSetChanged();
                        }

                        //toast(msg);
                        //加载空数据图
                        if (mVideoItems.size() == 0) {
                            mNodataView.setVisibility(View.VISIBLE);
                        } else {
                            mNodataView.setVisibility(View.GONE);
                        }
                    }

                }
            });

        } else {
            openActivity(LoginActivity.class);
            this.getActivity().finish();
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_latest_home;
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
       final VideoItem item = mVideoItems.get(position);
        if(StringUtils.isNotEmpty(item.getPrice()) && Integer.parseInt(item.getPrice())>0){
            DialogEnsureUtiles.showConfirm(getActivity(), "该房间需要收费" + item.getPrice(), new OnCustomClickListener() {
                @Override
                public void onClick(String value) {
                    MyApplication app = (MyApplication) getActivity().getApplication();
                    if(Integer.parseInt(app.getBalance()) < Integer.parseInt(item.getPrice())){
                        toast("余额不足，请充值");
                        return;
                    }
                    Bundle data = new Bundle();
                    data.putSerializable("videoItem", item);
                    openActivity(LivingActivity.class, data);
                }
            });

        }else if(StringUtils.isNotEmpty(item.getNeed_password()) && Integer.parseInt(item.getNeed_password())==1){
            DialogEnsureUtiles.showInfo(getActivity(), new OnCustomClickListener() {
                @Override
                public void onClick(final String value) {
                    JSONObject params = new JSONObject();
                    params.put("room_id", item.getRoom_id());
                    params.put("token",(String)SharePrefsUtils.get(getContext(),"user","token",""));
                    params.put("room_password",value);
                    Api.checkPass(getContext(), params, new OnRequestDataListener() {
                        @Override
                        public void requestSuccess(int code, JSONObject data) {
                            Bundle data1 = new Bundle();
                            data1.putSerializable("videoItem", item);
                            data1.putString("password",value);
                            openActivity(LivingActivity.class, data1);
                        }

                        @Override
                        public void requestFailure(int code, String msg) {
                            toast(msg);
                        }
                    });
                }
            },"","该房间需要密码");
        }else if(StringUtils.isNotEmpty(item.getMinute_charge()) && Integer.parseInt(item.getMinute_charge())>0){
            DialogEnsureUtiles.showConfirm(getActivity(), "该房间每分钟收费" + item.getMinute_charge(), new OnCustomClickListener() {
                @Override
                public void onClick(String value) {
                    MyApplication app = (MyApplication) getActivity().getApplication();
                    if(Integer.parseInt(app.getBalance()) < Integer.parseInt(item.getMinute_charge())){
                        toast("余额不足，请充值");
                        return;
                    }
                    Bundle data = new Bundle();
                    data.putSerializable("videoItem", item);
                    openActivity(LivingActivity.class, data);
                }
            });

        }else{
            Bundle data = new Bundle();
            data.putSerializable("videoItem", item);
            openActivity(LivingActivity.class, data);
        }
    }
}
