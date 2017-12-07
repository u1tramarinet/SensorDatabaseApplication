package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.table.Sensors;

import java.util.List;

/**
 * Created by takumi on 2017/12/05.
 */

public class SQLiteSensorsDAO {
    private Context context;
    SQLiteDatabase db;
    Sensors sensors;

    public SQLiteSensorsDAO(Context context){
        this.context = context;
        sensors = new Sensors();
    }

    public void insert(SQLiteSensorsDTO sensorsDTO){

    }

    public void update(SQLiteSensorsDTO sensorsDTO){

    }

    public List<SQLiteSensorsDTO> getAll(){
        return null;
    }

    public SQLiteSensorsDTO getBySensorId(long sensorId){
        return null;
    }

    public List<SQLiteSensorsDTO> getByDeviceId(long dviceId){
        return null;
    }

    public List<SQLiteSensorsDTO> getBySensorTypeId(long sensorTypeId){
        return null;
    }

    public SQLiteSensorsDTO getBySensorName(String sensorName){
        return null;
    }

    public SQLiteSensorsDTO getByDeviceIdAndSensorTypeId(long deviceId, long sensorTypeId){
        return null;
    }

    public void delete(){

    }
}
