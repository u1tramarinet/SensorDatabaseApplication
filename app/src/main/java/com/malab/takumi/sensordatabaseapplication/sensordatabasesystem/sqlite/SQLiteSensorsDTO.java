package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite;

/**
 * Created by takumi on 2017/12/05.
 */

public class SQLiteSensorsDTO {

    private long sensorId;
    private long deviceId;
    private long sensorNo;
    private long sensorTypeId;
    private String sensorName;
    private String parameter;
    private float[] values;

    /**
     * getter sensorId
     * @return String sensorId
     */
    public long getSensorId(){
        return sensorId;
    }

    /**
     * setter sensorId
     * @param sensorId String
     */
    public void setSensorId(long sensorId){
       this.sensorId = sensorId;
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
     * getter sensorNo
     * @return sensorNo long
     */
    public long getSensorNo(){
        return sensorNo;
    }

    /**
     * setter sensorNo
     * @param sensorNo long
     */
    public void setSensorNo(long sensorNo){
        this.sensorNo = sensorNo;
    }

    /**
     * getter sensorTypeId
     * @return sensorTypeId long
     */
    public long getSensorTypeId(){
        return sensorTypeId;
    }

    /**
     * setter sensorTypeId
     * @param sensorTypeId long
     */
    public void setSensorTypeId(long sensorTypeId){
        this.sensorTypeId = sensorTypeId;
    }

    /**
     * getter sensorName
     * @return sensorName String
     */
    public String getSensorName(){
        return sensorName;
    }

    /**
     * setter sensorName
     * @param sensorName String
     */
    public void setSensorName(String sensorName){
        this.sensorName = sensorName;
    }

    /**
     * getter parameter
     * @return paramter String
     */
    public String getParameter(){
        return parameter;
    }

    /**
     * setter parameter
     * @param parameter String
     */
    public void setParameter(String parameter){
        this.parameter = parameter;
    }

    /**
     * getter values
     * @return values float[]
     */
    public float[] getValues(){
        return values;
    }

    /**
     * setter values
     * @param values float[]
     */
    public void setValues(float[] values){
        this.values = values;
    }
}
