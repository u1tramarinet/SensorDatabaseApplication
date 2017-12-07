package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem;

import com.google.gson.annotations.SerializedName;

/**
 * Created by takumi on 2017/12/01.
 */

public class Data {
    @SerializedName("timestamp")
    private long timestamp;
    @SerializedName("device_id")
    private long deviceId;

}


