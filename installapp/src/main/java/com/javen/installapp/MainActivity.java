package com.javen.installapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    public void onInstall(View view){
        new Thread(){
            public void run() {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/appk/app-release.apk";
                System.out.println("path>>>>====="+path);
                if (ApkController.install(path, getApplicationContext())){
                    toast("安装成功");
                }else{
                    toast("安裝失败");
                }
            };
        }.start();
    }

    /**
     * 卸载
     */
    public void onUninstall(View view){
        new Thread(){
            public void run() {
                if (ApkController.uninstall("com.javen.recyclerview", getApplicationContext())){
                    toast("卸载成功");
                }else{
                    toast("卸载失败");
                }
            };
        }.start();
    }
    /**
     * 描述: 启动
     */
    public void onStartApp(View view){
        if (ApkController.startApp("com.javen.recyclerview","com.javen.recyclerview.MainActivity")) {
            toast("启动成功");
        }else {
            toast("启动失败");
        }
    }
    public void toast(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
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
