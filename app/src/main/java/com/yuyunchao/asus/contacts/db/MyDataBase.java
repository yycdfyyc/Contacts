package com.yuyunchao.asus.contacts.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yuyunchao.asus.contacts.entity.PhoneMsg;
import com.yuyunchao.asus.contacts.entity.PhoneType;

import java.util.ArrayList;

/**
 * Created by asus on 2016/8/27.
 */
public class MyDataBase extends SQLiteOpenHelper {
    private static String DB_NAME = "commonnum.db";//数据库名
    private static String TABLE_NAME_CLASSLIST = "classlist";//电话类型的表名
    private SQLiteDatabase myDataBase;
    private Cursor myCursor;
    //用于存放电话类型的list
    private ArrayList<PhoneType> classlist = new ArrayList<>();
    //用于存放相应的电话信息的list
    private ArrayList<PhoneMsg> msglist = new ArrayList<>();

    public MyDataBase(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * 查询classlist表的方法
     * @return 返回存放信息的list
     */
    public ArrayList<PhoneType> selectClass(){
        myDataBase = this.getReadableDatabase();
        myCursor = myDataBase.rawQuery("select * from "+TABLE_NAME_CLASSLIST,  null);
        classlist.add(new PhoneType("本地电话"));
        while(myCursor.moveToNext()){
            PhoneType type = new PhoneType();
            type.setPhoneType(myCursor.getString(myCursor.getColumnIndexOrThrow("name")));
            classlist.add(type);
        }
        myDataBase.close();
        return classlist;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 查询相应表名的表的信息
     * @param tableName 要查询的表名
     * @return 返回存放信息的list
     */
    public ArrayList<PhoneMsg> selectMsg(String tableName){
        myDataBase = this.getReadableDatabase();
        myCursor = myDataBase.rawQuery("select * from "+tableName,  null);
        while(myCursor.moveToNext()){
            PhoneMsg msg = new PhoneMsg();
            msg.setName(myCursor.getString(2));
            msg.setNumber(myCursor.getString(1));
            msglist.add(msg);
        }
        myDataBase.close();
        return msglist;
    }
}
