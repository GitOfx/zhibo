package com.yiren.live.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yiren.live.R;


/**
 * 自定义的对话框
 */
public abstract class MyAlerDialogKaiJiang extends AlertDialog implements View.OnClickListener {
	Context context;

	protected MyAlerDialogKaiJiang(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/**
	 * 布局中的其中一个组件
	 */
	private TextView cancel;
	private TextView confirm;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 加载自定义布局
		setContentView(R.layout.dialog_name);
		// setDialogSize(300, 200);
		cancel = (TextView) findViewById(R.id.cancel);
		confirm = (TextView) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	/**
	 * 修改 框体大小
	 * 
	 * @param width
	 * @param height
	 */
	public void setDialogSize(int width, int height) {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = 350;
		params.height = 200;
		this.getWindow().setAttributes(params);
	}

	public abstract void clickCancelBack(MyAlerDialogKaiJiang log);
	public abstract void clickConfirmBack(MyAlerDialogKaiJiang log);

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == cancel) {
			clickCancelBack(this);
		}
		if (v == confirm) {
			clickConfirmBack(this);
		}
	}

}