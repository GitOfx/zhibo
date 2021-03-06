package com.live.xue.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.live.xue.game.GameChipResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smart.androidutils.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;
import com.live.xue.intf.OnRequestDataListener;
import com.live.xue.view.SFProgrssDialog;

/**
 * Created by Administrator on 2016/8/19.
 * Author: XuDeLong
 */
public class Api {
    private static final String OS = "android";
    private static final String SOFT_VER = Build.VERSION.RELEASE;
    private static final String VERSION_NUM = "1.0.2";
    private static final String KEY = "jlUsqXufGweW1nkAHU3b4jWEaWoqhBfw";
//    public static final String HOST = "http://125.88.146.26/";
//    public static final String HOST = "http://jz.xingyuanzuqiu.com/";
    public static final String HOST = "http://xuebao.xingyuanzuqiu.com/";
    private static final String DO_LOGIN = HOST + "Api/SiSi/login";
    private static final String DO_REGISTER = HOST + "Api/SiSi/register";
    private static final String GET_VARCODE = HOST + "Api/SiSi/get_phone_varcode";
    private static final String GET_USERINFO = HOST + "Api/SiSi/getUserInfo";
    private static final String GET_USERDATA = HOST + "Api/SiSi/get_userinfo";
    private static final String FORGET_PASSWORD = HOST + "Api/SiSi/retrievePassword";
    public static final String SET_USERDATA = HOST + "Api/SiSi/change_userinfo";
    private static final String CHANGE_PASSWORD = HOST + "Api/SiSi/changePassword";
    private static final String FEED_BACK = HOST + "Api/SiSi/submitFeedback";
    private static final String GET_USER_ATTENTION_LIST = HOST + "Api/SiSi/getUserAttentionList";
    private static final String GET_USER_FANS_LIST = HOST + "Api/SiSi/getUserFansList";
    private static final String GET_USER_CONTRIBUTION_LIST = HOST + "Api/SiSi/getUserContributionList";
    private static final String GET_CHANNEL_LIST = HOST + "Api/SiSi/getLive";
    private static final String GET_ATTENTION_CHANNEL_LIST = HOST + "Api/SiSi/getAttentionLiveList";
    private static final String GET_LAUNCH_SCREEN = HOST + "Api/SiSi/getLaunchScreen";
    private static final String GET_BANNER = HOST + "Api/SiSi/getBanner";
    public static final String WEB_AUTH = HOST + "portal/Appweb/index";
    public static final String SEARCH = HOST + "Api/SiSi/searchUsers";
    public static final String START_LIVE = HOST + "Api/SiSi/startLive";
    public static final String WEB_EXCHANGE = HOST + "portal/Appweb/earn";
    public static final String WEB_GRADE = HOST + "portal/Appweb/grade";
    private static final String ADD_ATTENTION = HOST + "Api/SiSi/addAttention";
    private static final String SET_LOCATION = HOST + "Api/SiSi/setLocation";
    private static final String GET_CHANNLE_INFO = HOST + "Api/SiSi/enterLiveRoom";
    private static final String GET_GIFTS = HOST + "Api/SiSi/getGiftList";
    private static final String SEND_GIFT = HOST + "Api/SiSi/sendGiftToAnchor";
    private static final String SEND_DANMU = HOST + "Api/SiSi/sendDanmuToAnchor";
    private static final String SEND_REPORT = HOST + "Api/SiSi/sendReport";
    private static final String ADD_JINYAN = HOST + "Api/SiSi/setDisableSendMsg";
    private static final String LIVING_REALTIME_DATA = HOST + "Api/SiSi/getLiveRoomOnlineNumEarn";
    private static final String GET_LIVE_ROOM_ONLINE_LIST = HOST + "Api/SiSi/getLiveRoomOnlineUserList";
    private static final String EXIT_LIVE_ROOM = HOST + "Api/SiSi/exitLiveRoom";
    private static final String CANCEL_ATTENTION = HOST + "Api/SiSi/cancelAttention";
    private static final String STOP_PUBLISH = HOST + "Api/SiSi/stopLive";
    private static final String GET_TERM = HOST + "/Api/SiSi/getChannelTermList";
    private static final String GET_SHARE_INFO = HOST + "Api/SiSi/getShareInfo";
    private static final String THIRD_LOGIN = HOST + "Api/SiSi/sendOauthUserInfo";
    private static final String PAY = HOST  + "Api/SiSi/begin_wxpay";
    private static final String PAY_ALI = HOST + "Api/SiSi/begin_alipay";
    private static final String GET_CHARGE_PACK = HOST + "Api/SiSi/get_recharge_package";
    private static final String CHECK_UPDATE = HOST + "Api/SiSi/checkAndroidVer";
    private static final String SET_MANAGERER = HOST + "Api/SiSi/setLiveGuard";
    private static final String CANCEL_MANAGER = HOST + "Api/SiSi/CancelLiveGuard";
    private static final String GET_MANAGER_LIST = HOST + "Api/SiSi/getLiveGuardList";
    private static final String GET_TOPIC = HOST + "Api/SiSi/topic_list";
    private static final String SET_SHOP_LINK = HOST + "Api/SiSi/change_shopurl";
    private static final String GET_SHOP_LINK = HOST + "Api/SiSi/get_shopurl";
    private static final String PUBLISH_RECORD = HOST + "/Api/SiSi/getMyLiveRecordList";
    private static final String CHANGE_MOBILE = HOST + "/Api/SiSi/BindingMobile";
    private static final String GET_SYSTEM_RANK_LIST = HOST + "/Api/SiSi/getSystemRankingList";
    private static final String ADD_LIKE = HOST + "/Api/SiSi/add_like";
    public static final String LOGIN_BG = HOST + "/data/upload/demo_video/105_clip.mp4";
    public static final String SEARCH_MUSIC = "http://apis.baidu.com/geekery/music/query";
    public static final String GET_MUSIC = "http://apis.baidu.com/geekery/music/playinfo";
    public static final String CHECK_PASS = HOST + "/Api/SiSi/checkRoomPassword";
    public static final String SEARCH_MUSIC1 = HOST + "/Api/SiSi/searchSong";
    public static String GET_LikePic = HOST + "/Api/SiSi/getFlotageImage";
    public static String GET_SONG_LRC = HOST + "/Api/SiSi/searchSongLyric";
    public static final String DUANLIU = HOST + "/Api/SiSi/advancedManage";
    public static final String GET_SHOP = HOST + "/Api/SiSi/getActivityinfo";
    public static final String WEB_Family = HOST + "portal/Family/index";
    public static final String PUSH_CALLBACK = HOST + "/Api/SiSi/startLivePushCallback";
    public static final String WAP_PAY = HOST + "Api/SiSi/begin_wappay";
    public static final String XIEYI = HOST + "portal/page/index/id/2";
    public static final String SWITCH_MODE = HOST + "Api/SiSi/livingChangeRoomType";

//    dragonGame
    public static final String GAME_DOU_START_URL = HOST + "Api/dragonGame/start";
    public static final String GAME_DOU_CHIP_URL = HOST + "Api/dragonGame/chip";
    public static final String GAME_DOU_GAMEHEART_URL = HOST + "Api/dragonGame/gameHeart";
    public static final String GAME_DOU_PRIZE_URL = HOST + "Api/dragonGame/prize";
    public static final String GAME_DOU_RECORD_URL = HOST + "Api/dragonGame/record";

    public static final String GAME_DOU_GETCHIPTYPE_URL = HOST + "Api/dragonGame/getChipType";
    public static final String GAME_DOU_CHECKRECOVERY_URL = HOST + "Api/dragonGame/checkRecovery";
    public static final String GAME_DOU_FORBIDCHIP_URL = HOST + "Api/dragonGame/forbidChip";
    public static final String GAME_DOU_GETBALANCE_URL = HOST + "Api/dragonGame/getBalance";


    public static final String START_URL = HOST + "Api/game/start";
    public static final String CHIP_URL = HOST + "Api/game/chip";
    public static final String GAMEHEART_URL = HOST + "Api/game/gameHeart";
    public static final String PRIZE_URL = HOST + "Api/game/prize";
    public static final String RECORD_URL = HOST + "Api/game/record";

    public static final String GETCHIPTYPE_URL = HOST + "Api/game/getChipType";
    public static final String CHECKRECOVERY_URL = HOST + "Api/game/checkRecovery";
    public static final String FORBIDCHIP_URL = HOST + "Api/game/forbidChip";
    public static final String GETBALANCE_URL = HOST + "Api/game/getBalance";

    public static final String GETMESSAGE_URL = HOST + "Api/SiSi/getMessage";
    public static void getMessage(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GETMESSAGE_URL, context, params,null, listener);
    }

    public static void doRegister(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(DO_REGISTER, context, params,dialog, listener);
    }
    public static void pushCallback(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(PUSH_CALLBACK, context, params,null, listener);
    }
    public static void wapPay(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(WAP_PAY, context, params,dialog, listener);
    }
    public static void duanLiu(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(DUANLIU, context, params,dialog, listener);
    }
    public static void getShop(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_SHOP, context, params,null, listener);
    }

    public static void getSongLrc(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_SONG_LRC, context, params,null, listener);
    }

    public static void searchMusic1(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SEARCH_MUSIC1, context, params,dialog, listener);
    }

    public static void getLikePic(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"waiting...");
        excutePost(GET_LikePic, context, params,null, listener);
    }

    public static void checkPass(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CHECK_PASS, context, params,dialog, listener);
    }


    public static void addLike(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(DO_REGISTER, context, params,null, listener);
    }

    public static void getSystemRankList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_SYSTEM_RANK_LIST, context, params,dialog, listener);
    }

    public static void changeMobile(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CHANGE_MOBILE, context, params,dialog, listener);
    }

    public static void setShopLink(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SET_SHOP_LINK, context, params,dialog, listener);
    }

    public static void getShopLink(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_SHOP_LINK, context, params,dialog, listener);
    }

    public static void getVarCode(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_VARCODE, context, params,dialog, listener);
    }
    public static void doLogin(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(DO_LOGIN, context, params,dialog, listener);
    }
    public static void getUserInfo(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_USERINFO, context, params,dialog, listener);
    }
    public static void getTerm(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_TERM, context, params,null, listener);
    }
    public static void getUserData(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_USERDATA, context, params,dialog, listener);
    }

    public static void getUserData1(final Context context, JSONObject params, final OnRequestDataListener listener) {
       // ProgressDialog dialog = ProgressDialog.show(context, "", "请稍后...", true);
        excutePost(GET_USERINFO, context, params,null, listener);
    }

    public static void setUserData(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SET_USERDATA, context, params,dialog, listener);
    }

    public static void forgetPassword(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(FORGET_PASSWORD, context, params,dialog, listener);
    }

    public static void changePassword(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CHANGE_PASSWORD, context, params,dialog, listener);
    }

    public static void feedBack(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(FEED_BACK, context, params,dialog, listener);
    }

    public static void getUserAttentionList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_USER_ATTENTION_LIST, context, params,dialog, listener);
    }

    public static void getUserFansList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_USER_FANS_LIST, context, params,dialog, listener);
    }

    public static void getUserContributionList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_USER_CONTRIBUTION_LIST, context, params,dialog, listener);
    }

    public static void getChannelList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_CHANNEL_LIST, context, params,dialog, listener);
    }

    public static void getAttentionChannelList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_ATTENTION_CHANNEL_LIST, context, params,null, listener);
    }

    public static void getLaunchScreen(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_LAUNCH_SCREEN, context, params,null, listener);
    }
    public static void getBanner(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_BANNER, context, params,null, listener);
    }

    public static void doSearch(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SEARCH, context, params,dialog, listener);
    }
    public static void startLive(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(START_LIVE, context, params,dialog, listener);
    }

    public static void addAttention(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(ADD_ATTENTION, context, params,dialog, listener);
    }
    public static void setLocation(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SET_LOCATION, context, params,null, listener);
    }

    public static void getChannleInfo(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_CHANNLE_INFO, context, params,dialog, listener);
    }
    public static void getGifts(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //ProgressDialog dialog = ProgressDialog.show(context, "", "请稍后...", true);
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_GIFTS, context, params,dialog, listener);
    }
    public static void sendGift(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SEND_GIFT, context, params,null, listener);
    }

    public static void sendDanmu(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SEND_DANMU, context, params,null, listener);
    }

    public static void sendReport(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SEND_REPORT, context, params,dialog, listener);
    }
    public static void addJinyan(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(ADD_JINYAN, context, params,dialog, listener);
    }
    public static void getLiveRealTimeNum(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(LIVING_REALTIME_DATA, context, params,null, listener);
    }

    public static void getOnlineUsers(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_LIVE_ROOM_ONLINE_LIST, context, params,null, listener);
    }

    public static void exitLiveRoom(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(EXIT_LIVE_ROOM, context, params,null, listener);
    }

    public static void cancelAttention(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CANCEL_ATTENTION, context, params,dialog, listener);
    }

    public static void stopPublish(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(STOP_PUBLISH, context, params,dialog, listener);
    }

    public static void getPublishRecord(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(PUBLISH_RECORD, context, params,dialog, listener);
    }

    public static void getShareInfo(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_SHARE_INFO, context, params,dialog, listener);
    }

    public static void thirdLogin(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(THIRD_LOGIN, context, params,dialog, listener);
    }
    public static void pay(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(PAY, context, params,dialog, listener);
    }

    public static void getChargePack(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_CHARGE_PACK, context, params,dialog, listener);
    }

    public static void payAli(final Context context, JSONObject params, final OnRequestDataListener listener) {
        SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(PAY_ALI, context, params,dialog, listener);
    }

    public static void checkUpdate(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CHECK_UPDATE, context, params,null, listener);
    }

    public static void setManager(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SET_MANAGERER, context, params,null, listener);
    }
    public static void cancelManager(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CANCEL_MANAGER, context, params,null, listener);
    }
    public static void getManagerList(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_MANAGER_LIST, context, params,null, listener);
    }
    public static void getTopic(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GET_TOPIC, context, params,null, listener);
    }

    /**
     *  主播开始游戏接口
     *  type: 游戏类别 ，目前设定为1
     token: 令牌
     */
    public static void start(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(START_URL, context, params, null, listener);
    }
    /**
     *  用户下注接口
     *  请求参数：
     {
     ‘chip’:’{"z":[1,2,4],"zd":[1,2,3],"h":[1,1,1]}’,
     （参数请看接口6 getChipType 获取对应id）
     ’gameid’:’游戏id’,
     ’token’:’令牌’,
     }
     */
    public static void chip(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CHIP_URL, context, params, null, listener);
    }
    public static void chipLonghu(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GAME_DOU_CHIP_URL, context, params, null, listener);
    }
    /**
     *  3、游戏心跳
     */
    public static void gameHeart(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GAMEHEART_URL, context, params, null, listener);
    }
    public static void gameHeartLonghu(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GAME_DOU_GAMEHEART_URL, context, params, null, listener);
    }
    /**
     *  主播开奖
     */
    public static void prize(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(PRIZE_URL, context, params, null, listener);
    }
    /**
     *  主播开奖
     */
    public static void record(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(RECORD_URL, context, params, null, listener);
    }

    /**
     *  禁止下注接口（主播使用）
     */
    public static void forbidChip(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(FORBIDCHIP_URL, context, params, null, listener);
    }

    /**
     *  获取用户余额接口
     *  token
     */
    public static void getBalanceLonghu(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GAME_DOU_GETBALANCE_URL, context, params, null, listener);
    }
    public static void getBalance(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GETBALANCE_URL, context, params, null, listener);
    }

    /**
     *  获取筹码类型接口
     */
    public static void getChipType(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GETCHIPTYPE_URL, context, params, null, listener);
    }
    public static void getChipTypeLonghu(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(GAME_DOU_GETCHIPTYPE_URL, context, params, null, listener);
    }
    /**
     *  7、主播获取上一场未开奖游戏接口
     */
    public static void checkRecovery(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(CHECKRECOVERY_URL, context, params, null, listener);
    }

    public static void doSwitchMode(final Context context, JSONObject params, final OnRequestDataListener listener) {
        //SFProgrssDialog dialog = SFProgrssDialog.show(context,"请稍后...");
        excutePost(SWITCH_MODE, context, params,null, listener);
    }
    protected static JSONObject getJsonObject(int statusCode, byte[] responseBody, OnRequestDataListener listener) {
        if (statusCode == 200) {
            String response = null;
            try {
                response = new String(responseBody, "UTF-8");
//                LogUtils.e("response=" + response);
                if (response != null) {
                    JSONObject object = JSON.parseObject(response);
                    int code = object.getIntValue("code");
                    if (code != 200) {
                        String desc = object.getString("descrp");
                        if (listener != null) {
                            listener.requestFailure(code, desc);
                            return null;
                        }
                    }
                    return object;
                }
                return null;
            } catch (Exception e) {
                if (listener != null) {
                    listener.requestFailure(-1, "json解析失败!");
                }
                return null;
            }
        } else {
            if (listener != null) {
                listener.requestFailure(-1, "网络请求失败!");
            }
            return null;
        }

    }

    protected static RequestParams getRequestParams(JSONObject params) {
        RequestParams requestParams = new RequestParams();
        Iterator<?> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = (String) params.getString(key);
            requestParams.put(key, value);
        }
        return requestParams;
    }

    protected static void excutePost(final String url, final Context context, final JSONObject params, final SFProgrssDialog dialog, final OnRequestDataListener listener) {
        params.put("os",OS);
        params.put("soft_ver",SOFT_VER);
        params.put("version_num",VERSION_NUM);
        String stamp = getTime();
        params.put("timestamp",stamp);
        params.put("sign",getMD5(KEY+stamp));
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = getRequestParams(params);
        LogUtils.e("url "+url+" param "+params);
        client.post(context, url, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if(null != context){
                        if(dialog != null && null != dialog.getContext() && null != dialog.getWindow())
                            dialog.dismiss();
                        JSONObject data = getJsonObject(statusCode, responseBody, listener);
                        String msg = "status code "+statusCode+" url "+url+" param "+params;

//                        try {
//                            int code = ((JSONObject) data.getJSONObject("data")).getInteger("code");
//                            if (code != 200){
//                                String descrp = null;
//                                try {
//                                    descrp = ((JSONObject) data.getJSONObject("data")).getString("descrp");
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                if (!TextUtils.isEmpty(descrp)) {
//                                    Toast.makeText(context,descrp,Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        if (data != null) {

                            listener.requestSuccess(statusCode, data);
                        }
                        LogUtils.e(msg+"\nresult  "+new String(responseBody,"utf-8"));
//                        LogUtils.e(msg+"\nresult  "+new String(responseBody)+" code "+statusCode);
                    }
                }catch (Exception e){

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try{
                    if(null != context){
                        if(dialog != null && null != dialog.getContext() && null != dialog.getWindow())
                            dialog.dismiss();
                        String response = "";
                        try {
                            if(null != responseBody)
                                response = new String(responseBody, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String msg = "statusCode "+statusCode+"  url "+url+" param "+params;
                        LogUtils.e(msg+"\nresponse=" + response);
                        if (listener != null) {
                            listener.requestFailure(-1, "网络请求失败!");
                        }
                    }
                }catch (Exception e){

                }
            }
        });
    }

    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    private static String getMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
        }
        byte[] b = messageDigest.digest();
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            int a = b[i];
            if(a<0)
                a+=256;
            if(a<16)
                buf.append("0");
            buf.append(Integer.toHexString(a));

        }
        return buf.toString();  //32位
    }

    public static String getTime(){

        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳

        String  str=String.valueOf(time);

        return str;

    }

    public static void excuteUpload(String url, final Context context, RequestParams params,final SFProgrssDialog dialog, final OnRequestDataListener listener) {
        params.put("os",OS);
        params.put("soft_ver",SOFT_VER);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(context, url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(dialog != null)
                    dialog.dismiss();
                JSONObject data = getJsonObject(statusCode, responseBody, listener);
                if (data != null) {
                    listener.requestSuccess(statusCode, data);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(dialog != null)
                    dialog.dismiss();
                if (listener != null) {
                    listener.requestFailure(-1, "网络请求失败!");
                }
            }
        });
    }


}
