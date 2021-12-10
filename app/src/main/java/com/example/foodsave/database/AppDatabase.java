package com.example.foodsave.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodsave.database.Dao.Save_Item_Dao;
import com.example.foodsave.database.Dao.Save_Type_Dao;
import com.example.foodsave.database.RoomConverter;
import com.example.foodsave.database.entity.save_Type;
import com.example.foodsave.database.entity.save_item;

@Database(entities = {save_item.class, save_Type.class},version = 1,exportSchema = false)
@TypeConverters({RoomConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract Save_Item_Dao item_dao();
    public abstract Save_Type_Dao type_dao();
}
