package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite;

/**
 * Created by takumi on 2017/12/05.
 */

public class SQLiteDataDTO {

    private long timestamp;
    private long deviceId;
    private String data;

    /**
     * getter timestamp
     * @return long timestamp
     */
    public long getTimestamp(){
        return timestamp;
    }

    /**
     * setter timestamp
     * @param timestamp long
     */
    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    /**
     * getter deviceId
     * @return long deviceId
     */
    public long getDeviceId(){
        return deviceId;
    }

    /**
     * setter deviceId
     * @param deviceId long
     */
    public void setDeviceId(long deviceId){
        this.deviceId = deviceId;
    }

    /**
     * getter data
     * @return String data
     */
    public String getData(){
        return data;
    }

    /**
     * setter data
     * @param data String
     */
    public void setData(String data){
        this.data = data;
    }
}
