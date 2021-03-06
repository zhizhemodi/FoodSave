package com.example.foodsave.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodsave.database.entity.save_item;

import java.util.List;

@Dao
public interface Save_Item_Dao {
    @Query("SELECT * FROM save_item")
    List<save_item> loadAll();

    @Query("SELECT * FROM save_item WHERE type_Id = :typeId")
    List<save_item> loadAllByTypes(Integer typeId);

    @Insert
    void insertAll(save_item... save_items);

    @Insert
    void insert(save_item a_save_item);

    @Query("DELETE FROM save_item")
    void clearAll();

    @Query("DELETE FROM save_item WHERE id = :select_id")
    void clearOne(Integer select_id);
}
