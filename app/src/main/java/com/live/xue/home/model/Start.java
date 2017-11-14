package com.live.xue.home.model;

import java.io.Serializable;

/**
 * Created by my on 2017/6/14.
 */

public class Start implements Serializable {

    private String gameid;
    private SettingBean game_info ;
    private String stime;
    private String etime ;


    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public SettingBean getGame_info() {
        return game_info;
    }

    public void setGame_info(SettingBean game_info) {
        this.game_info = game_info;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public static class SettingBean implements Serializable{
        /**
         * z : 1.1
         * zd : 1.2
         * x : 1.3
         * xd : 1.4
         * h : 1.5
         */

        private String z;
        private String zd;
        private String x;
        private String xd;
        private String h;

        public String getZ() {
            return z;
        }

        public void setZ(String z) {
            this.z = z;
        }

        public String getZd() {
            return zd;
        }

        public void setZd(String zd) {
            this.zd = zd;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getXd() {
            return xd;
        }

        public void setXd(String xd) {
            this.xd = xd;
        }

        public String getH() {
            return h;
        }

        public void setH(String h) {
            this.h = h;
        }
    }


}
