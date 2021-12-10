package com.example.foodsave.Activity_Main;

import android.util.Log;

import com.example.foodsave.database.AppDatabase;
import com.example.foodsave.database.Dao.Save_Item_Dao;
import com.example.foodsave.database.Dao.Save_Type_Dao;
import com.example.foodsave.database.entity.save_Type;
import com.example.foodsave.database.entity.save_item;

import java.sql.Date;
import java.util.List;

public class SearchThread extends Thread{
    private Save_Item_Dao item_dao;
    private Save_Type_Dao type_dao;
    private AppDatabase mDb;
    private List<save_item> item_list;
    private List<save_Type> type_list;
    private List<String> type_name_list;
    private String type_name;

    private static final String All = "All_Type";

    public SearchThread(AppDatabase database,String type) {
        mDb = database;
        item_dao = mDb.item_dao();
        type_dao = mDb.type_dao();
        type_name = type;
    }


    @Override
    public void run(){
        String type_name1 = "food";
        Date now = new Date(System.currentTimeMillis());
        Log.i("hello",String.valueOf(type_dao.getTypeByName(type_name1).get(0).getId()));
        Log.i("hello",String.valueOf(type_dao.getAll().size()));
        if (type_dao.getTypeByName(type_name1) == null) {
            save_Type food = new save_Type(type_name1, 1);
            type_dao.insertOne(food);
        }
        save_item biscuit = new save_item("biscuit", now, (long)1000 , "箱子", type_dao.getTypeByName(type_name1).get(0).getId());
        item_dao.insert(biscuit);
        if (type_name.equals(All)) {
            type_list = type_dao.getAll();
            item_list = item_dao.loadAll();
            type_name_list = type_dao.getAllName();
        }
        else{
            type_list = type_dao.getTypeByName(type_name);
            if (type_list != null) item_list = item_dao.loadAllByTypes(type_list.get(0).getId());
        }
    }

    public List<save_item> getItem_list() {
        return item_list;
    }

    public void setItem_list(List<save_item> item_list) {
        this.item_list = item_list;
    }

    public List<save_Type> getType_list() {
        return type_list;
    }

    public void setType_list(List<save_Type> type_list) {
        this.type_list = type_list;
    }

    public List<String> getType_name_list(){return type_name_list;}
}
