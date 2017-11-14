package com.live.xue.game;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.rtc.kit.KSYRtcStreamer;
import com.ksyun.media.rtc.kit.RTCClient;
import com.ksyun.media.rtc.kit.RTCConstants;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.kit.OnAudioRawDataListener;
import com.ksyun.media.streamer.kit.OnPreviewFrameListener;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.litesuits.common.assist.Check;
import com.live.xue.R;
import com.live.xue.base.BaseActivity;
import com.live.xue.home.model.VideoItem;
import com.live.xue.intf.OnCustomClickListener;
import com.live.xue.intf.OnRequestDataListener;
import com.live.xue.living.AuthHttpTask;
import com.live.xue.living.LivingActivity;
import com.live.xue.utils.Api;
import com.live.xue.utils.DialogEnsureUtiles;
import com.live.xue.utils.Logs;
import com.smart.androidutils.images.GlideCircleTransform;
import com.smart.androidutils.utils.DensityUtils;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.smart.loginsharesdk.share.onekeyshare.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Ofx on 2017/11/9.
 */

public class GameLongHuActivity extends BaseActivity implements TextureView.SurfaceTextureListener{
    private static final String TAG = "GameLongHuActivity";
    //rtc
    private final static int PERMISSION_REQUEST_CAMERA_AUDIOREC = 1;
    private final String RTC_AUTH_SERVER = "http://rtc.vcloud.ks-live.com:6002/rtcauth";
    private final String RTC_AUTH_URI = "https://rtc.vcloud.ks-live.com:6001";
    private final String RTC_UINIQUE_NAME = "RTC_UINIQUE_NAME";
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    int chipType = 0;
    int xiaZhu = 0;
    List<String> chipList= new ArrayList<>();
    List<String> xiazhuList= new ArrayList<>();
    String xiaZhuStr;
    String gameid="0";
    String hostid;
    String roomid;

    TextView currentZhu;
    int timeDelay = 1000*30;
    Map<String,List<Integer>> zhuMap = new HashMap<String, List<Integer>>() ;
    @Bind(R.id.rv_game_left)
    RecyclerView rv_game_left;
    @Bind(R.id.rv_game_right)
    RecyclerView rv_game_right;

    @Bind(R.id.ll_c10)
    LinearLayout ll_c10;
    @Bind(R.id.ll_c50)
    LinearLayout ll_c50;
    @Bind(R.id.ll_c100)
    LinearLayout ll_c100;
    @Bind(R.id.ll_c500)
    LinearLayout ll_c500;
    @Bind(R.id.ll_c1000)
    LinearLayout ll_c1000;
    @Bind(R.id.ll_c10000)
    LinearLayout ll_c10000;

    @Bind(R.id.item_l)
    RelativeLayout item_l;
    @Bind(R.id.item_hu)
    RelativeLayout item_hu;
    @Bind(R.id.item_h)
    RelativeLayout item_h;
//    @Bind(R.id.item_x)
//    RelativeLayout item_x;
//    @Bind(R.id.item_z)
//    RelativeLayout item_z;

    @Bind(R.id.btn_confirm)
    Button btn_confirm;
    @Bind(R.id.btn_cancel)
    Button btn_cancel;

    @Bind(R.id.tv_zhu_l)
    TextView tv_zhu_l;
    @Bind(R.id.tv_zhu_h)
    TextView tv_zhu_h;
    @Bind(R.id.tv_zhu_hu)
    TextView tv_zhu_hu;


    // SurfaceView需在Layout中定义，此处不在赘述
    private Surface mSurface = null;
    private SurfaceView mVideoSurfaceView = null;
    private SurfaceHolder mSurfaceHolder = null;
    private TextureView mVideoTextureView = null;
    private SurfaceTexture mSurfaceTexture = null;
    private String mDataSource;
    private int mVideoWidth = 0;
    private int mVideoHeight = 0;

    public String token;
    public String userId;//用户id

    //实时数据
    Handler dataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private GLSurfaceView mCameraPreviewView = null;
    private KSYRtcStreamer mStreamer;
    private AuthHttpTask mRTCAuthTask;
    private AuthHttpTask.KSYOnHttpResponse mRTCAuthResponse;

    private KSYMediaPlayer ksyMediaPlayer;
    private final SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (ksyMediaPlayer != null && ksyMediaPlayer.isPlaying())
                ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (ksyMediaPlayer != null)
                ksyMediaPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed");
            if (ksyMediaPlayer != null) {
                ksyMediaPlayer.setDisplay(null);
            }
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Message mssg = handler.obtainMessage();
                mssg.what = 1;
                handler.sendMessageDelayed(mssg,timeDelay);
                gameHeart();
            }
        }
    };
    private Activity mContext;


    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {

            mVideoWidth = ksyMediaPlayer.getVideoWidth();
            mVideoHeight = ksyMediaPlayer.getVideoHeight();

            // Set Video Scaling Mode
            ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            Logs.e("setRotateDegree");
//            ksyMediaPlayer.setRotateDegree(90);
//            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
            //start player
            ksyMediaPlayer.start();


        }
    };
    private boolean mIsConnected;
    private boolean mIsRegisted;
    private boolean mPause;
    private VideoItem mVideoItem;
    private JSONObject channelInfo;
    private JSONArray sysMessage;


    Runnable dataRunnable = new Runnable() {
        @Override
        public void run() {
            JSONObject params = new JSONObject();
            params.put("token", token);
            params.put("room_id", channelInfo.getString("room_id"));
            Api.getLiveRealTimeNum(mContext, params, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data1) {
                    if (isActive) {
                        JSONObject data = data1.getJSONObject("data");
                        dataHandler.postDelayed(dataRunnable, 2000);

                    }
                }

                @Override
                public void requestFailure(int code, String msg) {
                    switch (code) {
                        case 502:
                            if (ksyMediaPlayer != null) {
                                ksyMediaPlayer.pause();
                            }

//                            namedialog.hide();
//                            namedialog.dismiss();
                            toast("直播结束");
                            break;
                        case 506:
                            if (ksyMediaPlayer != null) {
                                ksyMediaPlayer.pause();
                            }

                            toast("直播被封禁");
                            break;
                        case 500:
                            toast(msg);
                            break;
                        default:
                            dataHandler.postDelayed(dataRunnable, 2000);
                            break;
                    }

                }
            });
        }
    };
    private Handler mMainHandler;

    private void doRTCCallBreak() {
        mIsConnected = false;
        Toast.makeText(this, "call break", Toast
                .LENGTH_SHORT).show();
        //to stop RTC video/audio
        mStreamer.stopRTC();
        mStreamer.setRTCMainScreen(RTCConstants.RTC_MAIN_SCREEN_CAMERA);
    }

    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            long duration = ksyMediaPlayer.getDuration();
            long progress = duration * percent / 100;
//            mPlayerSeekbar.setSecondaryProgress((int)progress);
        }
    };
    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
            if (mVideoWidth > 0 && mVideoHeight > 0) {
                if (width != mVideoWidth || height != mVideoHeight) {
                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();

                    if (ksyMediaPlayer != null)
                        ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                }
            }
        }
    };
    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompletedListener = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            Log.e(TAG, "onSeekComplete...............");
        }
    };
    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            //    toast("OnCompletionListener, play complete.");
//            videoPlayEnd();
        }
    };
    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            switch (what) {
                case KSYMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    Log.e(TAG, "OnErrorListener, Error Unknown:" + what + ",extra:" + extra);
                    break;
                default:
                    Log.e(TAG, "OnErrorListener, Error:" + what + ",extra:" + extra);
            }

//            videoPlayEnd();

            return false;
        }
    };
    private StatsLogReport.OnLogEventListener mOnLogEventListener =
            new StatsLogReport.OnLogEventListener() {
                @Override
                public void onLogEvent(StringBuilder singleLogContent) {
                    Log.i(TAG, "***onLogEvent : " + singleLogContent.toString());
                }
            };
    private OnAudioRawDataListener mOnAudioRawDataListener = new OnAudioRawDataListener() {
        @Override
        public short[] OnAudioRawData(short[] data, int count) {
            Log.d(TAG, "OnAudioRawData data.length=" + data.length + " count=" + count);
            //audio pcm data
            return data;
        }
    };
    private OnPreviewFrameListener mOnPreviewFrameListener = new OnPreviewFrameListener() {
        @Override
        public void onPreviewFrame(byte[] data, int width, int height, boolean isRecording) {
            Log.d(TAG, "onPreviewFrame data.length=" + data.length + " " +
                    width + "x" + height + " isRecording=" + isRecording);
        }
    };
    private RTCClient.RTCErrorListener mRTCErrorListener = new RTCClient.RTCErrorListener() {
        @Override
        public void onError(int errorType, int arg1) {
            Log.e(TAG,"mRTCErrorListener +type "+ errorType +" arg "+arg1);
            switch (errorType) {
                case RTCClient.RTC_ERROR_AUTH_FAILED:
                    doAuthFailed();
                    break;
                case RTCClient.RTC_ERROR_REGISTED_FAILED:
                    doRegisteredFailed(arg1);
                    break;
                case RTCClient.RTC_ERROR_SERVER_ERROR:
                case RTCClient.RTC_ERROR_CONNECT_FAIL:
                    doRTCCallBreak();
                    break;
                case RTCClient.RTC_ERROR_STARTED_FAILED:
                    doStartCallFailed(arg1);
                    break;
                default:
                    break;
            }
        }
    };

    private void doStartCallFailed(int status) {
        mIsConnected = false;
        //if remote receive visible need hide

        Toast.makeText(this, "call failed: " + status, Toast
                .LENGTH_SHORT).show();

    }


    private void doReceiveRemoteCall(final String remoteUri) {
        //当前版本支持1对1的call
        if (mIsConnected) {
            mStreamer.getRtcClient().rejectCall();
        } else {
            //startCameraPreviewWithPermCheck();
            //mLianmaiContainer.setVisibility(View.VISIBLE);
            //接收拒绝
//            if (!Check.isEmpty(type)) {
//            toast("禁止连麦");
//                return;
//            }

            new AlertDialog.Builder(this)
                    .setMessage("主播想与你连麦？")
                    .setPositiveButton("接收", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

//                            if (Integer.parseInt(user_level)<17) {
                                toast("大于17级方可连麦");
//                                return;
//                            }


                            if (ksyMediaPlayer != null) {
                                ksyMediaPlayer.pause();
                                mVideoSurfaceView.setVisibility(View.INVISIBLE);
                            }
                            //startCameraPreviewWithPermCheck();
                            FrameLayout.LayoutParams tvLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT);
                            mCameraPreviewView.setLayoutParams(tvLayoutParams);
                            mStreamer.getRtcClient().answerCall();
//                            check.setVisibility(View.VISIBLE);
                        }
                    })
                    .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mStreamer.getRtcClient().rejectCall();
                        }
                    })
                    .show();

        }
    }

    private void doRegisteredSuccess() {
        mIsRegisted = true;
    }

    private void doStartCallSuccess() {
        mIsConnected = true;
        //can stop call
        mStreamer.startRTC();

        mStreamer.setRTCMainScreen(RTCConstants.RTC_MAIN_SCREEN_CAMERA);
//        mLianmaiStop.setVisibility(View.VISIBLE);
    }

    private void doStopCallResult() {
        mIsConnected = false;
        //can start call again
        //to stop RTC video/audio
        mStreamer.stopRTC();
        mStreamer.setRTCMainScreen(RTCConstants.RTC_MAIN_SCREEN_CAMERA);
//        mLianmaiStop.setVisibility(View.GONE);
        if (ksyMediaPlayer != null) {
            FrameLayout.LayoutParams tvLayoutParams = (FrameLayout.LayoutParams) mCameraPreviewView.getLayoutParams();
            tvLayoutParams.height = 1;
            tvLayoutParams.width = 1;
            mCameraPreviewView.setLayoutParams(tvLayoutParams);
            mVideoSurfaceView.setVisibility(View.VISIBLE);
            ksyMediaPlayer.start();
            mPause = false;

        }

    }
    private RTCClient.RTCEventChangedListener mRTCEventListener = new RTCClient.RTCEventChangedListener() {
        @Override
        public void onEventChanged(int event, Object arg1) {
            switch (event) {
                case RTCClient.RTC_EVENT_REGISTED:
                    doRegisteredSuccess();

                    break;
                case RTCClient.RTC_EVENT_STARTED:
                    doStartCallSuccess();
                    break;
                case RTCClient.RTC_EVENT_CALL_COMMING:
                    doReceiveRemoteCall(String.valueOf(arg1));
                    break;
                case RTCClient.RTC_EVENT_STOPPED:
                    Log.d(TAG, "stop result:" + arg1);
                    doStopCallResult();
                    break;
                case RTCClient.RTC_EVENT_UNREGISTED:
                    Log.d(TAG, "unregister result:" + arg1);
                    doUnRegisteredResult();
                    break;
                default:
                    break;
            }

        }
    };


    private void doAuthFailed() {
        //register failed

        //can register again
        mIsRegisted = false;
    }

    private void doRegisteredFailed(int failed) {
        //register failed

        //can register again
        mIsRegisted = false;
        if (mIsConnected) {
            mStreamer.stopRTC();
            mIsConnected = false;
        }
    }


    private void doUnRegisteredResult() {
        mIsRegisted = false;
        if (mIsConnected) {
            mStreamer.stopRTC();
            mIsConnected = false;
        }
        //can register again

        mStreamer.setRTCMainScreen(RTCConstants.RTC_MAIN_SCREEN_CAMERA);
    }

    private void doRegister(String authString) {
        //must set before register
        Log.e(TAG,"doRegister userId "+userId);
        mStreamer.setRTCSubScreenRect(0.65f, 0.7f, 0.35f, 0.3f, RTCConstants.SCALING_MODE_CENTER_CROP);
        mStreamer.getRtcClient().setRTCAuthInfo(RTC_AUTH_URI, authString, userId);
        mStreamer.getRtcClient().setRTCUniqueName(RTC_UINIQUE_NAME);
        //has default value
        mStreamer.getRtcClient().openChattingRoom(false);
        mStreamer.getRtcClient().setRTCResolutionScale(0.5f);
        mStreamer.getRtcClient().setRTCFps(15);
        mStreamer.getRtcClient().setRTCVideoBitrate(256 * 1024);
//        mStreamer.getRtcClient().registerRTC();
        Log.e(TAG,"doRegister end");
    }

    private void doUnRegister() {
        mStreamer.getRtcClient().unRegisterRTC();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //reinitTextureView((TextureView) findViewById(R.id.player_texture));

        if (ksyMediaPlayer != null) {
            ksyMediaPlayer.start();
            mPause = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        EventBus.getDefault().unregister(this);
        if (ksyMediaPlayer != null) {
//            ksyMediaPlayer.pause();
            mPause = true;

        }
        mStreamer.onPause();
        mStreamer.stopCameraPreview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCameraPreviewWithPermCheck();
//        EventBus.getDefault().register(this);
        if (ksyMediaPlayer != null) {
            ksyMediaPlayer.start();
            mPause = false;
        }
        mStreamer.setAudioOnly(false);
        mStreamer.onResume();
    }

    private void startCameraPreviewWithPermCheck() {
        int cameraPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int audioPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (cameraPerm != PackageManager.PERMISSION_GRANTED ||
                audioPerm != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Log.e(TAG, "No CAMERA or AudioRecord permission, please check");
                Toast.makeText(this, "No CAMERA or AudioRecord permission, please check",
                        Toast.LENGTH_LONG).show();
            } else {
                String[] permissions = {Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions,
                        PERMISSION_REQUEST_CAMERA_AUDIOREC);
            }
        } else {
            mStreamer.startCameraPreview();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
//        List<List<String>> record = GameResult.mokeData().getData().getRecord();
        GameLeftAdapter adapter = new GameLeftAdapter(new ArrayList<List<String>>());
        if (Logs.isDebug) {
            rv_game_left.setLayoutManager(new GridLayoutManager(this,11));
            rv_game_left.setAdapter(adapter);
        }

//        rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//        rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        List<List<String>> chart = new ArrayList<>();
//        List<List<String>> chart = GameResult.mokeData().getData().getChart();
        GameRightAdapter Rightadapter = new GameRightAdapter(chart);

        int width = DensityUtils.getWindowWidth(this);
        int rowCount = (width-DensityUtils.dip2px(this,15)*12) / DensityUtils.dip2px(this,15);
        if (Logs.isDebug) {
            rv_game_right.setLayoutManager(new GridLayoutManager(this,rowCount));
            rv_game_right.setAdapter(Rightadapter);
        }

//        rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//        rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        token = (String) SharePrefsUtils.get(this, "user", "token", "");
        Bundle data = getIntent().getExtras();

        JSONObject params = new JSONObject();
        if (null != data) {
            mVideoItem = (VideoItem) data.getSerializable("videoItem");
            if (mVideoItem != null) {
                Log.e(TAG,mVideoItem.toString());
            }else {
                Log.e(TAG,"mVideoItem is null");
            }
            hostid = (String) data.get("hostid");
            roomid = (String) data.get("roomid");
            userId = (String) SharePrefsUtils.get(this, "user", "userId", "");
            String roomPassword = data.getString("password");

            params.put("room_id", mVideoItem.getRoom_id());
            params.put("token", token);
            params.put("room_password", roomPassword);
        }


//        hostid：2，roomid：1，token：123
//        if (Logs.isDebug) {
//            token = "123";
//            roomid = "1";
//            hostid = "2";
//        }


        Message msg = handler.obtainMessage(1);
        handler.sendMessage(msg);
        initXiazhuItems();
        getChipType();
        getBance();

        String name = (String) SharePrefsUtils.get(this, "user", "user_nicename","user");
        ((TextView) findViewById(R.id.game_user_name)).setText(name);

        // live set
        mVideoSurfaceView = (SurfaceView) findViewById(R.id.player_surface);
        mSurfaceHolder = mVideoSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);
        mVideoSurfaceView.setKeepScreenOn(true);
        mContext = this;
        ksyMediaPlayer = new KSYMediaPlayer.Builder(this).build();
        ksyMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        ksyMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        ksyMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        ksyMediaPlayer.setOnInfoListener(mOnInfoListener);
        ksyMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
        ksyMediaPlayer.setOnErrorListener(mOnErrorListener);
        ksyMediaPlayer.setOnSeekCompleteListener(mOnSeekCompletedListener);
        ksyMediaPlayer.setScreenOnWhilePlaying(true);
        ksyMediaPlayer.setBufferTimeMax(3);
        ksyMediaPlayer.setTimeout(5, 30);

        Api.getChannleInfo(this, params, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if (isActive) {
                    Log.e(TAG,data.toJSONString());
                    channelInfo = data.getJSONObject("data");
                    sysMessage = data.getJSONArray("msg");
//
                    mDataSource = channelInfo.getString("channel_source");
                    //mDataSource = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
                    if (null != mDataSource) {
                        //loading_dialog = SFProgrssDialog.show(LivingActivity.this, "");
                        try {
                            ksyMediaPlayer.setDataSource(mDataSource);
                            ksyMediaPlayer.prepareAsync();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
//                    dataHandler.postDelayed(dataRunnable, 2000);
                }

            }
            @Override
            public void requestFailure(int code, String msg) {
                toast(msg);

                //mLiveEndContainer.setVisibility(View.VISIBLE);
            }
        });
        mCameraPreviewView = (GLSurfaceView) findViewById(R.id.camera_preview);
        mMainHandler = new Handler();
        mStreamer = new KSYRtcStreamer(this);
        //mStreamer.setUrl("rtmp://test.uplive.ks-cdn.com/live/androidtest");
        mStreamer.setPreviewFps(15);
        mStreamer.setTargetFps(15);
        int videoBitrate = 800;
        videoBitrate *= 1000;
        mStreamer.setVideoBitrate(videoBitrate * 3 / 4, videoBitrate, videoBitrate / 4);
        mStreamer.setAudioBitrate(48 * 1000);
        mStreamer.setPreviewResolution(StreamerConstants.VIDEO_RESOLUTION_480P);
        mStreamer.setTargetResolution(StreamerConstants.VIDEO_RESOLUTION_480P);
        mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        setOrition();
        mStreamer.setDisplayPreview(mCameraPreviewView);
        mStreamer.setEnableStreamStatModule(true);
        mStreamer.enableDebugLog(true);
        mStreamer.setFrontCameraMirror(false);
        mStreamer.setMuteAudio(false);
        mStreamer.setEnableAudioPreview(false);
        // mStreamer.startCameraPreview();
        //mStreamer.setOnInfoListener(mOnInfoListener);
        // mStreamer.setOnErrorListener(mOnErrorListener);
        mStreamer.setOnLogEventListener(mOnLogEventListener);
        mStreamer.setVoiceVolume(1.0f);
        mStreamer.setRTCRemoteVoiceVolume(1.0f);
        //mStreamer.setOnAudioRawDataListener(mOnAudioRawDataListener);
        //mStreamer.setOnPreviewFrameListener(mOnPreviewFrameListener);
        mStreamer.getImgTexFilterMgt().setFilter(mStreamer.getGLRender(),
                ImgTexFilterMgt.KSY_FILTER_BEAUTY_SKINWHITEN);
        mStreamer.setEnableImgBufBeauty(true);
        mStreamer.getImgTexFilterMgt().setOnErrorListener(new ImgTexFilterBase.OnErrorListener() {
            @Override
            public void onError(ImgTexFilterBase filter, int errno) {
                toast("当前机型不支持该滤镜");
                mStreamer.getImgTexFilterMgt().setFilter(mStreamer.getGLRender(),
                        ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE);
            }
        });
        mStreamer.getRtcClient().setRTCErrorListener(mRTCErrorListener);
        //toast(mStreamer.getVersion());
        mStreamer.getRtcClient().setRTCEventListener(mRTCEventListener);
        dataHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onRTCRegisterClick();
            }
        }, 5000);

    }


    private void onRTCRegisterClick() {
        if (mRTCAuthResponse == null) {
            if (isActive) {
                mRTCAuthResponse = new AuthHttpTask.KSYOnHttpResponse() {
                    @Override
                    public void onHttpResponse(int responseCode, final String response) {
                        Log.e(TAG,"onRTCRegisterClick code "+responseCode+"  "+response +" "+TAG);
                        if (responseCode == 200) {
                            mMainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        doRegister(response);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.e(TAG,"onRTCRegisterClick  "+e);
                                    }
                                }
                            });
                        } else {

                        }
                    }
                };

            }
        }
        if (!mIsRegisted) {
            if (null != mRTCAuthResponse) {
                mRTCAuthTask = new AuthHttpTask(mRTCAuthResponse);
                mRTCAuthTask.execute(RTC_AUTH_SERVER + "?uid=" + userId);
                //do not registed when registing
            }

        } else {
            doUnRegister();
        }

    }

    public IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
            switch (i) {
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Log.d(TAG, "Buffering Start.");
                    break;
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Log.d(TAG, "Buffering End.");
                    break;
                case KSYMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    //toast("Audio Rendering Start");
//                    if (null != loading_dialog) {
//                        loading_dialog.dismiss();
//                    }
                    break;
                case KSYMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    //toast("Video Rendering Start");
//                    if (null != loading_dialog) {
//                        loading_dialog.dismiss();
//                    }
                    break;
                case KSYMediaPlayer.MEDIA_INFO_SUGGEST_RELOAD:
                    // Player find a new stream(video or audio), and we could reload the video.
                    if (ksyMediaPlayer != null)
                        ksyMediaPlayer.reload(mDataSource, false);
                    break;
                case KSYMediaPlayer.MEDIA_INFO_RELOADED:
                    //toast("Succeed to reload video.");
                    Log.d(TAG, "Succeed to reload video.");
                    return false;
            }
            return false;
        }
    };
    private void initXiazhuItems(){
        setXiazhuInfo(item_l,"DRAGOM","龙","1:10",R.color.color_ff0000);//num3
        setXiazhuInfo(item_h,"TIE","和","1:10",R.color.color_00ba00);
        setXiazhuInfo(item_hu,"TIGER","虎","1:10",R.color.num3);
//        setXiazhuInfo(item_x,"PLAYER","闲","1:10",R.color.num3);
//        setXiazhuInfo(item_z,"BANNER","庄","1:10",R.color.color_ff0000);
    }

    private void setXiazhuInfo(RelativeLayout item,String title ,String name,String score,int coclor){
        TextView titleVIew  = (TextView) item.findViewById(R.id.tv_title);
        TextView nameView  = (TextView) item.findViewById(R.id.tv_name);
        TextView scoreView  = (TextView) item.findViewById(R.id.tv_score);
        titleVIew.setText(title);
        titleVIew.setTextColor(getResources().getColor(coclor));
        nameView.setText(name);
        nameView.setTextColor(getResources().getColor(coclor));
        scoreView.setText(score);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_game_dou;
    }

    @OnClick({R.id.ll_c10,R.id.ll_c50,R.id.ll_c100,R.id.ll_c500,R.id.ll_c1000,R.id.ll_c10000})
    public void onClickChip(View view){
        //每点一次当距投注信息就往上加
        ll_c10.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c50.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c100.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c500.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c1000.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c10000.setBackgroundResource(R.drawable.btn_topic_border_black);
        view.setBackgroundResource(R.drawable.btn_topic_border_red);



        if (view.getId() == R.id.ll_c10){
            selectChip(currentZhu,10,view);
            chipType = 1;
        }else if(view.getId() == R.id.ll_c50){
            selectChip(currentZhu,50,view);
            chipType = 2;
        }else if(view.getId() == R.id.ll_c100){
            selectChip(currentZhu,100,view);
            chipType = 3;
        }else if(view.getId() == R.id.ll_c500){
            selectChip(currentZhu,500,view);
            chipType = 4;
        }else if(view.getId() == R.id.ll_c1000){
            selectChip(currentZhu,1000,view);
            chipType = 5;
        }else if(view.getId() == R.id.ll_c10000){
            selectChip(currentZhu,10000,view);
            chipType = 6;
        }
    }

    private void selectChip(TextView view,int  num,View chipView){
        if (view != null) {
            String type = (String) chipView.getTag();
            int typeC = 0;
            try {
                typeC = Integer.parseInt(type);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            addToMap(xiaZhuStr,typeC);
            int current = Integer.parseInt(view.getText().toString());
            view.setText(current+num+"");
        }
    }

    private void addToMap(String key,int chipType){
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(xiaZhuStr)) {
            return;
        }
        if (key.equals(xiaZhuStr)){
            List<Integer> list = null;
            if (zhuMap.containsKey(key)) {
                list = zhuMap.get(key);
            }else {
                list = new ArrayList<>();
                zhuMap.put(key,list);
            }
            list.add(chipType);
        }
    }


    @OnClick({R.id.item_l,R.id.item_hu,R.id.item_h})
    public void onClickXiazhu(View view){
        item_l.setBackgroundResource(R.drawable.borde_black);
        item_hu.setBackgroundResource(R.drawable.borde_black);
        item_h.setBackgroundResource(R.drawable.borde_black);

        view.setBackgroundResource(R.drawable.borde_red);


        if (view.getId() == R.id.item_l){
            selectXiazhu(view,"long");
            xiaZhu = 1;
            xiaZhuStr = "long";
            currentZhu = tv_zhu_l;
        }else if(view.getId() == R.id.item_hu){
            xiaZhu = 2;
            xiaZhuStr = "hu";
            currentZhu = tv_zhu_hu;
            selectXiazhu(view,"hu");
        }else if(view.getId() == R.id.item_h){
            xiaZhu = 3;
            xiaZhuStr = "h";
            selectXiazhu(view,"h");
            currentZhu = tv_zhu_h;
        }
    }

    private void selectXiazhu(View view,String type){

    }

    @OnClick(R.id.btn_confirm)
    public void clickConfirm(View view){
        if (zhuMap.isEmpty()) {
            DialogEnsureUtiles.showConfirm(this, "请选择筹码", new OnCustomClickListener() {
                @Override
                public void onClick(String value) {

                }
            });

            return;
        }
        if (TextUtils.isEmpty(xiaZhuStr)) {
            DialogEnsureUtiles.showConfirm(this, "请先下庄", new OnCustomClickListener() {
                @Override
                public void onClick(String value) {

                }
            });
            return;
        }
        //开始下注
        com.alibaba.fastjson.JSONObject param = new com.alibaba.fastjson.JSONObject();
//        JSONArray array = new JSONArray();
//        array.add(chipType);
//        JSONObject zhuType = new JSONObject();
//        zhuType.put(xiaZhuStr,array);
        param.put("chip",zhuMap);
        param.put("gameid",gameid);
        param.put("token",token);

        Api.chipLonghu(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, com.alibaba.fastjson.JSONObject data) {
                data = data.getJSONObject("data");
                String type = (String) data.get("type");
//                String balance = (String) data.get("type");
//                String price = (String) data.get("type");
//                setResult(type);
                toast("下注成功");
                clickCancel(null);
            }

            @Override
            public void requestFailure(int code, String msg) {
                toast("下注失败");
//                clickCancel(null);
            }
        });
    }

    @OnClick(R.id.btn_cancel)
    public void clickCancel(View view){
        item_l.setBackgroundResource(R.drawable.borde_black);
        item_hu.setBackgroundResource(R.drawable.borde_black);
        item_h.setBackgroundResource(R.drawable.borde_black);

        chipList.clear();
        ll_c10.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c50.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c100.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c500.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c1000.setBackgroundResource(R.drawable.btn_topic_border_black);
        ll_c10000.setBackgroundResource(R.drawable.btn_topic_border_black);
        xiazhuList.clear();
        currentZhu = null;
        zhuMap.clear();
        tv_zhu_h.setText("0");
        tv_zhu_l.setText("0");

        tv_zhu_hu.setText("0");

    }

    private void gameStart(){
        JSONObject param = new JSONObject();
        param.put("type","1");
        param.put("token","1");
        Api.start(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                data = data.getJSONObject("data");
                gameid = (String) data.get("gameid");
                GameStartResult.Game_info game_info = data.getObject("game_info",GameStartResult.Game_info.class);

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    /**0:未开奖（gameid对应的开奖结果），参数：（z:庄赢，zd庄对赢，x：闲赢，xd：闲对赢，h：和赢）*/
    private void setResult(String result){
        if ("hu".equals(result)){
            addResult(tv_zhu_hu);
        } else if ("long".equals(result)){
            addResult(tv_zhu_l);
        } else if ("h".equals(result)){
            addResult(tv_zhu_h);
        }
    }

    private void addResult(TextView textView){
        try {
            int num = Integer.parseInt(textView.getText().toString());
            num++;
            textView.setText(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
    /**{
     “hostid”:主播id
     roomid：房间id
     gameid：游戏id，传初始化0，第一次心跳后，如果存在已开始游戏，返回游戏id，获取gameid后，客户端必须传递gameid，以作为服务端开奖结果查询，开奖后，服务端自动重置为0，主播再次点击开始下注后，心跳可获取对应gameid。总之就是服务端传gameid是什么值，客户端收到后就传什么到服务端
     }*/
    public void gameHeart(){
        JSONObject param = new JSONObject();
        param.put("hostid",hostid);
        param.put("roomid",roomid);
        param.put("gameid",gameid);
        Api.gameHeartLonghu(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                data = data.getJSONObject("data");
                gameid = (String) data.get("gameid");
                String result = (String) data.get("result");
                gameid = (String) data.get("gameid");
                JSONObject setting = (JSONObject) data.get("setting");
                setPeiRate(setting);
                JSONArray array = data.getJSONArray("chart");
                JSONArray record = data.getJSONArray("record");
                setRightRecyleData(array);
                setLeftRecycle(record);
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    GameLeftAdapter gameLeftAdapter;
    GridLayoutManager leftLayoutManeger;
    private void setLeftRecycle(JSONArray array){
        List<List<String>> record = new ArrayList<>();
        for (Object o : array) {
            JSONArray jsonArray = (JSONArray) o;
            List<String> list = new ArrayList<>();
            for (Object o1 : jsonArray) {
                list.add((String) o1);
            }

            record.add(list);
        }

        if (gameLeftAdapter == null) {
            gameLeftAdapter = new GameLeftAdapter(record);
            rv_game_left.setAdapter(gameLeftAdapter);
//            rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//            rv_game_left.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        }else {
            gameLeftAdapter.setData(record);
        }
        gameLeftAdapter.notifyDataSetChanged();
        //先找出最长的一排
        int rowCount=0;
        for (int i = 0; i < record.size(); i++) {
            if (record.get(i).size()> rowCount) {
                rowCount = record.get(i).size();
            }
        }
        if (leftLayoutManeger == null) {
            leftLayoutManeger = new GridLayoutManager(this,rowCount);
            rv_game_left.setLayoutManager(leftLayoutManeger);
        }else {
            leftLayoutManeger.setSpanCount(rowCount);
        }
    }

    GameRightAdapter Rightadapter;
    GridLayoutManager rightGridManeger;
    private void setRightRecyleData(JSONArray array){
        List<List<String>> chart = new ArrayList<>();
        for (Object o : array) {
            JSONArray jsonArray = (JSONArray) o;
            List<String> list = new ArrayList<>();
            for (Object o1 : jsonArray) {
                list.add((String) o1);
            }

            chart.add(list);
        }

        if (Rightadapter == null) {
            Rightadapter = new GameRightAdapter(chart);
            rv_game_right.setAdapter(Rightadapter);
//            rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//            rv_game_right.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        }else {
            Rightadapter.setData(chart);
        }
        Rightadapter.notifyDataSetChanged();
        //先找出最长的一排
        int rowCount=0;
        for (int i = 0; i < chart.size(); i++) {
            if (chart.get(i).size()> rowCount) {
                rowCount = chart.get(i).size();
            }
        }

        int width = DensityUtils.getWindowWidth(this);
        rowCount = (width-DensityUtils.dip2px(this,15)*11)/DensityUtils.dip2px(this,15);

        if (rightGridManeger == null) {
            rightGridManeger = new GridLayoutManager(this,rowCount);
            rv_game_right.setLayoutManager(rightGridManeger);
        }else {
            rightGridManeger.setSpanCount(rowCount);
        }

    }

    //赔率
    /*{z => 1.1   庄赔率
                    zd => 0.90 庄对赔率
                    x=> 0.85  闲赔率
                    xd => 1.2  闲对赔率
                    h => 0.9  和赔率
}*/
    private void setPeiRate(JSONObject data){
        String  z  = data.getString("l");
        ((TextView) item_l.findViewById(R.id.tv_score)).setText("1:"+z);
        String  x  = data.getString("x");
        ((TextView) item_h.findViewById(R.id.tv_score)).setText("1:"+x);
        String  h  = data.getString("hu");
        ((TextView) item_hu.findViewById(R.id.tv_score)).setText("1:"+h);
//        String  zd  = data.getString("zd");
//        ((TextView) item_z.findViewById(R.id.tv_score)).setText("1:"+zd);
//        String  xd = data.getString("xd");
//        ((TextView) item_z.findViewById(R.id.tv_score)).setText("1:"+xd);


    }
    /*chipType{
[  数组
 		 id=> 1 类型id
         giftname]=> 筹码500   筹码名称
         needcoin => 500		筹码价格
         gifticon_25]=> /deerlive/data/upload/20170604/59338df28cf72.jpg  筹码缩略图地址
         gifticon => /deerlive/data/upload/20170604/59339b4ad15ed.jpg   筹码图片地址
]
}*/
    private void getChipType(){
        Api.getChipTypeLonghu(this, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                data = data.getJSONObject("data");
                JSONArray chipType = data.getJSONArray("chipType");
                for (Object o : chipType) {
                    JSONObject object = (JSONObject) o;
                    String coin = object.getString("needcoin");
                    String type = object.getString("id");
                    if ("10".equals(coin)){
                        ll_c10.setTag(type);
                    }else if ("50".equals(coin)){
                        ll_c50.setTag(type);
                    }else if ("100".equals(coin)){
                        ll_c100.setTag(type);
                    }else if ("500".equals(coin)){
                        ll_c500.setTag(type);
                    }else if ("1000".equals(coin)){
                        ll_c1000.setTag(type);
                    }else if ("10000".equals(coin)){
                        ll_c10000.setTag(type);
                    }
                }
                Logs.e("getChipType");
            }

            @Override
            public void requestFailure(int code, String msg) {
                Logs.e(msg+"getChipType");
            }
        });
    }

    private void getBance(){
        JSONObject param = new JSONObject();
        param.put("token",token);
        Api.getBalanceLonghu(this, param, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                data = data.getJSONObject("data");
                String Balance = (String) data.get("balance");
                ((TextView) findViewById(R.id.game_money)).setText(Balance);
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int i, int i1) {
        if (mSurfaceTexture == null) {
            mSurfaceTexture = surface;
        }
        if (mSurface == null) {
            mSurface = new Surface(mSurfaceTexture);
        }

        if (ksyMediaPlayer != null) {
            Log.e(TAG, "setSurface ..........");
            ksyMediaPlayer.setSurface(mSurface);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.e(TAG, "onSurfaceTextureDestroyed ...............");
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataHandler != null) {
            dataHandler.removeCallbacks(dataRunnable);
        }
//        if (helper != null) {
//            // helper.removeSoftKeyboardStateListener(mSoftKeyboardStateListener);
//        }
        if (ksyMediaPlayer != null) {
            ksyMediaPlayer.release();
            ksyMediaPlayer = null;
        }
        mVideoTextureView = null;
        mSurfaceTexture = null;
        if (mIsConnected) {
            mStreamer.getRtcClient().stopCall();
        }

        if (mIsRegisted) {
            mStreamer.getRtcClient().unRegisterRTC();
        }
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }
        mStreamer.setOnLogEventListener(null);

        mStreamer.stopCameraPreview();
        mStreamer.release();
    }
}
