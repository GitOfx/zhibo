package com.yiren.live.home.model;

import java.io.Serializable;

/**
 * Created by my on 2017/6/14.
 */

public class GetChipType implements Serializable {


    /**
     * id : 1
     * giftname : 500筹码
     * needcoin : 500
     * gifticon_25 : /deerlive/data/upload/20170607/593798bb2858d.png
     * gifticon : /deerlive/data/upload/20170607/593798bfaa214.png
     */

    private String id;
    private String giftname;
    private String needcoin;
    private String gifticon_25;
    private String gifticon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGiftname() {
        return giftname;
    }

    public void setGiftname(String giftname) {
        this.giftname = giftname;
    }

    public String getNeedcoin() {
        return needcoin;
    }

    public void setNeedcoin(String needcoin) {
        this.needcoin = needcoin;
    }

    public String getGifticon_25() {
        return gifticon_25;
    }

    public void setGifticon_25(String gifticon_25) {
        this.gifticon_25 = gifticon_25;
    }

    public String getGifticon() {
        return gifticon;
    }

    public void setGifticon(String gifticon) {
        this.gifticon = gifticon;
    }
}
