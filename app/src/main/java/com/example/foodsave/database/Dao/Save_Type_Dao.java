package com.example.foodsave.database.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.foodsave.database.entity.save_Type;

import java.util.List;

@Dao
public interface Save_Type_Dao {
    @Query("SELECT * FROM save_Type")
    public List<save_Type> getAll();

    @Query("SELECT * FROM save_Type WHERE name = :type_name")
    public List<save_Type> getTypeByName(String type_name);
}
