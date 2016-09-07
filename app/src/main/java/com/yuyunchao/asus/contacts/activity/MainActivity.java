package com.yuyunchao.asus.contacts.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.yuyunchao.asus.contacts.BaseActivity;
import com.yuyunchao.asus.contacts.R;
import com.yuyunchao.asus.contacts.adapter.TypeLVAdapter;
import com.yuyunchao.asus.contacts.db.MyDataBase;
import com.yuyunchao.asus.contacts.entity.PhoneType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *主界面
 */public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener{
	private ListView lv_main;//布局中的listView
	private LinearLayout ll_loading;//loading的布局
	private boolean is_exit = false; //点击退出键时的布尔值
	private long l_firstTime;//第一次点击的时间
	private long l_secondTime;//第二次点击的时间
	private MyDataBase myDB;//SQLiteOpenHelper的实例
	//要存放数据库的路径
	private static String DB_PATH = "/data/data/com.yuyunchao.asus.contacts/databases/";
	//数据库名
	private static String DB_NAME = "commonnum.db";
	//主界面的电话类型listView的适配器
	TypeLVAdapter adapter;
	//声明一个接受PhoneType的集合
	ArrayList<PhoneType> phoneTypes = new ArrayList<>();

	@Override
	protected int setContent() {
		return R.layout.activity_main;
	}

	/**
	 * 装载控件
	 */
	@Override
	protected void initView() {
		lv_main = (ListView) findViewById(R.id.lv_main);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		requestPermission();
		//启动初始化异步任务
		new importDataTask().execute();
	}

	/**
	 * 装载lsitview
	 */
	private void initList(){
		//通过构造函数实例化
		myDB = new MyDataBase(this);
		//调用相应的方法，往集合中传入数据
		phoneTypes = myDB.selectClass();
		adapter = new TypeLVAdapter(this,phoneTypes);

	}
	@Override
	protected void setListener() {
		lv_main.setOnItemClickListener(this);
	}

	/**
	 * 按键点击的方法
	 * @param keyCode
	 * @param event
     * @return
     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		doubleClickExit(keyCode,event);
		return true;
	}

	/**
	 * 双击退出按钮退出
	 * @param keyCode
	 * @param event
     */
	private void doubleClickExit(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK && !is_exit){
			//第一次点击时的时间戳
			l_firstTime = System.currentTimeMillis();
			//重置退出为true
			is_exit=true;
			//弹窗提示
			Toast.makeText(this, getString(R.string.exit),Toast.LENGTH_SHORT).show();
		}else if(keyCode == KeyEvent.KEYCODE_BACK && is_exit){
			//第二次点击时的时间戳
			l_secondTime = System.currentTimeMillis();

			if(l_secondTime - l_firstTime <2000){
				//如果两次点击时间不超过2s则退出
				finish();
			}else{
				//如果两次点击时间超过2s则重置退出为false
				is_exit=false;
				//并回调自身
				doubleClickExit(keyCode,event);
			}
		}
	}

	/**
	 * 点击电话类型列表时的监听
	 * @param parent
	 * @param view
	 * @param position item的序号 从0开始
     * @param id
     */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent;
		String type = phoneTypes.get(position).getPhoneType();
		if(type.equals("本地电话")){
			//当点击本地电话时 启动本地拨号界面
			intent = new Intent(Intent.ACTION_DIAL);
			startActivity(intent);
		}else{
			//当点击其他项时
			 intent = new Intent(this, PhoneMsgActivity.class);
			//把点击的item的position（从1开始）组合成一个表名并传到下个界面
			intent.putExtra("tableName","table" + position );
			startActivity(intent);
		}
	}
	/**
	 * 导入数据库
	 */
	private void importDataBase() throws IOException {
		File path = new File(DB_PATH);
		File outFile = new File(DB_PATH +DB_NAME);
		if(!path.exists()){
			path.mkdirs();
		}
		if(!outFile.exists()){
			outFile.createNewFile();
		}else {
			return;
		}
		InputStream myInput = this.getAssets().open(DB_NAME);
		OutputStream myOutput = new FileOutputStream(outFile);
		byte[] buffer = new byte[1024];
		int length=0;
		while ((length = myInput.read(buffer)) != -1){
			myOutput.write(buffer,0,length);
		}
		myInput.close();
		myOutput.flush();
		myOutput.close();


	}
	/**
	 * 异步初始化操作任务类
	 */
	class importDataTask extends AsyncTask<Void, Void, Void>{
		//任务启动后在异步线程中执行的代码，不可操作UI
		@Override
		protected Void doInBackground(Void... params) {
			//转移数据库文件
			try {
				importDataBase();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//装载ListView
			initList();
			return null;
		}


		//任务启动之前执行的代码，可操作UI
		@Override
		protected void onPreExecute() {
			//让loading界面显示
			ll_loading.setVisibility(View.VISIBLE);
		}

		//任务完成之后执行的代码，可操作UI
		@Override
		protected void onPostExecute(Void aVoid) {
			//隐藏loading界面
			ll_loading.setVisibility(View.GONE);
			//给列表设置适配器
			lv_main.setAdapter(adapter);
		}
	}
	/**
	 * 动态申请权限
	 */
	private void requestPermission(){
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!=
				PackageManager.PERMISSION_GRANTED){
			//申请CALL_PHONE权限
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.CALL_PHONE},
					0);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//判断申请码
		switch (requestCode){
			case 0:
				if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
					//申请的第一个权限成功后
					Toast.makeText(this,"申请权限成功",Toast.LENGTH_SHORT).show();
				}else{
					//申请的第一个权限失败后
					finish();
				}
				break;
		}
	}
}
