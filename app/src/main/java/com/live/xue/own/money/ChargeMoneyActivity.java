package com.live.xue.own.money;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.live.xue.MyApplication;
import com.live.xue.R;
import com.live.xue.base.BaseActivity;
import com.live.xue.intf.OnCustomClickListener;
import com.live.xue.intf.OnRequestDataListener;
import com.live.xue.pay.alipay.PayResult;
import com.live.xue.utils.Api;
import com.live.xue.utils.DialogEnsureUtiles;
import com.smart.androidutils.utils.SharePrefsUtils;
import com.smart.androidutils.utils.StringUtils;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ChargeMoneyActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.charge_container)
    LinearLayout mChargeContainer;
//    @Bind(R.id.text_top_title)
//    TextView mTextTopTitle;
//    @Bind(R.id.frame_yuanbao_container)
//    FrameLayout mFrameYuanBaoContainer;
    @Bind(R.id.text_my_gold_num)
    TextView mTextMyGoldNum;
//    @Bind(R.id.text_my_gold_price)
//    TextView mTextMyGoldPrice;
//    @Bind(R.id.edit_charge_gold_num)
//    EditText mEditChargeGoldNum;
//    @Bind(R.id.text_pay_num)
//    TextView mTextPayNum;
//    @Bind(R.id.text_pay_num_desc)
//    TextView mTextPayNumDesc;
//    @Bind(R.id.text_pay_num_yuan)
//    TextView mTextPayNumYuan;
    @Bind(R.id.checkbox_wechat)
    CheckBox mCheckBoxPayWechat;
    @Bind(R.id.checkbox_alipay)
    CheckBox mCheckBoxAliPay;

    private int mAvatarContainerWidth;
    private int mAvatarContainerHeight;
    private String token ;
    private String selectMoney;
    private String currentMoney;
    private static final int SDK_PAY_FLAG = 1;
    @OnClick(R.id.image_top_back)
    public void back(View view) {
        ChargeMoneyActivity.this.finish();
    }

    @OnClick(R.id.linear_pay_weichat_container)
    public void payWechat(View view) {
        mCheckBoxPayWechat.setChecked(true);
    }

    @OnClick(R.id.linear_alipay_container)
    public void alipay(View view) {
        mCheckBoxAliPay.setChecked(true);
    }

    @OnCheckedChanged(R.id.checkbox_wechat)
    public void weChatPay(CompoundButton compoundButton, boolean b) {
        if (b) {
            mCheckBoxAliPay.setChecked(false);
        }
    }

    @OnCheckedChanged(R.id.checkbox_alipay)
    public void aliPay(CompoundButton compoundButton, boolean b) {
        if (b) {
            mCheckBoxPayWechat.setChecked(false);
        }
    }

//    @OnClick(R.id.btn_charge_gold)
//    public void chargeGold(View view) {
//        token = (String)SharePrefsUtils.get(this,"user","token","");
//        product = "1";
//        String goldNum = mEditChargeGoldNum.getText().toString().trim();
//        if (StringUtils.isEmpty(goldNum)) {
//            toast("请输入充值的金币数量!");
//            return;
//        }
//        if ((!mCheckBoxPayWechat.isChecked()) && (!mCheckBoxAliPay.isChecked())) {
//            toast("请选择支付方式!");
//            return;
//        }
//       final  Button btn = (Button)view;
//        btn.setEnabled(false);
//        btn.setText("支付中.....");
//        //TODO 进行支付操作
//        JSONObject params = new JSONObject();
//        params.put("token",token);
//        params.put("item_id",product);
//        Api.pay(this, params, new OnRequestDataListener() {
//            @Override
//            public void requestSuccess(int code, JSONObject data) {
//                JSONObject payInfo = data.getJSONObject("request");
//                if(mCheckBoxPayWechat.isChecked()){
//                    Wechat wechat = new Wechat(ChargeMoneyActivity.this);
//                    wechat.pay(payInfo.toJSONString());
//                }else{
//                    Alipay alipay = new Alipay(ChargeMoneyActivity.this);
//                    alipay.pay();
//                }
//                btn.setEnabled(false);
//                btn.setText("确认");
//            }
//
//            @Override
//            public void requestFailure(int code, String msg) {
//
//            }
//        });
//
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = (String)SharePrefsUtils.get(this,"user","token","");
//        Bundle data = getIntent().getExtras();
//        if (null != data) {
//            mTextMyGoldNum.setText(data.getString("balance"));
//            currentMoney = data.getString("balance");
//        }
        MyApplication app = (MyApplication) this.getApplication();
        mTextMyGoldNum.setText(app.getBalance());
        currentMoney = app.getBalance();
       // mTextTopTitle.setText("金币充值");
        //mAvatarContainerWidth = DensityUtils.screenWidth(this);
        //mAvatarContainerHeight = (mAvatarContainerWidth * 330) / 750;
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mAvatarContainerWidth, mAvatarContainerHeight);
        //mFrameYuanBaoContainer.setLayoutParams(params);
        //mCheckBoxPayWechat.setChecked(true);
        //mCheckBoxAliPay.setChecked(true); //默认支付宝
//        mEditChargeGoldNum.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s != null && s.length() > 0) {
//                    if (NumUtils.isDigitByMatches(s.toString())) {
//                        mTextPayNum.setText(s);
//                    } else {
//                        //不是整数
//                        toast("请输入有效的整数!");
//                    }
//                } else {
//                    mTextPayNum.setText("0");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        JSONObject param1 = new JSONObject();
        Api.getChargePack(this, param1, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if (isActive){
                    JSONArray list = data.getJSONArray("info");
                    for (int i = 0;i<list.size();i++){
                        View v =  LayoutInflater.from(ChargeMoneyActivity.this).inflate(R.layout.item_charge,null);
                        v.setTag(list.getJSONObject(i).getString("id"));
                        v.setOnClickListener(ChargeMoneyActivity.this);
                        TextView need = (TextView) v.findViewById(R.id.charge_item_num);
                        Button money = (Button) v.findViewById(R.id.charge_item_money);
                        need.setText(list.getJSONObject(i).getString("diamond_num"));
                        money.setText(list.getJSONObject(i).getString("money_num"));
                        mChargeContainer.addView(v);
                     }

                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication app = (MyApplication) this.getApplication();
        mTextMyGoldNum.setText(app.getBalance());
        currentMoney = app.getBalance();
        init();
       // int screenWidth = DensityUtils.screenWidth(this);
       // int baseWidth = DensityUtils.viewWidth(mTextPayNumDesc) + DensityUtils.viewWidth(mTextPayNumYuan);
       // int width = baseWidth + +DensityUtils.dip2px(this, 40);
       // mTextPayNum.setMaxWidth((screenWidth - width));
        //int widthMyGold = width + DensityUtils.dip2px(this, 6) + DensityUtils.viewWidth(mTextMyGoldPrice);
        //mTextMyGoldNum.setMaxWidth((screenWidth - widthMyGold));
    }

    private void init() {
        String token = (String) SharePrefsUtils.get(this, "user", "token", "");
        String userId = (String) SharePrefsUtils.get(this, "user", "userId", "");
        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(userId)) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("token", token);
            requestParams.put("id", userId);
            Api.getUserInfo(this, requestParams, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data) {
                    if (isActive){
                        JSONObject userInfo = data.getJSONObject("data");
                        MyApplication app = (MyApplication) getApplication();
                        app.setBalance(userInfo.getString("balance"));
                        mTextMyGoldNum.setText(app.getBalance());
                        currentMoney = app.getBalance();
                    }
                }

                @Override
                public void requestFailure(int code, String msg) {
                    toast(msg);
                }
            });
        }
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_charge_money;
    }

    @Override
    public void onClick(View view) {
//        TextView zuanshi = (TextView) view.findViewById(R.id.charge_item_num);
//        selectMoney = (String)zuanshi.getText();
//        String id = (String)view.getTag();
//       // SFProgrssDialog dialog = SFProgrssDialog.show(ChargeMoneyActivity.this,"请稍后...");
//        JSONObject params = new JSONObject();
//        params.put("token",token);
//        params.put("item_id",id);




//        if(mCheckBoxPayWechat.isChecked()){
////            params.put("payway","wxpay");
//
//
//        }else if(mCheckBoxAliPay.isChecked()){
////            params.put("payway","alipay");
//        }else{
//            toast("请选择支付方式");
//            return;
//        }


//        Api.wapPay(this, params, new OnRequestDataListener() {
//            @Override
//            public void requestSuccess(int code, JSONObject data) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri uri = Uri.parse(data.getString("code_url"));
//                intent.setData(uri);
//                startActivity(intent);
//            }
//
//            @Override
//            public void requestFailure(int code, String msg) {
//                toast(msg);
//            }
//        });
      //  addWeChat();


        Intent intent = new Intent(ChargeMoneyActivity.this, com.live.xue.own.money.WXPayPicActivity.class);
        startActivity(intent);




    }

    private void addWeChat(){
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText("");
        DialogEnsureUtiles.showConfirm(this, "联系微信充值(微信号已复制到剪贴板)", new OnCustomClickListener() {
            @Override
            public void onClick(String value) {
                Intent intent = new Intent();
                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cmp);
                startActivity(intent);
            }
        });
    }
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    PayResult payResult = new PayResult((String) msg.obj);
//                    /**
//                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
//                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
//                     * docType=1) 建议商户依赖异步通知
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        Toast.makeText(ChargeMoneyActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // 判断resultStatus 为非"9000"则代表可能支付失败
//                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if (TextUtils.equals(resultStatus, "8000")) {
//                            Toast.makeText(ChargeMoneyActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            Toast.makeText(ChargeMoneyActivity.this, "支付失败"+resultInfo, Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        };
//    };
        @SuppressLint("HandlerLeak")
        private Handler mHandler = new Handler() {
            @SuppressWarnings("unused")
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((String) msg.obj);
                        /**
                         * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                         * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                         * docType=1) 建议商户依赖异步通知
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            Toast.makeText(ChargeMoneyActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                            MyApplication app = (MyApplication) ChargeMoneyActivity.this.getApplication();
                            app.setBalance(Integer.parseInt(selectMoney) + Integer.parseInt(currentMoney) + "");
                            mTextMyGoldNum.setText(Integer.parseInt(selectMoney) + Integer.parseInt(currentMoney) + "");
                        } else {
                            // 判断resultStatus 为非"9000"则代表可能支付失败
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                Toast.makeText(ChargeMoneyActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                Toast.makeText(ChargeMoneyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        };
}
