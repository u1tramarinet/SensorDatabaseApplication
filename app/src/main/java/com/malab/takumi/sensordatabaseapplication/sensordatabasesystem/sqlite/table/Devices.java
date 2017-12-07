package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.SQLiteDevicesDAO;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.SQLiteDevicesDTO;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.SQLiteSensorsDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takumi on 2017/12/02.
 */

public class Devices extends Table implements BaseColumns {

    private static final String TABLE_NAME              = "table_devices";
    private static final String COLUMN_DEVICE_ID        = "device_id";
    private static final String COLUMN_DEVICE_UNIQUE_ID = "device_unique_id";
    private static final String COLUMN_DEVICE_NAME      = "device_name";

    private static final String SQL_CREATE_DEVICES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID                     + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    COLUMN_DEVICE_ID        + INTEGER_TYPE                  + COMMA_SEP +
                    COLUMN_DEVICE_UNIQUE_ID + TEXT_TYPE                     + COMMA_SEP +
                    COLUMN_DEVICE_NAME      + TEXT_TYPE                     + " )";

    private static final String SQL_DELETE_DEVICES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_SELECT_DEVICE_UNIQUE_ID =
            "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DEVICE_UNIQUE_ID + "=?";

    private static long newDevId;

    public Devices(){}

    public void createTable(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_DEVICES);
    }

    public void deleteTable(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_DEVICES);
    }

    public long insert(SQLiteDatabase db, String sDevUniqueId, String sDevName){
        ContentValues row = new ContentValues();
        row.put(COLUMN_DEVICE_ID, newDevId);
        row.put(COLUMN_DEVICE_UNIQUE_ID, sDevUniqueId);
        row.put(COLUMN_DEVICE_NAME, sDevName);
        long newRowId = db.insert(TABLE_NAME, null, row);
        if(newRowId == -1){
            return -1;
        }
        newDevId = newRowId + 1;
        return  newRowId;
    }

    public List<SQLiteDevicesDTO> getAll(SQLiteDatabase db){
        Cursor cursor = null;
        SQLiteDevicesDTO devicesDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME;
        cursor = db.rawQuery(sql, null);
        List<SQLiteDevicesDTO> devicesDTOS = new ArrayList<>();
        while (cursor.moveToNext()){
            devicesDTO = new SQLiteDevicesDTO();
            devicesDTO.setDeviceId(cursor.getLong(1));
            devicesDTO.setDeviceUniqueId(cursor.getString(2));
            devicesDTO.setDeviceName(cursor.getString(3));
            devicesDTOS.add(devicesDTO);
        }
        cursor.close();
        return devicesDTOS;
    }

    public SQLiteDevicesDTO getByDeviceId(SQLiteDatabase db, long devId){
        Cursor cursor = null;
        SQLiteDevicesDTO devicesDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DEVICE_ID + " = ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(devId)});

        if(cursor.moveToNext()){
            devicesDTO = new SQLiteDevicesDTO();
            devicesDTO.setDeviceId(cursor.getLong(1));
            devicesDTO.setDeviceUniqueId(cursor.getString(2));
            devicesDTO.setDeviceName(cursor.getString(3));
        }
        cursor.close();
        return devicesDTO;
    }

    public SQLiteDevicesDTO getByDeviceUniqueId(SQLiteDatabase db, long devUniqueId){
        Cursor cursor = null;
        SQLiteDevicesDTO devicesDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DEVICE_UNIQUE_ID + " = ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(devUniqueId)});

        if(cursor.moveToNext()){
            devicesDTO = new SQLiteDevicesDTO();
            devicesDTO.setDeviceId(cursor.getLong(1));
            devicesDTO.setDeviceUniqueId(cursor.getString(2));
            devicesDTO.setDeviceName(cursor.getString(3));
        }
        cursor.close();
        return devicesDTO;
    }

    public int deleteByDeviceId(SQLiteDatabase db, long devId){
        return db.delete(TABLE_NAME, COLUMN_DEVICE_ID + " = ? ", new String[]{String.valueOf(devId)});
    }

    public int deleteByDeviceUniqueId(SQLiteDatabase db, String devUniqueId){
        return db.delete(TABLE_NAME,COLUMN_DEVICE_UNIQUE_ID + " = ?", new String[]{devUniqueId});
    }

    public int deleteAll(SQLiteDatabase db){
        return db.delete(TABLE_NAME, null, null);
    }




//    public String getColumnDeviceId(){
//        return COLUMN_DEVICE_ID;
//    }
//
//    public String getColumnDeviceUniqueId(){
//        return COLUMN_DEVICE_UNIQUE_ID;
//    }
//
//    public String getColumnDeviceName(){
//        return COLUMN_DEVICE_NAME;
//    }
//
//    public String getSqlCreateTable(){
//        return SQL_CREATE_DEVICES;
//    }
//
//    public String getSqlDeleteTable(){
//        return SQL_DELETE_DEVICES;
//    }
//
//    /** **/
//
//    // デバイスかあるかどうか　1つあり：true、それ以外：false
//    private boolean exists(SQLiteDatabase db, String deviceUniqueId){
//        return getCount(db, deviceUniqueId) == 1;
//    }
//
//    // デバイスを登録する（新規登録１：deviceIdを総数から生成）
//    // 成功したらデバイスID、失敗したら-1が返る
//    private long register(SQLiteDatabase db, String deviceUniqueId){
//        long newDeviceId = getCount(db);
//        return insert(db, newDeviceId, deviceUniqueId);
//    }
//
//    // デバイスIDをデバイスユニークIDから取得する
//    public long getDeviceId(SQLiteDatabase db, String deviceUniqueId){
//        if(!exists(db, deviceUniqueId)){
//            return register(db, deviceUniqueId);
//        }
//        Cursor cursor = db.rawQuery(SQL_SELECT_DEVICE_UNIQUE_ID, new String[]{deviceUniqueId});
//        if(cursor.getCount() != 1) return -1;
//        try{
//            if(cursor.moveToNext()){
//                return cursor.getLong(cursor.getColumnIndex(COLUMN_DEVICE_ID));
//            }
//        }catch(SQLException sqle){
//            sqle.printStackTrace();
//        }finally {
//            cursor.close();
//        }
//        return -1;
//    }
//
//    /** **/
//
//    // 登録済みのデバイスの総数
//    private long getCount(SQLiteDatabase db){
//        return  DatabaseUtils.queryNumEntries(db, TABLE_NAME);
//    }
//
//    private static final String SQL_WHERE_DEVICE_UNIQUE_ID = COLUMN_DEVICE_UNIQUE_ID + "=?";
//    // 指定したデバイスユニークIDのデバイスはいくつあるか　基本存在するなら1つのはず
//    private long getCount(SQLiteDatabase db, String deviceUniqueId){
//        return DatabaseUtils.queryNumEntries(db, TABLE_NAME, SQL_WHERE_DEVICE_UNIQUE_ID, new String[]{deviceUniqueId});
//    }
//
//    // 新しいデバイスを挿入する（新規登録２）
//    private long insert(SQLiteDatabase db, long deviceId, String deviceUniqueId){
//        return insert(db, deviceId, deviceUniqueId, "Device"+deviceId);
//    }
//
//    // 新しいデバイスを挿入する（フルセット）（新規登録３）デバイスIDを返す
//    private long insert(SQLiteDatabase db, long deviceId, String deviceUniqueId, String deviceName){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_DEVICE_ID, deviceId);
//        contentValues.put(COLUMN_DEVICE_UNIQUE_ID, deviceUniqueId);
//        contentValues.put(COLUMN_DEVICE_NAME, deviceName);
//
//        long newRowId = db.insert(TABLE_NAME, null, contentValues);
//        if(newRowId != -1){
//            return deviceId;
//        }else{
//            return -1;
//        }
//    }
//
//    // デバイスネームを設定する
//    long setDeviceName(SQLiteDatabase db, String deviceUniqueId, String deviceName){
//        if(exists(db, deviceUniqueId)) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(COLUMN_DEVICE_NAME, deviceName);
//            return db.update(TABLE_NAME, contentValues, COLUMN_DEVICE_UNIQUE_ID + " = ?", new String[]{deviceUniqueId});
//        }else{
//            return -1;
//        }
//    }
//
//    // デバイスIDをもとに1デバイスを削除する
//    int delete(SQLiteDatabase db, long deviceId){
//        return db.delete(TABLE_NAME, COLUMN_DEVICE_ID + " = ?", new String[]{String.valueOf(deviceId)});
//    }
//
//    // デバイスユニークIDをもとに1デバイスを削除する
//    int delete(SQLiteDatabase db, String deviceUniqueID){
//        return db.delete(TABLE_NAME, COLUMN_DEVICE_UNIQUE_ID + " = ?", new String[]{deviceUniqueID});
//    }
//
//
//
//
//
//    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
//    @Override
//    public Cursor getAll(SQLiteDatabase db) {
//        return db.rawQuery(SQL_SELECT_ALL, null);
//    }
//
//    private static final String SQL_SELECT_ROW = "SELECT * FROM " + TABLE_NAME + "WHERE";
//    /**
//     *
//     * @param db
//     * @param contentValues deviceId or deviceUniqueID or deviceName
//     * @return contentValues 1 row
//     */
//    @Override
//    public ContentValues get(SQLiteDatabase db, ContentValues contentValues) {
//        Cursor cursor = null;
//
//        if(contentValues.containsKey(COLUMN_DEVICE_ID)){
//            long deviceId = contentValues.getAsLong(COLUMN_DEVICE_ID);
//            String sql = SQL_SELECT_ROW + " " + COLUMN_DEVICE_ID + " = ?";
//            cursor = db.rawQuery(sql, new String[]{ String.valueOf(deviceId) });
//        }else if(contentValues.containsKey(COLUMN_DEVICE_UNIQUE_ID)){
//            String deviceUniqueId = contentValues.getAsString(COLUMN_DEVICE_UNIQUE_ID);
//            String sql = SQL_SELECT_ROW + " " + COLUMN_DEVICE_UNIQUE_ID + " = ?";
//            cursor = db.rawQuery(sql, new String[]{ deviceUniqueId });
//        }else if(contentValues.containsKey(COLUMN_DEVICE_NAME)){
//            String deviceName = contentValues.getAsString(COLUMN_DEVICE_NAME);
//            String sql = SQL_SELECT_ROW + " " + COLUMN_DEVICE_NAME + " = ?";
//            cursor = db.rawQuery(sql, new String[]{ deviceName });
//        }
//        if(cursor == null) return null;
//        ContentValues row = null;
//        if( cursor.moveToNext() ){
//            row = setCV(cursor);
//        }
//        cursor.close();
//        db.close();
//        return row;
//    }
//
//    @Override
//    public ContentValues setCV(Cursor cursor) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_DEVICE_ID, cursor.getLong(1));
//        contentValues.put(COLUMN_DEVICE_UNIQUE_ID, cursor.getString(2));
//        contentValues.put(COLUMN_DEVICE_NAME, cursor.getString(3));
//        return contentValues;
//    }
//
//    public ContentValues setSV(long deviceId, String deviceUniqueId, String deviceName){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_DEVICE_ID, deviceId);
//        contentValues.put(COLUMN_DEVICE_UNIQUE_ID, deviceUniqueId);
//        contentValues.put(COLUMN_DEVICE_NAME, deviceName);
//        return contentValues;
//    }
//
//    /**
//     *
//     * @param db
//     * @param row all
//     * @return device id or error -1
//     */
//    @Override
//    public long add(SQLiteDatabase db, ContentValues row) {
//        long id = db.insert(TABLE_NAME, null, row);
//        db.close();
//        if(id != -1){
//            return row.getAsLong(COLUMN_DEVICE_ID);
//        }
//        return -1;
//    }
//
//    /**
//     *
//     * @param db
//     * @param contentValues deviceId or deviceUniqueId or deviceName
//     * @return number of deleted row
//     */
//    @Override
//    public int delete(SQLiteDatabase db, ContentValues contentValues) {
//        int res = -1;
//        if(contentValues.containsKey(COLUMN_DEVICE_ID)){
//            long deviceId = contentValues.getAsLong(COLUMN_DEVICE_ID);
//            res = db.delete(TABLE_NAME, COLUMN_DEVICE_ID + " = ?", new String[]{ String.valueOf( deviceId ) });
//        }else if(contentValues.containsKey(COLUMN_DEVICE_UNIQUE_ID)){
//            String deviceUniqueId = contentValues.getAsString(COLUMN_DEVICE_UNIQUE_ID);
//            res = db.delete(TABLE_NAME, COLUMN_DEVICE_UNIQUE_ID + " = ?", new String[]{ deviceUniqueId });
//        }else if(contentValues.containsKey(COLUMN_DEVICE_NAME)){
//            String deviceName = contentValues.getAsString(COLUMN_DEVICE_NAME);
//            res = db.delete(TABLE_NAME, COLUMN_DEVICE_NAME + " = ?", new String[]{ deviceName } );
//        }
//        db.close();
//        return res;
//    }
//
//    /**
//     *
//     * @param db
//     * @param row device id and device unique id
//     * @return number of updated rows
//     */
//    @Override
//    public int update(SQLiteDatabase db, ContentValues row) {
//        int res = db.update(TABLE_NAME, row, COLUMN_DEVICE_ID + " = ? AND " + COLUMN_DEVICE_UNIQUE_ID + " = ?",
//                new String[]{ row.getAsString(COLUMN_DEVICE_ID), row.getAsString(COLUMN_DEVICE_UNIQUE_ID) });
//        db.close();
//        return res;
//    }
}