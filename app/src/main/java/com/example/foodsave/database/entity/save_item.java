package com.example.foodsave.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

/*
 * 类描述：该类主要用于记录储物信息
 * 数据描述：
 * @id: 唯一编号，自动生成
 * @name: 储物名称，可重复
 * @create_Date: 生产日期或储存日期
 * @save_Len: 保质期
 * @save_Place: 存放地点
 * @type_Id: 储物类型编号
 * @left_time: 剩余保质期，不存储于数据库
 */
@Entity
public class save_item implements Comparable{
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

    @Ignore
    private Long left_time;

    @Ignore
    private String status;

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

    public Long getLeft_time() {
        return left_time;
    }

    public void setLeft_time(Long left_time) {
        this.left_time = left_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Object o) {
        save_item ot = (save_item) o;
        int re = (int) ((this.getLeft_time() / this.getSave_Len()) - (ot.getLeft_time() / ot.getSave_Len()));
        return re;
    }
}
