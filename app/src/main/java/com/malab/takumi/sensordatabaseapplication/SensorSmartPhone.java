package com.malab.takumi.sensordatabaseapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.SensorDeviceManager;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.SensorListener;

import java.util.List;
import java.util.Locale;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by takumi on 2017/11/30.
 */

public class SensorSmartPhone extends SensorDeviceManager implements SensorEventListener{
    private static final String TAG = "SensorSmartPhone";
    private static final String DEVICE_UNIQUE_ID = "SampleSmartPhone";
    private SensorManager sensorManager;
    private SensorListener sensorListener;
    private List<Sensor> sensors;

    SensorSmartPhone(Context context, SensorListener listener){
        super(context, listener);
        this.context = context;
        this.sensorListener = listener;
    }

    @Override
    public void init() {
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
    }

    @Override
    public void registerListener() {
        for(int i = 0; i < sensors.size() && i != 17; i++){
            sensorManager.registerListener(this, sensors.get(i), SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void unregisterListener() {
        sensorManager.unregisterListener(this);
        Log.d(TAG, "unregistered.");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d(TAG, "onSensorChanged");
        long timestamp = sensorEvent.timestamp;
        int sensorTypeId = sensorEvent.sensor.getType();
        String sensorName = sensorEvent.sensor.getName();
        float[] values = sensorEvent.values;
        Log.d(TAG, String.format("timestamp=%d, sensorTypeId=%d, sensorName=%s, values=%s", timestamp, sensorTypeId, sensorName, arrayToString(values)));
//        sensorListener.upload(timestamp, DEVICE_UNIQUE_ID, sensorTypeId, values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    String arrayToString(float[] values){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < values.length; i++){
            sb.append(String.format(Locale.JAPAN, "{ %d : %f }", i, values[i]));
        }
        sb.append("]");
        return new String(sb);
    }
}
