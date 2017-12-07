package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem;

import android.content.Context;

/**
 * Created by takumi on 2017/12/01.
 */

public abstract class SensorDeviceManager implements SensorListener {

    protected Context context;
    private SensorListener sensorListener;

    public SensorDeviceManager(Context context, SensorListener sensorListener){
        this.context = context;
        this.sensorListener = sensorListener;
    }

    public String getStringType(int typeId){
        switch (typeId){
            case(TYPE_ACCELEROMETER):
                return STRING_TYPE_ACCELEROMETER;
            case(TYPE_MAGNETIC_FIELD):
                return STRING_TYPE_MAGNETIC_FIELD;
            case(TYPE_ORIENTATION):
                return STRING_TYPE_ORIENTATION;
            case(TYPE_GYROSCOPE):
                return STRING_TYPE_GYROSCOPE;
            case(TYPE_LIGHT):
                return STRING_TYPE_LIGHT;
            case(TYPE_PRESSURE):
                return STRING_TYPE_PRESSURE;
            case(TYPE_TEMPERATURE):
                return STRING_TYPE_TEMPERATURE;
            case(TYPE_PROXIMITY):
                return STRING_TYPE_PROXIMITY;
            case(TYPE_GRAVITY):
                return STRING_TYPE_GRAVITY;
            case(TYPE_LINEAR_ACCELERATION):
                return STRING_TYPE_LINEAR_ACCELEROMETER;
            default:
                return "UNKNOWN";
        }
    }

    @Override
    public void upload(long timestamp, String deviceUniqueId, long sensorTypeId, float[] values) {
        sensorListener.upload(timestamp, deviceUniqueId, sensorTypeId, values);
    }

    public abstract void init();
    public abstract void registerListener();
    public abstract void unregisterListener();
}
