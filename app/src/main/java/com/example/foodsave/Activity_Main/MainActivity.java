package com.example.foodsave.Activity_Main;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.example.foodsave.Activity_Main.HomeFragment;
import com.example.foodsave.R;

/*
 * 主页面UI线程
 * 用于设置toolbar, homefragment
 * 可能需要添加toolbar中的listener
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置toolbar的显示字
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.main_activity_title);
        setSupportActionBar(toolbar);

        //获取 drawerlayout 实例，并添加监听
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //创建homefragment实例并载入Fragmentlayout
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,homeFragment).commit();
    }
}