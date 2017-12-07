package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite;

/**
 * Created by takumi on 2017/11/30.
 */

public interface DatabaseListener {
    int TAG_TABLE_DATA = 0;
    int TAG_TABLE_DEVICE = 1;
    int TAG_TABLE_SENSOR = 2;
    int TAG_VALUE_TOTAL = 3;
    int TAG_VALUE_DIFF = 4;

    void onDatabaseContentChanged(long rowCount, String tagTable, String tagValue);

}
