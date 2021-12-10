package com.example.foodsave.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

/*
 *
 * 类描述：该类主要用于记录储物信息
 * 数据描述：
 *
 */
@Entity
public class save_item {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "create_Date")
    private Date create_Date;

    @ColumnInfo(name = "save_Len")
    private Long save_Len;

    @ColumnInfo(name = "save_Place")
    private String save_Place;

    @ColumnInfo(name = "type_Id")
    private Integer type_Id;

    public save_item(String name, Date create_Date, Long save_Len, String save_Place, Integer type_Id){
        this.name = name;
        this.create_Date = create_Date;
        this.save_Len = save_Len;
        this.save_Place = save_Place;
        this.type_Id = type_Id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }

    public Long getSave_Len() {
        return save_Len;
    }

    public void setSave_Len(Long save_Len) {
        this.save_Len = save_Len;
    }

    public String getSave_Place() {
        return save_Place;
    }

    public void setSave_Place(String save_Place) {
        this.save_Place = save_Place;
    }

    public Integer getType_Id() {
        return type_Id;
    }

    public void setType_Id(Integer type_Id) {
        this.type_Id = type_Id;
    }
}
