package com.example.cropdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class AboutActivity extends AppCompatActivity {

    public static void  activityStart(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


    }


    public static void MysqlConnection() {
        //初始化驱动String[] args
        Connection c = null;
        try {
            //驱动类com.mysql.jdbc.Driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://47.96.232.152:3306/test?user=root&password=root&useUnicode=true";
            c = DriverManager.getConnection(url);
//            //Statement对象用于执行SQL语句
//            Statement s = c.createStatement();
//            String sql = "insert into login values(999, 'test0','password','password','test@test.com')";
//            s.execute(sql);
//            Log.e("mysql","连接成功，获取连接对象： " + s);
//            System.out.println("连接成功，获取连接对象： " + s);
            //数据库连接是有限资源，相关操作结束后先关s再关c
//            s.close();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
