package com.yiren.live.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by my on 2017/6/14.
 */

public class GameHeart implements Serializable {


    /**
     * stime : 14974260971497426997
     * etime :
     * setting : {"z":"1.1","zd":"1.2","x":"1.3","xd":"1.4","h":"1.5"}
     * result : 0
     * gameid : 17
     * prize_gameid : 17
     * hostid : 60491
     * users : []
     */

    private String stime;
    private String etime;
    private SettingBean setting;
    private String result;
    private String gameid;
    private String prize_gameid;
    private String hostid;
    private List<User> users;
        private String ctime;

    @Override
    public String toString() {
        return "GameHeart{" +
                "stime='" + stime + '\'' +
                ", etime='" + etime + '\'' +
                ", setting=" + setting +
                ", result='" + result + '\'' +
                ", gameid='" + gameid + '\'' +
                ", prize_gameid='" + prize_gameid + '\'' +
                ", hostid='" + hostid + '\'' +
                ", users=" + users +
                ", ctime='" + ctime + '\'' +
                '}';
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public SettingBean getSetting() {
        return setting;
    }

    public void setSetting(SettingBean setting) {
        this.setting = setting;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public String getPrize_gameid() {
        return prize_gameid;
    }

    public void setPrize_gameid(String prize_gameid) {
        this.prize_gameid = prize_gameid;
    }

    public String getHostid() {
        return hostid;
    }

    public void setHostid(String hostid) {
        this.hostid = hostid;
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

    public static class User implements Serializable{
        private String Hostid;
        private String uid ;
        private String user_nicename;
        private String type ;
        private String price ;
        private String create_time;

        public String getHostid() {
            return Hostid;
        }

        public void setHostid(String hostid) {
            Hostid = hostid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_nicename() {
            return user_nicename;
        }

        public void setUser_nicename(String user_nicename) {
            this.user_nicename = user_nicename;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }


}
