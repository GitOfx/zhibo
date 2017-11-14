package com.live.xue.own.money;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.live.xue.R;
import com.live.xue.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by my on 2017/6/21.
 */

public class WXPayPicActivity extends BaseActivity {

    @Bind(R.id.image_top_back)
    ImageView image_top_back;
    @Bind(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.wx_pay_pic;
    }

    @OnClick(R.id.image_top_back)
    public void Finish(View view) {
        finish();
    }

    Bitmap bm;

    @OnClick(R.id.submit)
    public void Submit(View view) {
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.erweima);


        saveBitmap();
    }

    /**
     * 保存方法
     */
    public void saveBitmap() {
        File f1 = new File(Environment
                .getExternalStorageDirectory().getAbsolutePath() + "/Zhibo/");

        if (!f1.exists()) {
            f1.mkdirs();
        }
        File f = new File(Environment
                .getExternalStorageDirectory().getAbsolutePath() + "/Zhibo/zhibo.jpg");

        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Toast.makeText(WXPayPicActivity.this, "保存成功", Toast.LENGTH_LONG);

    }


}
