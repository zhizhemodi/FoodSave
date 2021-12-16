package com.example.foodsave.Activity_Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.foodsave.Other.about_us;
import com.example.foodsave.R;
import com.google.android.material.navigation.NavigationView;

/*
 * 主页面UI线程
 * 用于设置toolbar, HomeFragment
 * 可能需要添加toolbar中的listener
 */
public class MainActivity extends AppCompatActivity implements fragmentListener {

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,homeFragment).commit();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_about){
                    about_us about_usFragment = new about_us();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content,about_usFragment).commit();
                }
                return true;
            }
        });
    }

    @Override
    public void showTitle(String title) {
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle(title);
    }
}