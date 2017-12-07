package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.table.Data;

/**
 * Created by takumi on 2017/12/05.
 */

public class SQLiteDataDAO {
    private Context context;
    SQLiteDatabase db;
    Data table_data;

    public SQLiteDataDAO(Context context){
        this.context = context;
    }

    public void createTable(){

    }

    public void updateTable(){

    }

    public void deleteTable(){

    }

    public void insert(){

    }

    public void update(){

    }

    public void getAll(){

    }

    public void getByTimestamp(long time){

    }

    public void getByTimestamp(long start, long end){

    }

    public void getByDeviceId(long deviceId){

    }

    public void delete(long id){

    }
}
