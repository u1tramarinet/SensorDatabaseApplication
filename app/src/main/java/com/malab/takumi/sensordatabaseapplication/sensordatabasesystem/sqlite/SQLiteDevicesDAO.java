package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.table.Devices;

/**
 * Created by takumi on 2017/12/05.
 */

public class SQLiteDevicesDAO {
    private Context context;
    SQLiteDatabase db;
    Devices table_devices;

    public SQLiteDevicesDAO(Context context){
        this.context = context;
    }

    public void insert(){

    }

    public void update(){

    }

    public void getAll(){

    }

    public void getByDeviceId(long deviceId){

    }

    public void getByDeviceUniqueId(String deviceUniqueId){

    }

    public void getByDeviceName(String deviceName){

    }

    public void delete(long deviceId){

    }
}
