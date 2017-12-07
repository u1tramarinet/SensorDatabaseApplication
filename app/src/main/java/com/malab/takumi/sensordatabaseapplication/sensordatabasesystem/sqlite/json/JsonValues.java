package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.json;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.SQLiteSensorsDTO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by takumi on 2017/12/02.
 */

public class JsonValues {

    public static String valuesToJson(List<SQLiteSensorsDTO> sensorsDTOS){
        Gson gson = new Gson();
        List<Value> sensorValues = new ArrayList<>();
        for(SQLiteSensorsDTO sensorsDTO: sensorsDTOS) {
            long senId = sensorsDTO.getSensorId();
            float[] values = sensorsDTO.getValues();
            for (int i = 0; i < values.length; i++) {
                long sensorId = format(senId, i);
                sensorValues.add(new Value(sensorId, values[i]));
            }
        }
        return gson.toJson(sensorValues);
    }

    List<SQLiteSensorsDTO> valuesFromJson(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Value>>(){}.getType();
        List<Value> valueList = gson.fromJson(json, type);
        List<SQLiteSensorsDTO> sensorsDTOS = new ArrayList<>();
        SQLiteSensorsDTO sensorsDTO = null;
        for(Value value : valueList){
            sensorsDTO = new SQLiteSensorsDTO();
            sensorsDTO.setSensorId(value.id / 100);
            sensorsDTO.setDeviceId(value.id / 1000);
            sensorsDTO.setParameter(String.format(Locale.JAPAN,"%d : %f", value.id%100, value.sensorValue));
            sensorsDTOS.add(sensorsDTO);
        }
        return sensorsDTOS;
    }

    // XXYY XX:SensorNo, YY:Index
    private static long format(long senId, int index){
        return senId * 100 + index;
    }

    private static class Value {
        @SerializedName("sensor_id")
        private long id;
        @SerializedName("value")
        private float sensorValue;

        Value(long sensorId, float sensorValue){
            this.id = sensorId;
            this.sensorValue = sensorValue;
        }
    }
}
