package com.live.xue.game;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.live.xue.MyApplication;
import com.live.xue.R;
import com.live.xue.utils.Logs;
import com.smart.androidutils.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ofx on 2017/10/31.
 * 18202639577
 */

public class GameRightAdapter extends RecyclerView.Adapter {

    List<String> record = new ArrayList<>();
    int size = 25*6;
    int rowCount = 0;
    int coloumCount = 0;
    Context context;

    public GameRightAdapter(List<List<String>> recordListList){
        record.clear();
        //先找出最长的一排
        for (int i = 0; i < recordListList.size(); i++) {
            if (recordListList.get(i).size()> rowCount) {
                rowCount = recordListList.get(i).size();
            }
            coloumCount++;
        }

        int width = DensityUtils.screenWidth(MyApplication.getGlobalContext());
        int rowCount = ((width-GameActivity.item_height*12) / GameActivity.item_height)+1;
//        int width = DensityUtils.getWindowWidth(MyApplication.getGlobalContext());
//        rowCount = (width-DensityUtils.dip2px(MyApplication.getGlobalContext(),15)*12) / DensityUtils.dip2px(MyApplication.getGlobalContext(),15);
        if (coloumCount < 6){
            coloumCount = 6;
        }

        for (int i = 0; i < coloumCount; i++) {
            if (recordListList.size() > i) {
                for (int j = 0; j < rowCount; j++) {
                    if (recordListList.get(i).size() > j) {
                        record.add(recordListList.get(i).get(j));
                    } else {
                        record.add("0");
                    }
                }
            } else {
                for (int j = 0; j < rowCount; j++) {
                    record.add("0");
                }
            }
        }
        int size = record.size();
        Logs.e("row "+rowCount+" cloclum "+coloumCount);
    }

    public void setData(List<List<String>> recordListList){
        //先找出最长的一排
        record.clear();
        for (int i = 0; i < recordListList.size(); i++) {
            if (recordListList.get(i).size()> rowCount) {
                rowCount = recordListList.get(i).size();
            }
            coloumCount++;
        }

        int width = DensityUtils.getWindowWidth(MyApplication.getGlobalContext());
        rowCount = (width-DensityUtils.dip2px(MyApplication.getGlobalContext(),15)*12)/DensityUtils.dip2px(MyApplication.getGlobalContext(),15);

        if (coloumCount < 6){
            coloumCount = 6;
        }

        for (int i = 0; i < coloumCount; i++) {
            if (recordListList.size() > i) {
                for (int j = 0; j < rowCount; j++) {
                    if (recordListList.get(i).size() > j) {
                        record.add(recordListList.get(i).get(j));
                    } else {
                        record.add("0");
                    }
                }
            } else {
                for (int j = 0; j < rowCount; j++) {
                    record.add("0");
                }
            }
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_right_item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Holder holder1 = (Holder) holder;
        String content = record.get(position);
       if (GameActivity.item_height > 0){
           int dp =DensityUtils.dip2px(context,1);
           RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder1.imageView.getLayoutParams();
           params.height = GameActivity.item_height;
           params.width = GameActivity.item_height;
           holder1.imageView.setLayoutParams(params);

           RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) holder1.textView.getLayoutParams();
           params1.height = GameActivity.item_height;
           params1.width = GameActivity.item_height;
           params1.addRule(RelativeLayout.CENTER_IN_PARENT);
           holder1.textView.setLayoutParams(params);
           holder1.textView.setGravity(Gravity.CENTER);
//           RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) holder1.rl_img.getLayoutParams();
//           params1.height = GameActivity.item_height+2*dp;
//           params1.width = GameActivity.item_height+2*dp;
//           holder1.rl_img.setPadding(dp,dp,dp,dp);
//           holder1.rl_img.setLayoutParams(params);

       }

        Logs.e("GameActivity.item_height "+GameActivity.item_height);
        holder1.imageView.setVisibility(View.VISIBLE);
        holder1.textView.setText("");
//        content = "x4";
        if (content.startsWith("x")){
            holder1.imageView.setBackgroundResource(R.drawable.r_000);
            String num = content.substring(1);
            if (!TextUtils.isEmpty(num)) {
                holder1.textView.setText(num);
            }
        }else if (content.startsWith("z")){
            holder1.imageView.setBackgroundResource(R.drawable.r_010);
            String num = content.substring(1);
            if (!TextUtils.isEmpty(num)) {
                holder1.textView.setText(num);
            }
        }else {
            holder1.imageView.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return record.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        RelativeLayout rl_img;
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            textView = (TextView) itemView.findViewById(R.id.tv_num);
            rl_img = (RelativeLayout) itemView.findViewById(R.id.rl_img);
        }
    }
}
