package com.live.xue.game;

/**
 * Created by Ofx on 2017/11/4.
 * data:{
 gameid:此次游戏的唯一标识id
 game_info => {   //赔率信息，为防止开始游戏后，后台突然改赔率导致，每场后台会存储特定的赔率信息。
 z => 1.1   庄赔率   （key值取庄(z)的首字母）
 zd => 0.90 庄对赔率  （key值取庄对(zd)的首字母）
 x]=> 0.85  闲赔率
 xd => 1.2  闲对赔率
 h => 0.9  和赔率
 }
 stime：游戏开始时间戳
 etime ： 游戏停止下注时间戳
 }
 */

public class GameStartResult {
    public  int code;
    public  DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public String gameid;
        public Game_info game_info;
        public String stime;
        public String etime;

    }

    static  class Game_info {
        public String zd;
        public String x;
        public String z;
        public String xd;
        public String h;

    }
}
