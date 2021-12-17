package com.example.foodsave.Activity_Main;

import android.content.Context;
import android.util.Log;

import com.example.foodsave.R;
import com.example.foodsave.database.AppDatabase;
import com.example.foodsave.database.Dao.Save_Item_Dao;
import com.example.foodsave.database.Dao.Save_Type_Dao;
import com.example.foodsave.database.entity.save_Type;
import com.example.foodsave.database.entity.save_item;

import java.sql.Date;
import java.util.List;

/*
 * 数据库查询线程
 * 输入数据：
 * @database: 要查询的数据库
 * @type: 按类型查询
 */
public class SearchThread extends Thread{
    private Save_Item_Dao item_dao;
    private Save_Type_Dao type_dao;
    private AppDatabase mDb;
    private List<save_item> item_list;
    private List<save_Type> type_list;
    private List<String> type_name_list;
    private String type_name;
    private String[] status;

    private static final String All = "All_Type";

    public SearchThread(AppDatabase database,String type, String[] status) {
        mDb = database;
        item_dao = mDb.item_dao();
        type_dao = mDb.type_dao();
        type_name = type;
        this.status = status;
    }


    @Override
    public void run(){
        //为了测试需要向数据库添加数据的代码
        // start
        String type_name1 = "药品";
        Date now = new Date(System.currentTimeMillis());
        if (type_dao.getTypeByName(type_name1).size() == 0) {
            save_Type food = new save_Type(type_name1, 1);
            type_dao.insertOne(food);
        }
        save_item bis = new save_item("bis",now,(long)5000,"箱子",1);
        //item_dao.insert(bis);
        // end

        //假如按类型筛选数据值为All，调用查询所有数据的函数
        if (type_name.equals(All)) {
            type_list = type_dao.getAll();
            item_list = item_dao.loadAll();
            type_name_list = type_dao.getAllName();
        }
        //按类型筛选数据筛选
        else{
            type_list = type_dao.getTypeByName(type_name);
            if (type_list != null) item_list = item_dao.loadAllByTypes(type_list.get(0).getId());
        }

        //获取系统时间并计算储物剩余保质期
        Date nTime = new Date(System.currentTimeMillis());
        for (int i = 0;i < item_list.size();i++){
            save_item n = item_list.get(i);
            n.setLeft_time(nTime.getTime() - n.getCreate_Date().getTime());
            n.setStatus(getStatus(n.getLeft_time(),n.getSave_Len()));
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

    /*
     * 函数描述：用于计算储物存储时间占比并返回百分比字段，用于修改item背景颜色
     * 数据描述：
     * @left_time: 储存时间
     * @save_len: 保质期时长
     */
    private String getStatus(Long left_time, Long save_len){
        Double percentage = (left_time * 1.0 / save_len * 1.0) * 100;
        if (percentage <= 25){
            return status[1];
        }
        else if (percentage <= 50) {
            return status[2];
        }
        else if (percentage <= 75) {
            return status[3];
        }
        else if (percentage < 100) {
            return status[4];
        }
        else if (percentage < 105) {
            return status[5];
        }
        else if (percentage < 110) {
            return status[6];
        }
        else
            return status[7];
    }
}
