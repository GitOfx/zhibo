package com.yiren.live.game;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiren.live.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ofx on 2017/10/31.
 */

public class GameRightAdapter extends RecyclerView.Adapter {

    List<String> record = new ArrayList<>();
    int size = 25*6;
    int rowCount = 0;
    int coloumCount = 0;

    public GameRightAdapter(List<List<String>> recordListList){
        //先找出最长的一排
        for (int i = 0; i < recordListList.size(); i++) {
            if (recordListList.get(i).size()> rowCount) {
                rowCount = recordListList.get(i).size();
            }
            coloumCount++;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_right_item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder holder1 = (Holder) holder;
        String content = record.get(position);
        holder1.imageView.setVisibility(View.VISIBLE);
        holder1.textView.setText("");
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
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            textView = (TextView) itemView.findViewById(R.id.tv_num);
        }
    }
}
