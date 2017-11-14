package com.live.xue.game;

/**
 * Created by Ofx on 2017/11/4.
 * data:{
 "type":下注类别’，（z:庄，zd庄对，x：闲，xd：闲对，h：和）
 "balance":剩余金额
 "price"：下注金额
 }
 */

public class GameChipResult {
    public  int code;
    public  DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public String type;
        public String balance;
        public String price;

    }
}
