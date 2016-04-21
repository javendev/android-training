package com.javen.devicemanagert;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.javen.sms.receiver.SMSActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    // 激活程序
    public void OnActivate(View v) {
        DeviceMethod.getInstance(this).onActivate();
    }

    // 移除程序 如果不移除程序 APP无法被卸载
    public void OnRemoveActivate(View v) {
        DeviceMethod.getInstance(this).onRemoveActivate();
    }

    // 设置解锁方式 不需要激活就可以运行
    public void btnszmm(View v) {
        DeviceMethod.getInstance(this).startLockMethod();
    }

    // 设置解锁方式
    public void btnmm(View v) {
       DeviceMethod.getInstance(this).setLockMethod();
    }

    // 立刻锁屏
    public void btnlock(View v) {
        DeviceMethod.getInstance(this).LockNow();
    }

    // 设置5秒后锁屏
    public void btnlocktime(View v) {
        DeviceMethod.getInstance(this).LockByTime(5000);
    }

    // 恢复出厂设置
    public void btnwipe(View v) {
        DeviceMethod.getInstance(this).WipeData();
    }

    // 设置密码锁
    public void setPassword(View v) {
       DeviceMethod.getInstance(this).setPassword("1234");

    }

    public void startSMSActivity(View view){
        startActivity(new Intent(this, SMSActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
