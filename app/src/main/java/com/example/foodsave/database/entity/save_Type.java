package com.example.foodsave.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* 储物类型类
 * 数据描述：
 * @id: 类型编号，唯一且自动生成
 * @name: 类型名称
 * @pic_num: 该类型所对应的图片
 */
@Entity
public class save_Type {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "pic_num")
    private Integer pic_num;

    public save_Type(String name, Integer pic_num) {
        this.name = name;
        this.pic_num = pic_num;
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

    public Integer getPic_num() {
        return pic_num;
    }

    public void setPic_num(Integer pic_num) {
        this.pic_num = pic_num;
    }
}
