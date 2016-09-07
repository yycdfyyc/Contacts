package com.yuyunchao.asus.contacts.activity;

import com.yuyunchao.asus.contacts.BaseActivity;
import com.yuyunchao.asus.contacts.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.content.Intent;

/**
 * 开屏动画界面
 */
public class LogoActivity extends BaseActivity {
	private ImageView iv_androidy;

    /**
	 * 装载控件
	 */
    @Override
    protected void initView(){
    	iv_androidy = (ImageView) findViewById(R.id.iv_androidy);
    }

	/**
	 * 装载动画
	 */
    private void initAnim(){
		//通过XML方式加载动画
		Animation anim= AnimationUtils.loadAnimation(this, R.anim.loge_anim);
		//设置动画结束时的监听 使其跳转到主界面
		anim.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}
			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(LogoActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		iv_androidy.startAnimation(anim);
    }
	@Override
	protected int setContent() {
		return R.layout.activity_logo;
	}
	@Override
	protected void setListener() {
		
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		//当界面获得焦点时，启动动画
		if(hasFocus){
			initAnim();
		}
		
	}

}





