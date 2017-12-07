package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite;

/**
 * Created by takumi on 2017/12/05.
 */

public class SQLiteDevicesDTO {

    private long deviceId;
    private String deviceUniqueId;
    private String deviceName;

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
     * getter deviceUniqueId
     * @return String deviceUniqueId
     */
    public String getDeviceUniqueId(){
        return deviceUniqueId;
    }

    /**
     * setter deviceUniqueId
     * @param deviceUniqueId String
     */
    public void setDeviceUniqueId(String deviceUniqueId){
        this.deviceUniqueId = deviceUniqueId;
    }

    /**
     * getter deviceName
     * @return String deviceName
     */
    public String getDeviceName(){
        return deviceName;
    }

    /**
     * setter deviceName
     * @param deviceName String
     */
    public void setDeviceName(String deviceName){
        this.deviceName = deviceName;
    }
}
