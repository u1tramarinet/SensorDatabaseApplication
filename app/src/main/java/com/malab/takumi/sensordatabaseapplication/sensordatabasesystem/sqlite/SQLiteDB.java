package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.json.JsonParameters;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.json.JsonValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takumi on 2017/11/22.
 */

public class SQLiteDB {
    private static final String TAG = "SQLiteDB";
    private static SQLiteDatabase db;
    private SQLiteDBHelper sqLiteHelper;
    private Context activity;

    public Data data;
    public Devices devices;
    public Sensors sensors;

    public SQLiteDB(Context context){
        this.activity = context;
        sqLiteHelper = new SQLiteDBHelper(this.activity);
    }

    private void open(){
        db =  sqLiteHelper.open(activity);
    }

    public long insertData(long timestamp, String devUniqueId, List<SQLiteSensorsDTO> sensorsDTOS){
        long deviceID = devices.getByDeviceUniqueId(devUniqueId).getDeviceId();
        for(SQLiteSensorsDTO sensorsDTO : sensorsDTOS){
            long senTypeId = sensorsDTO.getSensorTypeId();
            long sensorId = sensors.getBySensorId(deviceID, senTypeId).getSensorId();
            sensorsDTO.setSensorId(sensorId);
        }
        String dataJson = JsonValues.valuesToJson(sensorsDTOS);
        open();
        return data.insert(timestamp, deviceID, dataJson);
    }

    public long insertDevice(String sDevUniqueId, String sDevName){
        return devices.insert(sDevUniqueId, sDevName);
    }

    public long insertSensor(long lDevId, long lSenTypeId, String sSenName, String sParams){
        return sensors.insert(lDevId, lSenTypeId, sSenName, sParams);
    }

    public long insertSensor(long lDevId, long lSenTypeId, String sSenName, int nParams){
        return sensors.insert(lDevId, lSenTypeId, sSenName, nParams);
    }

    public long insertSensor(long lDevId, long lSenTypeId, String sSenName, String[] names){
        return sensors.insert(lDevId, lSenTypeId, sSenName, names);
    }

    class SQLiteDBHelper extends SQLiteOpenHelper {
        private static final String TAG = "SQLiteDBHelper";
        private static final String DATABASE_NAME = "SensorData.db";
        private static final int DATABASE_VERSION = 2;
        private SQLiteDBHelper sqLiteDBHelper = null;

        SQLiteDBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            data.createTable(db);
            devices.createTable(db);
            sensors.createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            data.deleteTable(db);
            devices.deleteTable(db);
            sensors.deleteTable(db);
            onCreate(db);
        }

        synchronized SQLiteDBHelper getDBHelper(Context context){
            if(sqLiteDBHelper == null){
                sqLiteDBHelper = new SQLiteDBHelper(context);
            }
            return sqLiteDBHelper;
        }

        synchronized SQLiteDatabase open(Context context){
            sqLiteDBHelper.getDBHelper(context);
            return sqLiteDBHelper.getWritableDatabase();
        }
    }

    abstract class Table {
        static final String TEXT_TYPE = " TEXT";
        static final String INTEGER_TYPE = " INTEGER";
        static final String REAL_TYPE = " REAL";
        static final String COMMA_SEP = ",";
        static final String SQL_SELECT_ALL = "SELECT * FROM ";
        static final String SQL_WHERE = " WHERE ";
        static final String SQL_AND = " AND ";
    }

    private class Data extends Table implements BaseColumns {
        private static final String TAG = "Table/DataDAO";
        private static final String TABLE_NAME = "table_data";
        private static final String COLUMN_TIMESTAMP = "timestamp";
        private static final String COLUMN_DEVICE_ID = "device_id";
        private static final String COLUMN_DATA = "data";

        private static final String SQL_CREATE_DATA =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                        COLUMN_TIMESTAMP + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_DEVICE_ID + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_DATA + TEXT_TYPE + " )";

        private static final String SQL_DELETE_DATA =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        public Data() {
        }

        public void createTable(SQLiteDatabase sqldb) {
            sqldb.execSQL(SQL_CREATE_DATA);
        }

        public void deleteTable(SQLiteDatabase sqldb) {
            sqldb.execSQL(SQL_DELETE_DATA);
        }

        public long insert(long timestamp, long deviceId, String data) {
            ContentValues row = new ContentValues();
            row.put(COLUMN_TIMESTAMP, timestamp);
            row.put(COLUMN_DEVICE_ID, deviceId);
            row.put(COLUMN_DATA, data);

            return db.insert(TABLE_NAME, null, row);
        }

        public List<SQLiteDataDTO> getAll() {
            Cursor cursor = null;
            SQLiteDataDTO dataDTO = null;
            String sql = SQL_SELECT_ALL + TABLE_NAME;
            cursor = db.rawQuery(sql, null);
            List<SQLiteDataDTO> dataDTOS = new ArrayList<>();
            while (cursor.moveToNext()) {
                dataDTO = new SQLiteDataDTO();
                dataDTO.setTimestamp(cursor.getLong(1));
                dataDTO.setDeviceId(cursor.getLong(2));
                dataDTO.setData(cursor.getString(3));
                dataDTOS.add(dataDTO);
            }
            cursor.close();
            return dataDTOS;
        }

        public long getCount(){
            return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        }

        private final String SQL_WHERE_TIMESTAMP = COLUMN_TIMESTAMP + " = ?";
        public List<SQLiteDataDTO> getByTimestamp(long timestamp) {
            Cursor cursor = null;
            SQLiteDataDTO dataDTO = null;
            String sql = SQL_SELECT_ALL + TABLE_NAME + SQL_WHERE + SQL_WHERE_TIMESTAMP;
            cursor = db.rawQuery(sql, new String[]{String.valueOf(timestamp)});
            List<SQLiteDataDTO> dataDTOS = new ArrayList<>();
            while (cursor.moveToNext()) {
                dataDTO = new SQLiteDataDTO();
                dataDTO.setTimestamp(cursor.getLong(1));
                dataDTO.setDeviceId(cursor.getLong(2));
                dataDTO.setData(cursor.getString(3));
                dataDTOS.add(dataDTO);
            }
            cursor.close();
            return dataDTOS;
        }

        public long getCountByTimestamp(long timestamp){
            return  DatabaseUtils.queryNumEntries(db, TABLE_NAME,
                    SQL_WHERE_TIMESTAMP, new String[]{String.valueOf(timestamp)});
        }

        private final String SQL_WHERE_TIMESTAMP_BETWEEN = COLUMN_TIMESTAMP + " BETWEEN ? AND ?";
        public List<SQLiteDataDTO> getByTimestamp(long start, long end) {
            Cursor cursor = null;
            SQLiteDataDTO dataDTO = null;
            String sql = SQL_SELECT_ALL + TABLE_NAME + SQL_WHERE + SQL_WHERE_TIMESTAMP_BETWEEN;
            cursor = db.rawQuery(sql, new String[]{String.valueOf(start), String.valueOf(end)});
            List<SQLiteDataDTO> dataDTOS = new ArrayList<>();
            while (cursor.moveToNext()) {
                dataDTO = new SQLiteDataDTO();
                dataDTO.setTimestamp(cursor.getLong(1));
                dataDTO.setDeviceId(cursor.getLong(2));
                dataDTO.setData(cursor.getString(3));
                dataDTOS.add(dataDTO);
            }
            cursor.close();
            return dataDTOS;
        }

        public long getCountByTimestamp(long start, long end){
            return  DatabaseUtils.queryNumEntries(db, TABLE_NAME, SQL_WHERE_TIMESTAMP_BETWEEN,
                    new String[]{String.valueOf(start), String.valueOf(end)});
        }

        private final String SQL_WHERE_DEVICE_ID = COLUMN_DEVICE_ID + " = ?";
        public List<SQLiteDataDTO> getByDeviceId(long deviceId) {
            Cursor cursor = null;
            SQLiteDataDTO dataDTO = null;
            String sql = SQL_SELECT_ALL + TABLE_NAME + SQL_WHERE + SQL_WHERE_DEVICE_ID;
            cursor = db.rawQuery(sql, new String[]{String.valueOf(deviceId)});
            List<SQLiteDataDTO> dataDTOS = new ArrayList<>();
            while (cursor.moveToNext()) {
                dataDTO = new SQLiteDataDTO();
                dataDTO.setTimestamp(cursor.getLong(1));
                dataDTO.setDeviceId(cursor.getLong(2));
                dataDTO.setData(cursor.getString(3));
                dataDTOS.add(dataDTO);
            }
            cursor.close();
            return dataDTOS;
        }

        public long getCountByDeviceId(long deviceId){
            return  DatabaseUtils.queryNumEntries(db, TABLE_NAME, SQL_WHERE_DEVICE_ID,
                    new String[]{String.valueOf(deviceId)});
        }

        public int deleteByTimestamp(long timestamp) {
            return db.delete(TABLE_NAME, COLUMN_TIMESTAMP + " = ? ", new String[]{String.valueOf(timestamp)});
        }

        public int deleteByTimestamp(long start, long end) {
            String sql = COLUMN_TIMESTAMP + " BETWEEN " + start + " AND " + end;
            return db.delete(TABLE_NAME, sql, new String[]{String.valueOf(start), String.valueOf(end)});
        }

        public int deleteByDeviceId(long deviceId) {
            return db.delete(TABLE_NAME, COLUMN_DEVICE_ID + " = ?", new String[]{String.valueOf(deviceId)});
        }

        public int deleteAll() {
            return db.delete(TABLE_NAME, null, null);
        }
    }


    private static long devicesNewDevId;
    public class Devices extends Table implements BaseColumns {

        private static final String TABLE_NAME = "table_devices";
        private static final String COLUMN_DEVICE_ID = "device_id";
        private static final String COLUMN_DEVICE_UNIQUE_ID = "device_unique_id";
        private static final String COLUMN_DEVICE_NAME = "device_name";

        private static final String SQL_CREATE_DEVICES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                        COLUMN_DEVICE_ID + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_DEVICE_UNIQUE_ID + TEXT_TYPE + COMMA_SEP +
                        COLUMN_DEVICE_NAME + TEXT_TYPE + " )";

        private static final String SQL_DELETE_DEVICES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        private static final String SQL_SELECT_DEVICE_UNIQUE_ID =
                "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DEVICE_UNIQUE_ID + "=?";


        public Devices() {
            devicesNewDevId = getCount() + 1;
        }

        public void createTable(SQLiteDatabase sqldb) {
            sqldb.execSQL(SQL_CREATE_DEVICES);
        }

        public void deleteTable(SQLiteDatabase sqldb) {
            sqldb.execSQL(SQL_DELETE_DEVICES);
        }

        public long insert(String sDevUniqueId, String sDevName) {
            ContentValues row = new ContentValues();
            row.put(COLUMN_DEVICE_ID, devicesNewDevId);
            row.put(COLUMN_DEVICE_UNIQUE_ID, sDevUniqueId);
            row.put(COLUMN_DEVICE_NAME, sDevName);
            long newRowId = db.insert(TABLE_NAME, null, row);
            if (newRowId == -1) {
                return -1;
            }
            devicesNewDevId = newRowId + 1;
            return newRowId;
        }

        public List<SQLiteDevicesDTO> getAll() {
            Cursor cursor = null;
            SQLiteDevicesDTO devicesDTO = null;
            String sql = "SELECT * FROM " + TABLE_NAME;
            cursor = db.rawQuery(sql, null);
            List<SQLiteDevicesDTO> devicesDTOS = new ArrayList<>();
            while (cursor.moveToNext()) {
                devicesDTO = new SQLiteDevicesDTO();
                devicesDTO.setDeviceId(cursor.getLong(1));
                devicesDTO.setDeviceUniqueId(cursor.getString(2));
                devicesDTO.setDeviceName(cursor.getString(3));
                devicesDTOS.add(devicesDTO);
            }
            cursor.close();
            return devicesDTOS;
        }

        public long getCount(){
            return  DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        }

        private final String SQL_WHERE_DEVICE_ID = COLUMN_DEVICE_ID + " = ?";
        public SQLiteDevicesDTO getByDeviceId(long devId) {
            Cursor cursor = null;
            SQLiteDevicesDTO devicesDTO = null;
            String sql = SQL_SELECT_ALL + TABLE_NAME + SQL_WHERE + SQL_WHERE_DEVICE_ID;
            cursor = db.rawQuery(sql, new String[]{String.valueOf(devId)});

            if (cursor.moveToNext()) {
                devicesDTO = new SQLiteDevicesDTO();
                devicesDTO.setDeviceId(cursor.getLong(1));
                devicesDTO.setDeviceUniqueId(cursor.getString(2));
                devicesDTO.setDeviceName(cursor.getString(3));
            }
            cursor.close();
            return devicesDTO;
        }

        public long getCountByDeviceId(long devId){
            return  DatabaseUtils.queryNumEntries(db, TABLE_NAME, SQL_WHERE_DEVICE_ID,
                    new String[]{String.valueOf(devId)});
        }

        private final String SQL_WHERE_DEVICE_UNIQUE_ID = COLUMN_DEVICE_UNIQUE_ID + " = ?";
        public SQLiteDevicesDTO getByDeviceUniqueId(String devUniqueId) {
            Cursor cursor = null;
            SQLiteDevicesDTO devicesDTO = null;
            String sql = SQL_SELECT_ALL + TABLE_NAME + SQL_WHERE + SQL_WHERE_DEVICE_UNIQUE_ID;
            cursor = db.rawQuery(sql, new String[]{devUniqueId});

            if (cursor.moveToNext()) {
                devicesDTO = new SQLiteDevicesDTO();
                devicesDTO.setDeviceId(cursor.getLong(1));
                devicesDTO.setDeviceUniqueId(cursor.getString(2));
                devicesDTO.setDeviceName(cursor.getString(3));
            }
            cursor.close();
            return devicesDTO;
        }

        public long getCountByDeviceUniqueId(String devUniqueId){
            return  DatabaseUtils.queryNumEntries(db, TABLE_NAME, SQL_WHERE_DEVICE_UNIQUE_ID,
                    new String[]{devUniqueId});
        }

        public int deleteByDeviceId(long devId) {
            return db.delete(TABLE_NAME, COLUMN_DEVICE_ID + " = ? ", new String[]{String.valueOf(devId)});
        }

        public int deleteByDeviceUniqueId(String devUniqueId) {
            return db.delete(TABLE_NAME, COLUMN_DEVICE_UNIQUE_ID + " = ?", new String[]{devUniqueId});
        }

        public int deleteAll() {
            return db.delete(TABLE_NAME, null, null);
        }
    }

    public class Sensors extends Table implements BaseColumns {

        private static final String TABLE_NAME = "table_sensors";
        private static final String COLUMN_SENSOR_ID = "sensor_id";
        private static final String COLUMN_SENSOR_TYPE_ID = "sensor_type_id";
        private static final String COLUMN_SENSOR_NAME = "sensor_name";
        private static final String COLUMN_SENSOR_PARAMETER = "sensor_parameter";
        private static final String BAR_SEP = "-";

        private static final String SQL_CREATE_SENSORS =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                        COLUMN_SENSOR_ID + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_SENSOR_TYPE_ID + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_SENSOR_NAME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_SENSOR_PARAMETER + TEXT_TYPE + " )";

        private static final String SQL_DELETE_SENSORS =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

//        private static JsonParameters jsonValues = new JsonParameters();

        public Sensors() {
        }

        public void createTable(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_SENSORS);
        }

        public void deleteTable(SQLiteDatabase db) {
            db.execSQL(SQL_DELETE_SENSORS);
        }

        public long insert(long lDevId, long lSenTypeId, String sSenName, String sParams) {
            long lSenId = getSensorId(lDevId, lSenTypeId);
            ContentValues row = new ContentValues();
            row.put(COLUMN_SENSOR_ID, lSenId);
            row.put(COLUMN_SENSOR_TYPE_ID, lSenTypeId);
            row.put(COLUMN_SENSOR_NAME, sSenName);
            row.put(COLUMN_SENSOR_PARAMETER, sParams);

            long newRowId = db.insert(TABLE_NAME, null, row);

            if (newRowId == -1) {
                return -1;
            } else {
                return lSenId;
            }
        }

        public long insert(long lDevId, long lSenTypeId, String sSenName, int nParams) {
            long lSenId = getSensorId(lDevId, lSenTypeId);
            String parameterJson = JsonParameters.parametersToJson(sSenName, nParams);
            ContentValues row = new ContentValues();
            row.put(COLUMN_SENSOR_ID, lSenId);
            row.put(COLUMN_SENSOR_TYPE_ID, lSenTypeId);
            row.put(COLUMN_SENSOR_NAME, sSenName);
            row.put(COLUMN_SENSOR_PARAMETER, parameterJson);

            long newRowId = db.insert(TABLE_NAME, null, row);

            if (newRowId == -1) {
                return -1;
            } else {
                return lSenId;
            }
        }

        public long insert(long lDevId, long lSenTypeId, String sSenName, String[] names) {
            long lSenId = getSensorId(lDevId, lSenTypeId);
            String parameterJson = JsonParameters.parametersToJson(names);
            ContentValues row = new ContentValues();
            row.put(COLUMN_SENSOR_ID, lSenId);
            row.put(COLUMN_SENSOR_TYPE_ID, lSenTypeId);
            row.put(COLUMN_SENSOR_NAME, sSenName);
            row.put(COLUMN_SENSOR_PARAMETER, parameterJson);

            long newRowId = db.insert(TABLE_NAME, null, row);

            if (newRowId == -1) {
                return -1;
            } else {
                return lSenId;
            }
        }

        private long getSensorId(long devId, long senNo) {
            return devId * 100 + senNo;
        }

        private long getDeviceId(long senId) {
            return senId / 100;
        }

        private long getSensorNo(long senId) {
            return senId % 100;
        }

        public List<SQLiteSensorsDTO> getAll() {
            Cursor cursor = null;
            SQLiteSensorsDTO sensorsDTO = null;
            String sql = SQL_SELECT_ALL + TABLE_NAME;
            cursor = db.rawQuery(sql, null);
            List<SQLiteSensorsDTO> sensorsDTOS = new ArrayList<>();
            while (cursor.moveToNext()) {
                sensorsDTO = new SQLiteSensorsDTO();
                sensorsDTO.setSensorId(cursor.getLong(1));
                sensorsDTO.setSensorTypeId(cursor.getLong(2));
                sensorsDTO.setSensorName(cursor.getString(3));
                sensorsDTO.setParameter(cursor.getString(4));
                sensorsDTOS.add(sensorsDTO);
            }
            cursor.close();
            return sensorsDTOS;
        }

        public long getCount(){
            return  DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        }

        private final String SQL_WHERE_DEVICE_ID = COLUMN_SENSOR_ID + " BETWEEN ? AND ?";
        public List<SQLiteSensorsDTO> getByDeviceId(long devId) {
            Cursor cursor = null;
            SQLiteSensorsDTO sensorsDTO = null;
            String sql = SQL_SELECT_ALL + TABLE_NAME + SQL_WHERE + SQL_WHERE_DEVICE_ID;
            cursor = db.rawQuery(sql, new String[]{String.valueOf(devId), String.valueOf(devId + 99)});

            List<SQLiteSensorsDTO> sensorsDTOS = new ArrayList<>();
            while (cursor.moveToNext()) {
                sensorsDTO = new SQLiteSensorsDTO();
                sensorsDTO.setSensorId(cursor.getLong(1));
                sensorsDTO.setSensorTypeId(cursor.getLong(2));
                sensorsDTO.setSensorName(cursor.getString(3));
                sensorsDTO.setParameter(cursor.getString(4));
                sensorsDTOS.add(sensorsDTO);
            }
            cursor.close();
            return sensorsDTOS;
        }

        public long getCountByDeviceId(long devId){
            return  DatabaseUtils.queryNumEntries(db, TABLE_NAME, SQL_WHERE_DEVICE_ID,
                    new String[]{String.valueOf(devId), String.valueOf(devId + 99)});
        }

        private final String SQL_WHERE_SENSOR_TYPE_ID = COLUMN_SENSOR_TYPE_ID + " = ?";
        public SQLiteSensorsDTO getBySensorId(long devId, long senTypeId) {
            Cursor cursor = null;
            SQLiteSensorsDTO sensorsDTO = null;
            String sql = SQL_SELECT_ALL + TABLE_NAME + SQL_WHERE + SQL_WHERE_DEVICE_ID + SQL_AND + SQL_WHERE_SENSOR_TYPE_ID;
            cursor = db.rawQuery(sql,
                    new String[]{String.valueOf(getSensorId(devId, 0)),
                            String.valueOf(getSensorId(devId,  99)),
                            String.valueOf(senTypeId)});

            if (cursor.moveToNext()) {
                sensorsDTO = new SQLiteSensorsDTO();
                sensorsDTO.setSensorId(cursor.getLong(1));
                sensorsDTO.setSensorTypeId(cursor.getLong(2));
                sensorsDTO.setSensorName(cursor.getString(3));
                sensorsDTO.setParameter(cursor.getString(4));
            }
            cursor.close();
            return sensorsDTO;
        }

        public long getCountBySensorId(long devId, long senTypeId){
            return  DatabaseUtils.queryNumEntries(db, TABLE_NAME,
                    SQL_WHERE_DEVICE_ID + SQL_AND + SQL_WHERE_SENSOR_TYPE_ID,
                    new String[]{String.valueOf(getSensorId(devId, 0)),
                            String.valueOf(getSensorId(devId,  99))});
        }


        public int deleteByDeviceId(long devId) {
            return db.delete(TABLE_NAME, SQL_WHERE_DEVICE_ID, new String[]{String.valueOf(devId), String.valueOf(devId + 99)});
        }

        public int deleteBySensorId(long devId, long senTypeId) {
            return db.delete(TABLE_NAME,
                    SQL_WHERE_DEVICE_ID + SQL_AND + SQL_WHERE_SENSOR_TYPE_ID,
                    new String[]{String.valueOf(getSensorId(devId, 0)),
                            String.valueOf(getSensorId(devId,  99)),
                            String.valueOf(senTypeId)});
        }

        public int deleteAll() {
            return db.delete(TABLE_NAME, null, null);
        }
    }
}