package com.example.foodsave.Activity_Main;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.foodsave.Other.SettingFragment;
import com.example.foodsave.Other.about_us;
import com.example.foodsave.R;
import com.example.foodsave.database.AppDatabase;
import com.example.foodsave.database.entity.save_item;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/*
 * 主页面UI线程
 * 用于设置toolbar, HomeFragment
 * 可能需要添加toolbar中的listener
 */
public class MainActivity extends AppCompatActivity implements fragmentListener {

    private NavigationView navigationView;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private HomeFragment homeFragment;
    private static final String EveryDay_notice = "android.intent.action.alarm.timer"; //每日提醒广播名称



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取SharedPreferences
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

        //获取存储的字体样式，若没有则默认为Middle_textSize
        int text_style = pref.getInt("text_style", R.style.Middle_textSize);
        this.setTheme(text_style);

        //设置toolbar的显示字
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.main_activity_title);
        setSupportActionBar(toolbar);

        //获取 DrawerLayout 实例，并添加监听
        DrawerLayout drawer = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //创建HomeFragment实例并载入FragmentLayout
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,homeFragment).commit();

        //获取navigationView实例并设置选项监听
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //创建关于我们页面
                if (item.getItemId() == R.id.nav_about){
                    about_us about_usFragment = new about_us();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content,about_usFragment).commit();
                }
                //创建设置界面
                if (item.getItemId() == R.id.nav_set){
                    SettingFragment setting_fragment = new SettingFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content,setting_fragment).commit();
                }
                if (item.getItemId() == R.id.update_to_premium){
                    //TODO 网络升级功能
                }
                if (item.getItemId() == R.id.delete_sql){
                    //TODO 删除数据库所有信息
                }
                return true;
            }
        });
    }

    //选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    //选项菜单监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_add){
            //TODO 进入添加储物界面
            Toast.makeText(this,"进入添加界面",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_delete){
            //TODO 删除选中储物
            AppDatabase database = Room.databaseBuilder(Objects.requireNonNull(this).getApplicationContext(),
                    AppDatabase.class,getResources().getString(R.string.Database_Name)).build();
            DeleteThread deleteThread = new DeleteThread(database,homeFragment.getSelect_now());
            deleteThread.start();
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content,homeFragment).commit();
            Toast.makeText(this,"删除储物",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    /*
     * toolbar标题设置方法
     * @title: 当前页面标题名
     */
    @Override
    public void showTitle(String title) {
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle(title);
    }

    /*
     * 字体大小设置方法
     * @text_style: 字体样式
     * 通过setTheme设置字体样式
     * 通过editor存储设置的字体样式
     */
    @Override
    public void setTextTheme(int text_style) {
        this.setTheme(text_style);
        editor.putInt("text_style",text_style).commit();
    }

    @Override
    public boolean getAlarmStatus() {
        return pref.getBoolean("alarm_set", false);
    }

    @Override
    public void setAlarmStatus(boolean status) {
        editor.putBoolean("alarm_set", status).commit();
        if (status == false){
            Toast.makeText(this,getString(R.string.stop_alarm),Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,getString(R.string.start_alarm),Toast.LENGTH_SHORT).show();
            PeriodicWorkRequest everyday = new PeriodicWorkRequest.Builder(EveryDayWork.class, 24, TimeUnit.HOURS).build();
            WorkManager.getInstance().enqueue(everyday);
        }
    }

}