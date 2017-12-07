package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem;

/**
 * Created by takumi on 2017/11/29.
 */

public interface SensorType {

    int TYPE_ACCELEROMETER  = 1;
    String STRING_TYPE_ACCELEROMETER = "accelerometer";
    int TYPE_MAGNETIC_FIELD = 2;
    String STRING_TYPE_MAGNETIC_FIELD = "magneticField";
    int TYPE_ORIENTATION = 3;
    String STRING_TYPE_ORIENTATION = "orientation";
    int TYPE_GYROSCOPE = 4;
    String STRING_TYPE_GYROSCOPE = "gyroscope";
    int TYPE_LIGHT = 5;
    String STRING_TYPE_LIGHT = "light";
    int TYPE_PRESSURE = 6;
    String STRING_TYPE_PRESSURE = "pressure";
    int TYPE_TEMPERATURE = 7;
    String STRING_TYPE_TEMPERATURE = "temperature";
    int TYPE_PROXIMITY = 8;
    String STRING_TYPE_PROXIMITY = "proximity";
    int TYPE_GRAVITY = 9;
    String STRING_TYPE_GRAVITY = "gravity";
    int TYPE_LINEAR_ACCELERATION = 10;
    String STRING_TYPE_LINEAR_ACCELEROMETER = "accelerometer";

    public String getStringType(int typeId);
}