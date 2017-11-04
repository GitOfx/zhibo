package com.yiren.live.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.yiren.live.R;
import com.yiren.live.base.BaseActivity;
import com.yiren.live.intf.OnRequestDataListener;
import com.yiren.live.utils.Api;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ofx on 2017/10/29.
 */

public class GameActivity extends BaseActivity {
    int chipType = 0;
    int xiaZhu = 0;
    String gameid;
    String token;
    String hostid;
    String roomid;

    int timeDelay = 1000*30;

    @Bind(R.id.rv_game_left)
    RecyclerView rv_game_left;
    @Bind(R.id.rv_game_right)
    RecyclerView rv_game_right;

    @Bind(R.id.ll_c10)
    LinearLayout ll_c10;
    @Bind(R.id.ll_c50)
    LinearLayout ll_c50;
    @Bind(R.id.ll_c100)
    LinearLayout ll_c100;
    @Bind(R.id.ll_c500)
    LinearLayout ll_c500;
    @Bind(R.id.ll_c1000)
    LinearLayout ll_c1000;
    @Bind(R.id.ll_c10000)
    LinearLayout ll_c10000;

    @Bind(R.id.item_xd)
    RelativeLayout item_xd;
    @Bind(R.id.item_zd)
    RelativeLayout item_zd;
    @Bind(R.id.item_h)
    RelativeLayout item_h;
    @Bind(R.id.item_x)
    RelativeLayout itm_x;
    @Bind(R.id.item_z)
    RelativeLayout item_z;

    @Bind(R.id.btn_confirm)
    Button btn_confirm;
    @Bind(R.id.btn_cancel)
    Button btn_cancel;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Message mssg = handler.obtainMessage();
                mssg.what = 1;
                handler.sendMessageDelayed(mssg,timeDelay);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        List<List<String>> record = GameResult.mokeData().getData().getRecord();
        GameLeftAdapter adapter = new GameLeftAdapter(record);
        rv_game_left.setLayoutManager(new GridLayoutManager(this,record.get(0).size()));
        rv_game_left.setAdapter(adapter);
        rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        List<List<String>> chart = GameResult.mokeData().getData().getChart();
        GameRightAdapter Rightadapter = new GameRightAdapter(chart);
        //先找出最长的一排
        int rowCount=0;
        for (int i = 0; i < chart.size(); i++) {
            if (chart.get(i).size()> rowCount) {
                rowCount = chart.get(i).size();
            }
        }
        rv_game_right.setLayoutManager(new GridLayoutManager(this,rowCount));
        rv_game_right.setAdapter(Rightadapter);
        rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_game;
    }

    @OnClick({R.id.ll_c10,R.id.ll_c50,R.id.ll_c100,R.id.ll_c500,R.id.ll_c1000,R.id.ll_c10000})
    public void onClickChip(View view){
        ll_c10.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c50.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c100.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c500.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c1000.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c10000.setBackgroundResource(R.drawable.btn_topic_border_black);
        view.setBackgroundResource(R.drawable.btn_topic_border_red);

        if (view.getId() == R.id.ll_c10){
            chipType = 1;
        }else if(view.getId() == R.id.ll_c50){
            chipType = 2;
        }else if(view.getId() == R.id.ll_c100){
            chipType = 3;
        }else if(view.getId() == R.id.ll_c500){
            chipType = 4;
        }else if(view.getId() == R.id.ll_c1000){
            chipType = 5;
        }else if(view.getId() == R.id.ll_c10000){
            chipType = 6;
        }
    }

    @OnClick({R.id.item_xd,R.id.item_zd,R.id.item_h,R.id.item_x,R.id.item_z})
    public void onClickXiazhu(View view){

        item_xd.setBackgroundResource(R.drawable.borde_black);
        item_zd.setBackgroundResource(R.drawable.borde_black);
        item_h.setBackgroundResource(R.drawable.borde_black);
        itm_x.setBackgroundResource(R.drawable.borde_black);
        item_z.setBackgroundResource(R.drawable.borde_black);
        view.setBackgroundResource(R.drawable.borde_red);

        if (view.getId() == R.id.item_xd){
            xiaZhu = 1;
        }else if(view.getId() == R.id.item_zd){
            xiaZhu = 2;
        }else if(view.getId() == R.id.item_h){
            xiaZhu = 3;
        }else if(view.getId() == R.id.item_x){
            xiaZhu = 4;
        }else if(view.getId() == R.id.item_z){
            xiaZhu = 5;
        }
    }

    @OnClick(R.id.btn_confirm)
    public void clickConfirm(View view){
        //开始下注
        com.alibaba.fastjson.JSONObject param = new com.alibaba.fastjson.JSONObject();
        param.put("chip","");
        param.put("gameid","");
        param.put("token","");

        Api.chip(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, com.alibaba.fastjson.JSONObject data) {
                String type = (String) data.get("type");
                String balance = (String) data.get("type");
                String price = (String) data.get("type");

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    @OnClick(R.id.btn_cancel)
    public void clickCancel(View view){

    }

    private void gameStart(){
        JSONObject param = new JSONObject();
        param.put("type","1");
        param.put("token","1");
        Api.start(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                gameid = (String) data.get("gameid");
                GameStartResult.Game_info game_info = data.getObject("game_info",GameStartResult.Game_info.class);
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }
}
