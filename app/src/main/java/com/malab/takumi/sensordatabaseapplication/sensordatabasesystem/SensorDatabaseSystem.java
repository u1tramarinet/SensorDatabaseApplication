package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem;

import android.content.Context;
import android.util.Log;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.cloud.Cloud;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.fog.Fog;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.SQLiteDB;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.SQLiteSensorsDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takumi on 2017/11/30.
 */

public class SensorDatabaseSystem implements SensorListener  {
    private static final String TAG = "SensorDatabaseSystem";
    private SQLiteDB sqLiteDB;
    private Fog fog;
    private Cloud cloud;
    private static long uploadTime = 0L;
    private static String nowHandlingDevice;
    private static List<SQLiteSensorsDTO> sensorsDTOS = new ArrayList<>();


    public SensorDatabaseSystem(Context context){
        sqLiteDB = new SQLiteDB(context);
        fog = new Fog(context);
        cloud = new Cloud(context);
    }

    public void initDevices(String devUniqueId, String devName){
        sqLiteDB.insertDevice(devUniqueId, devName);
    }

    public void initSensors(long lDevId, long lSenTypeId, String sSenName, int nParams){
        sqLiteDB.insertSensor(lDevId, lSenTypeId, sSenName, nParams);
    }

    public void initSensors(long lDevId, long lSenTypeId, String sSenName, String[] names){
        sqLiteDB.insertSensor(lDevId, lSenTypeId, sSenName, names);
    }

    public void upload(long timestamp, String deviceUniqueId, long sensorTypeId, float[] values) {
        Log.d(TAG, "start to upload");
        if(timestamp != 0L && ( uploadTime != timestamp || nowHandlingDevice.equals(deviceUniqueId))){
            Log.d(TAG, "uploading");
            sqLiteDB.insertData(timestamp, deviceUniqueId, sensorsDTOS);
            sensorsDTOS = new ArrayList<>();
            uploadTime = timestamp;
            nowHandlingDevice = deviceUniqueId;
        }else{
            Log.d(TAG, "store to temp list");
            SQLiteSensorsDTO sensorsDTO = new SQLiteSensorsDTO();
            sensorsDTO.setSensorTypeId(sensorTypeId);
            sensorsDTO.setValues(values);
        }

    }

    @Override
    public String getStringType(int typeId) {
        return null;
    }
}