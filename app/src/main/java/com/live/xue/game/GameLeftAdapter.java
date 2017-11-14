package com.live.xue.game;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.live.xue.R;
import com.live.xue.utils.Logs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ofx on 2017/10/31.
 */

public class GameLeftAdapter extends RecyclerView.Adapter {
    int size = 25*6;
    int rowCount = 0;
    int coloumCount = 0;
    List<String> record = new ArrayList<>();
    public boolean isDraon = false;
    public GameLeftAdapter(List<List<String>> recordListList){
        //先找出最长的一排
        record.clear();
        for (int i = 0; i < recordListList.size(); i++) {
            if (recordListList.get(i).size()> rowCount) {
                rowCount = recordListList.get(i).size();
            }
            coloumCount++;
        }

        if (rowCount < 11){
            rowCount = 11;
        }
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

    public void setData(List<List<String>> recordListList){
        //先找出最长的一排
        record.clear();
        for (int i = 0; i < recordListList.size(); i++) {
            if (recordListList.get(i).size()> rowCount) {
                rowCount = recordListList.get(i).size();
            }
            coloumCount++;
        }

        if (rowCount < 11){
            rowCount = 11;
        }

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_left_item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder holder1 = (Holder) holder;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder1.imageView.getLayoutParams();
        if (GameActivity.item_height > 0) {
            params.height = GameActivity.item_height;
            params.width = GameActivity.item_height;
            holder1.imageView.setLayoutParams(params);
        }
        Logs.e("left GameActivity.item_height "+GameActivity.item_height);
        holder1.imageView.setBackgroundResource(getItemResId(record.get(position)));
    }

    /**
     * x：闲（拼音首字母）
     z：庄（拼音首字母）
     h：和（拼音首字母）

     xxd：闲闲对
     xzd：闲庄对
     xzdxd：闲庄对闲对

     zzd：庄庄对
     zxd：庄闲对
     zzdxd：庄庄对闲对
     * */
    private int getItemResId(String item){

        if (isDraon){//z龙，x虎，h和
            if ("x".equals(item)){
                return R.drawable.hu_dou;
            }else if ("z".equals(item)){
                return R.drawable.longg;
            }else if ("h".equals(item)){
                return R.drawable.h;
            }else {
                return 0;
            }
//            return ;
        }

        if ("x".equals(item)){
            return R.drawable.x;
        }else if ("z".equals(item)){
            return R.drawable.z;
        }else if ("h".equals(item)){
            return R.drawable.h;
        }else if ("xxd".equals(item)){
            return R.drawable.x_xd;
        }else if ("xzd".equals(item)){
            return R.drawable.x_zd;
        }else if ("xzdxd".equals(item)){
            return R.drawable.x_zd_xd;
        }else if ("zzd".equals(item)){
            return R.drawable.z_zd;
        }else if ("zxd".equals(item)){
            return R.drawable.z_xd;
        }else if ("zzdxd".equals(item)){
            return R.drawable.x_zd_xd;
        }
        return 0;
    }
    @Override
    public int getItemCount() {
        return record.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}
