package com.live.xue.game;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.live.xue.R;
import com.live.xue.base.BaseActivity;
import com.live.xue.intf.OnCustomClickListener;
import com.live.xue.intf.OnRequestDataListener;
import com.live.xue.utils.Api;
import com.live.xue.utils.DialogEnsureUtiles;
import com.live.xue.utils.Logs;
import com.smart.androidutils.utils.DensityUtils;
import com.smart.androidutils.utils.SharePrefsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ofx on 2017/11/9.
 */

public class GameLongHuActivity extends BaseActivity {
    int chipType = 0;
    int xiaZhu = 0;
    List<String> chipList= new ArrayList<>();
    List<String> xiazhuList= new ArrayList<>();
    String xiaZhuStr;
    String gameid="0";
    String token;
    String hostid;
    String roomid;

    TextView currentZhu;
    int timeDelay = 1000*30;
    Map<String,List<Integer>> zhuMap = new HashMap<String, List<Integer>>() ;
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
    RelativeLayout item_x;
    @Bind(R.id.item_z)
    RelativeLayout item_z;

    @Bind(R.id.btn_confirm)
    Button btn_confirm;
    @Bind(R.id.btn_cancel)
    Button btn_cancel;

    @Bind(R.id.tv_zhu_x)
    TextView tv_zhu_x;
    @Bind(R.id.tv_zhu_h)
    TextView tv_zhu_h;
    @Bind(R.id.tv_zhu_z)
    TextView tv_zhu_z;
    @Bind(R.id.tv_zhu_xd)
    TextView tv_zhu_xd;
    @Bind(R.id.tv_zhu_zd)
    TextView tv_zhu_zd;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Message mssg = handler.obtainMessage();
                mssg.what = 1;
                handler.sendMessageDelayed(mssg,timeDelay);
                gameHeart();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
//        List<List<String>> record = GameResult.mokeData().getData().getRecord();
        GameLeftAdapter adapter = new GameLeftAdapter(new ArrayList<List<String>>());
        if (Logs.isDebug) {
            rv_game_left.setLayoutManager(new GridLayoutManager(this,11));
            rv_game_left.setAdapter(adapter);
        }

//        rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//        rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        List<List<String>> chart = new ArrayList<>();
//        List<List<String>> chart = GameResult.mokeData().getData().getChart();
        GameRightAdapter Rightadapter = new GameRightAdapter(chart);

        int width = DensityUtils.getWindowWidth(this);
        int rowCount = (width-DensityUtils.dip2px(this,15)*12) / DensityUtils.dip2px(this,15);
        if (Logs.isDebug) {
            rv_game_right.setLayoutManager(new GridLayoutManager(this,rowCount));
            rv_game_right.setAdapter(Rightadapter);
        }

//        rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//        rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        token = (String) SharePrefsUtils.get(this, "user", "token", "");
        hostid = (String) getIntent().getExtras().get("hostid");
        roomid = (String) getIntent().getExtras().get("roomid");
//        hostid：2，roomid：1，token：123
//        if (Logs.isDebug) {
//            token = "123";
//            roomid = "1";
//            hostid = "2";
//        }

        Message msg = handler.obtainMessage(1);
        handler.sendMessage(msg);
        initXiazhuItems();
        getChipType();
        getBance();

        String name = (String) SharePrefsUtils.get(this, "user", "user_nicename","user");
        ((TextView) findViewById(R.id.game_user_name)).setText(name);
    }


    private void initXiazhuItems(){
        setXiazhuInfo(item_xd,"PAIR","闲对","1:10",R.color.num3);
        setXiazhuInfo(item_h,"TIE","和","1:10",R.color.color_00ba00);
        setXiazhuInfo(item_zd,"PAIR","庄对","1:10",R.color.color_ff0000);
        setXiazhuInfo(item_x,"PLAYER","闲","1:10",R.color.num3);
        setXiazhuInfo(item_z,"BANNER","庄","1:10",R.color.color_ff0000);
    }

    private void setXiazhuInfo(RelativeLayout item,String title ,String name,String score,int coclor){
        TextView titleVIew  = (TextView) item.findViewById(R.id.tv_title);
        TextView nameView  = (TextView) item.findViewById(R.id.tv_name);
        TextView scoreView  = (TextView) item.findViewById(R.id.tv_score);
        titleVIew.setText(title);
        titleVIew.setTextColor(getResources().getColor(coclor));
        nameView.setText(name);
        nameView.setTextColor(getResources().getColor(coclor));
        scoreView.setText(score);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_game;
    }

    @OnClick({R.id.ll_c10,R.id.ll_c50,R.id.ll_c100,R.id.ll_c500,R.id.ll_c1000,R.id.ll_c10000})
    public void onClickChip(View view){
        //每点一次当距投注信息就往上加
        ll_c10.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c50.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c100.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c500.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c1000.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c10000.setBackgroundResource(R.drawable.btn_topic_border_black);
        view.setBackgroundResource(R.drawable.btn_topic_border_red);



        if (view.getId() == R.id.ll_c10){
            selectChip(currentZhu,10,view);
            chipType = 1;
        }else if(view.getId() == R.id.ll_c50){
            selectChip(currentZhu,50,view);
            chipType = 2;
        }else if(view.getId() == R.id.ll_c100){
            selectChip(currentZhu,100,view);
            chipType = 3;
        }else if(view.getId() == R.id.ll_c500){
            selectChip(currentZhu,500,view);
            chipType = 4;
        }else if(view.getId() == R.id.ll_c1000){
            selectChip(currentZhu,1000,view);
            chipType = 5;
        }else if(view.getId() == R.id.ll_c10000){
            selectChip(currentZhu,10000,view);
            chipType = 6;
        }
    }

    private void selectChip(TextView view,int  num,View chipView){
        if (view != null) {
            String type = (String) chipView.getTag();
            int typeC = 0;
            try {
                typeC = Integer.parseInt(type);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            addToMap(xiaZhuStr,typeC);
            int current = Integer.parseInt(view.getText().toString());
            view.setText(current+num+"");
        }
    }

    private void addToMap(String key,int chipType){
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(xiaZhuStr)) {
            return;
        }
        if (key.equals(xiaZhuStr)){
            List<Integer> list = null;
            if (zhuMap.containsKey(key)) {
                list = zhuMap.get(key);
            }else {
                list = new ArrayList<>();
                zhuMap.put(key,list);
            }
            list.add(chipType);
        }
    }


    @OnClick({R.id.item_xd,R.id.item_zd,R.id.item_h,R.id.item_x,R.id.item_z})
    public void onClickXiazhu(View view){
        item_xd.setBackgroundResource(R.drawable.borde_black);
        item_zd.setBackgroundResource(R.drawable.borde_black);
        item_h.setBackgroundResource(R.drawable.borde_black);
        item_x.setBackgroundResource(R.drawable.borde_black);
        item_z.setBackgroundResource(R.drawable.borde_black);
        view.setBackgroundResource(R.drawable.borde_red);


        if (view.getId() == R.id.item_xd){
            selectXiazhu(view,"xd");
            xiaZhu = 1;
            xiaZhuStr = "xd";
            currentZhu = tv_zhu_xd;
        }else if(view.getId() == R.id.item_zd){
            xiaZhu = 2;
            xiaZhuStr = "zd";
            currentZhu = tv_zhu_zd;
            selectXiazhu(view,"zd");
        }else if(view.getId() == R.id.item_h){
            xiaZhu = 3;
            xiaZhuStr = "h";
            selectXiazhu(view,"h");
            currentZhu = tv_zhu_h;
        }else if(view.getId() == R.id.item_x){
            xiaZhu = 4;
            xiaZhuStr = "x";
            selectXiazhu(view,"x");
            currentZhu = tv_zhu_x;
        }else if(view.getId() == R.id.item_z){
            xiaZhu = 5;
            xiaZhuStr = "z";
            currentZhu = tv_zhu_z;
            selectXiazhu(view,"z");
        }
    }

    private void selectXiazhu(View view,String type){

    }

    @OnClick(R.id.btn_confirm)
    public void clickConfirm(View view){
        if (zhuMap.isEmpty()) {
            DialogEnsureUtiles.showConfirm(this, "请选择筹码", new OnCustomClickListener() {
                @Override
                public void onClick(String value) {

                }
            });

            return;
        }
        if (TextUtils.isEmpty(xiaZhuStr)) {
            DialogEnsureUtiles.showConfirm(this, "请先下庄", new OnCustomClickListener() {
                @Override
                public void onClick(String value) {

                }
            });
            return;
        }
        //开始下注
        com.alibaba.fastjson.JSONObject param = new com.alibaba.fastjson.JSONObject();
//        JSONArray array = new JSONArray();
//        array.add(chipType);
//        JSONObject zhuType = new JSONObject();
//        zhuType.put(xiaZhuStr,array);
        param.put("chip",zhuMap);
        param.put("gameid",gameid);
        param.put("token",token);

        Api.chip(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, com.alibaba.fastjson.JSONObject data) {
                data = data.getJSONObject("data");
                String type = (String) data.get("type");
//                String balance = (String) data.get("type");
//                String price = (String) data.get("type");
//                setResult(type);
                toast("下注成功");
                clickCancel(null);
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast("下注失败");
//                clickCancel(null);
            }
        });
    }

    @OnClick(R.id.btn_cancel)
    public void clickCancel(View view){
        item_xd.setBackgroundResource(R.drawable.borde_black);
        item_zd.setBackgroundResource(R.drawable.borde_black);
        item_h.setBackgroundResource(R.drawable.borde_black);
        item_x.setBackgroundResource(R.drawable.borde_black);
        item_z.setBackgroundResource(R.drawable.borde_black);
        chipList.clear();
        ll_c10.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c50.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c100.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c500.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c1000.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c10000.setBackgroundResource(R.drawable.btn_topic_border_black);
        xiazhuList.clear();
        currentZhu = null;
        zhuMap.clear();
        tv_zhu_h.setText("0");
        tv_zhu_x.setText("0");
        tv_zhu_xd.setText("0");
        tv_zhu_z.setText("0");
        tv_zhu_zd.setText("0");
    }

    private void gameStart(){
        JSONObject param = new JSONObject();
        param.put("type","1");
        param.put("token","1");
        Api.start(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                data = data.getJSONObject("data");
                gameid = (String) data.get("gameid");
                GameStartResult.Game_info game_info = data.getObject("game_info",GameStartResult.Game_info.class);

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    /**0:未开奖（gameid对应的开奖结果），参数：（z:庄赢，zd庄对赢，x：闲赢，xd：闲对赢，h：和赢）*/
    private void setResult(String result){
        if ("z".equals(result)){
            addResult(tv_zhu_z);
        }else if ("zd".equals(result)){
            addResult(tv_zhu_zd);
        }else if ("x".equals(result)){
            addResult(tv_zhu_x);
        }else if ("xd".equals(result)){
            addResult(tv_zhu_xd);
        }else if ("h".equals(result)){
            addResult(tv_zhu_h);
        }
    }

    private void addResult(TextView textView){
        try {
            int num = Integer.parseInt(textView.getText().toString());
            num++;
            textView.setText(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
    /**{
     “hostid”:主播id
     roomid：房间id
     gameid：游戏id，传初始化0，第一次心跳后，如果存在已开始游戏，返回游戏id，获取gameid后，客户端必须传递gameid，以作为服务端开奖结果查询，开奖后，服务端自动重置为0，主播再次点击开始下注后，心跳可获取对应gameid。总之就是服务端传gameid是什么值，客户端收到后就传什么到服务端
     }*/
    public void gameHeart(){
        JSONObject param = new JSONObject();
        param.put("hostid",hostid);
        param.put("roomid",roomid);
        param.put("gameid",gameid);
        Api.gameHeart(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                data = data.getJSONObject("data");
                gameid = (String) data.get("gameid");
                String result = (String) data.get("result");
                gameid = (String) data.get("gameid");
                JSONObject setting = (JSONObject) data.get("setting");
                setPeiRate(setting);
                JSONArray array = data.getJSONArray("chart");
                JSONArray record = data.getJSONArray("record");
                setRightRecyleData(array);
                setLeftRecycle(record);
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    GameLeftAdapter gameLeftAdapter;
    GridLayoutManager leftLayoutManeger;
    private void setLeftRecycle(JSONArray array){
        List<List<String>> record = new ArrayList<>();
        for (Object o : array) {
            JSONArray jsonArray = (JSONArray) o;
            List<String> list = new ArrayList<>();
            for (Object o1 : jsonArray) {
                list.add((String) o1);
            }

            record.add(list);
        }

        if (gameLeftAdapter == null) {
            gameLeftAdapter = new GameLeftAdapter(record);
            rv_game_left.setAdapter(gameLeftAdapter);
//            rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//            rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        }else {
            gameLeftAdapter.setData(record);
        }
        gameLeftAdapter.notifyDataSetChanged();
        //先找出最长的一排
        int rowCount=0;
        for (int i = 0; i < record.size(); i++) {
            if (record.get(i).size()> rowCount) {
                rowCount = record.get(i).size();
            }
        }
        if (leftLayoutManeger == null) {
            leftLayoutManeger = new GridLayoutManager(this,rowCount);
            rv_game_left.setLayoutManager(leftLayoutManeger);
        }else {
            leftLayoutManeger.setSpanCount(rowCount);
        }
    }

    GameRightAdapter Rightadapter;
    GridLayoutManager rightGridManeger;
    private void setRightRecyleData(JSONArray array){
        List<List<String>> chart = new ArrayList<>();
        for (Object o : array) {
            JSONArray jsonArray = (JSONArray) o;
            List<String> list = new ArrayList<>();
            for (Object o1 : jsonArray) {
                list.add((String) o1);
            }

            chart.add(list);
        }

        if (Rightadapter == null) {
            Rightadapter = new GameRightAdapter(chart);
            rv_game_right.setAdapter(Rightadapter);
//            rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//            rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        }else {
            Rightadapter.setData(chart);
        }
        Rightadapter.notifyDataSetChanged();
        //先找出最长的一排
        int rowCount=0;
        for (int i = 0; i < chart.size(); i++) {
            if (chart.get(i).size()> rowCount) {
                rowCount = chart.get(i).size();
            }
        }

        int width = DensityUtils.getWindowWidth(this);
        rowCount = (width-DensityUtils.dip2px(this,15)*11)/DensityUtils.dip2px(this,15);

        if (rightGridManeger == null) {
            rightGridManeger = new GridLayoutManager(this,rowCount);
            rv_game_right.setLayoutManager(rightGridManeger);
        }else {
            rightGridManeger.setSpanCount(rowCount);
        }

    }

    //赔率
    /*{z => 1.1   庄赔率
                    zd => 0.90 庄对赔率
                    x=> 0.85  闲赔率
                    xd => 1.2  闲对赔率
                    h => 0.9  和赔率
}*/
    private void setPeiRate(JSONObject data){
        String  z  = data.getString("z");
        ((TextView) item_z.findViewById(R.id.tv_score)).setText("1:"+z);
        String  x  = data.getString("x");
        ((TextView) item_z.findViewById(R.id.tv_score)).setText("1:"+x);
        String  h  = data.getString("h");
        ((TextView) item_z.findViewById(R.id.tv_score)).setText("1:"+h);
        String  zd  = data.getString("zd");
        ((TextView) item_z.findViewById(R.id.tv_score)).setText("1:"+zd);
        String  xd = data.getString("xd");
        ((TextView) item_z.findViewById(R.id.tv_score)).setText("1:"+xd);


    }
    /*chipType{
[  数组
 		 id=> 1 类型id
         giftname]=> 筹码500   筹码名称
         needcoin => 500		筹码价格
         gifticon_25]=> /deerlive/data/upload/20170604/59338df28cf72.jpg  筹码缩略图地址
         gifticon => /deerlive/data/upload/20170604/59339b4ad15ed.jpg   筹码图片地址
]
}*/
    private void getChipType(){
        Api.getChipType(this, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                data = data.getJSONObject("data");
                JSONArray chipType = data.getJSONArray("chipType");
                for (Object o : chipType) {
                    JSONObject object = (JSONObject) o;
                    String coin = object.getString("needcoin");
                    String type = object.getString("id");
                    if ("10".equals(coin)){
                        ll_c10.setTag(type);
                    }else if ("50".equals(coin)){
                        ll_c50.setTag(type);
                    }else if ("100".equals(coin)){
                        ll_c100.setTag(type);
                    }else if ("500".equals(coin)){
                        ll_c500.setTag(type);
                    }else if ("1000".equals(coin)){
                        ll_c1000.setTag(type);
                    }else if ("10000".equals(coin)){
                        ll_c10000.setTag(type);
                    }
                }
                Logs.e("getChipType");
            }

            @Override
            public void requestFailure(int code, String msg) {
                Logs.e(msg+"getChipType");
            }
        });
    }

    private void getBance(){
        JSONObject param = new JSONObject();
        param.put("token",token);
        Api.getBalance(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                data = data.getJSONObject("data");
                String Balance = (String) data.get("balance");
                ((TextView) findViewById(R.id.game_money)).setText(Balance);
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }
}
