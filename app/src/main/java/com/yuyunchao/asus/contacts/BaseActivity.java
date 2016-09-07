package com.yuyunchao.asus.contacts;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * 所有界面的基础界面
 * @author asus
 *
 */
public abstract class BaseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		//去掉标题，全屏显示
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		//布局初始化
		setContentView(setContent());
		initView();
		setListener();
	}

	/**
	 * 设置布局
     */
	protected abstract int setContent();

	/**
	 * 装载控件
	 */
	protected abstract void initView();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();
	
	
}
