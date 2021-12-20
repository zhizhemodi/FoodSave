package com.example.foodsave.Activity_Main;

/*
 * 页面监听接口
 * 用于主Activity与fragment之间的通信
 */
public interface fragmentListener {
    void showTitle(String title);
    void setTextTheme(int text_style);
    boolean getAlarmStatus();
    void setAlarmStatus(boolean status);
}
