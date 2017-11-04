package com.yiren.live.game;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yiren.live.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ofx on 2017/10/31.
 */

public class GameLeftAdapter extends RecyclerView.Adapter {

    List<String> record;
    public GameLeftAdapter(List<List<String>> recordListList){
        if (recordListList != null) {
            this.record = new ArrayList<>();
            for (List<String> stringList : recordListList) {
                if (stringList != null) {
                    for (String s : stringList) {
                        record.add(s);
                    }
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
