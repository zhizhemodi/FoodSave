package com.example.foodsave.Activity_Main;

import android.util.Log;

import com.example.foodsave.database.AppDatabase;
import com.example.foodsave.database.Dao.Save_Item_Dao;
import com.example.foodsave.database.Dao.Save_Type_Dao;

public class DeleteThread extends Thread{
    private Save_Item_Dao item_dao;
    private AppDatabase mDb;
    private int item_id;


    public DeleteThread(AppDatabase database, int item_id) {
        mDb = database;
        item_dao = mDb.item_dao();
        this.item_id = item_id;
    }

    @Override
    public void run(){
        item_dao.clearOne(item_id);
    }
}
