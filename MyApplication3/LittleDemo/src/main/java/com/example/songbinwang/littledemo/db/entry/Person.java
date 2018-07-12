package com.example.songbinwang.littledemo.db.entry;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by SongbinWang on 2017/7/31.
 */
@DatabaseTable(tableName = "tb_person") //标明这是数据库中一张表，表名为tb_person
public class Person {

    @DatabaseField(generatedId = true) //对应表tb_person，中id列，id为主键且自动生成
    private int _id;
    @DatabaseField(columnName = "name")//对应列name
    private String name;
    @DatabaseField(columnName = "descri")//对应列descri
    private String descri;

    public Person(){}

    public Person(String name, String descri){
        this.name = name;
        this.descri = descri;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getDescri() {
        return descri;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    @Override
    public String toString() {
        return "Person{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", descri='" + descri + '\'' +
                '}';
    }
}
