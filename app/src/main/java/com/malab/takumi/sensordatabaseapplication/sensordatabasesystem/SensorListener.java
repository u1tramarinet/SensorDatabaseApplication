package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem;

/**
 * Created by takumi on 2017/11/28.
 */

public interface SensorListener extends SensorType {
    void upload(long timestamp, String deviceUniqueId, long sensorTypeId, float[] values);
}