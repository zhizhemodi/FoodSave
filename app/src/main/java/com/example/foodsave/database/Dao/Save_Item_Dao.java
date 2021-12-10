package com.example.foodsave.database.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.foodsave.database.entity.save_item;

import java.util.List;

@Dao
public interface Save_Item_Dao {
    @Query("SELECT * FROM save_item")
    List<save_item> loadAll();

    @Query("SELECT * FROM save_item WHERE type_Id = :typeId")
    List<save_item> loadAllByTypes(Integer typeId);
}
