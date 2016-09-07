package com.yuyunchao.asus.contacts.activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.yuyunchao.asus.contacts.BaseActivity;
import com.yuyunchao.asus.contacts.R;
import com.yuyunchao.asus.contacts.adapter.MsgLVAdapter;
import com.yuyunchao.asus.contacts.db.MyDataBase;
import com.yuyunchao.asus.contacts.entity.PhoneMsg;

import java.util.ArrayList;

/**
 * 相应电话类型信息界面
 */
public class PhoneMsgActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    ListView lv_msg;
    LinearLayout ll_loading;
    //用于存放电话信息的list
    ArrayList<PhoneMsg> msglist = new ArrayList<PhoneMsg>();
    //自定义的数据库帮助对象
    private MyDataBase myDB;
    //具体信息的适配器
    MsgLVAdapter adapter;

    @Override
    protected int setContent() {
        return R.layout.activity_phone_msg;
    }

    @Override
    protected void initView() {
        lv_msg = (ListView) findViewById(R.id.lv_msg);
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
        //启动初始化异步任务
        new readDataBase().execute();
    }

    /**
     * 装载listview
     */
    private void initList() {
        getMsg();
        //给listView配置适配器
        adapter = new MsgLVAdapter(this, msglist);
    }

    @Override
    protected void setListener() {
        lv_msg.setOnItemClickListener(this);
    }

    /**
     * 得到点击的listview所对应的信息
     */
    private void getMsg() {
        myDB = new MyDataBase(this);
        Intent intent = getIntent();
        //调用selectMsg传入相应的表名并接收相应信息（存放在list中）
        msglist = myDB.selectMsg(intent.getStringExtra("tableName"));
    }


    /**
     * 
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = msglist.get(position).getName();//点击项的电话
        final String number = msglist.get(position).getNumber();//点击项的电话号码
        //创建一个对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");//标题
        builder.setMessage("您将要拨打  " + name + "\n" + number);//对话框信息
        //点击拨打按钮时，拨打相应电话
        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //电话号码
                Uri data = Uri.parse("tel:" + number);
                //拨打相应的电话
                Intent intent = new Intent("android.intent.action.CALL", data);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();//显示对话框



    }

    /**
     * 异步初始化操作任务类
     */
    class readDataBase extends AsyncTask<Void, Void, Void>{
        //任务启动后在异步线程中执行的代码，不可操作UI
        @Override
        protected Void doInBackground(Void... params) {
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
            lv_msg.setAdapter(adapter);
        }
    }


}
