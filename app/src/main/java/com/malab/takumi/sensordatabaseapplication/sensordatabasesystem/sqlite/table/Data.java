package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.SQLiteDataDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takumi on 2017/12/02.
 */

public class Data extends Table implements BaseColumns {
    private static final String TAG = "Table/DataDAO";
    private static final String TABLE_NAME       = "table_data";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_DEVICE_ID = "device_id";
    private static final String COLUMN_DATA = "data";

    private static final String SQL_CREATE_DATA =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID              + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    COLUMN_TIMESTAMP + INTEGER_TYPE                  + COMMA_SEP +
                    COLUMN_DEVICE_ID + INTEGER_TYPE                  + COMMA_SEP +
                    COLUMN_DATA + TEXT_TYPE                     + " )";

    private static final String SQL_DELETE_DATA =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Data(){}

    public void createTable(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_DATA);
    }

    public void deleteTable(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_DATA);
    }

    public long insert(SQLiteDatabase db, long timestamp, long deviceId, String data){
        ContentValues row = new ContentValues();
        row.put(COLUMN_TIMESTAMP, timestamp);
        row.put(COLUMN_DEVICE_ID, deviceId);
        row.put(COLUMN_DATA, data);

        return db.insert(TABLE_NAME, null, row);
    }

    public List<SQLiteDataDTO> getAll(SQLiteDatabase db){
        Cursor cursor = null;
        SQLiteDataDTO dataDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME;
        cursor = db.rawQuery(sql, null);
        List<SQLiteDataDTO> dataDTOS = new ArrayList<>();
        while (cursor.moveToNext()){
            dataDTO = new SQLiteDataDTO();
            dataDTO.setTimestamp(cursor.getLong(1));
            dataDTO.setDeviceId(cursor.getLong(2));
            dataDTO.setData(cursor.getString(3));
            dataDTOS.add(dataDTO);
        }
        cursor.close();
        return dataDTOS;
    }

    public List<SQLiteDataDTO> getByTimestamp(SQLiteDatabase db, long timestamp){
        Cursor cursor = null;
        SQLiteDataDTO dataDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TIMESTAMP + " = ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(timestamp)});
        List<SQLiteDataDTO> dataDTOS = new ArrayList<>();
        while (cursor.moveToNext()){
            dataDTO = new SQLiteDataDTO();
            dataDTO.setTimestamp(cursor.getLong(1));
            dataDTO.setDeviceId(cursor.getLong(2));
            dataDTO.setData(cursor.getString(3));
            dataDTOS.add(dataDTO);
        }
        cursor.close();
        return dataDTOS;
    }

    public List<SQLiteDataDTO> getByTimestamp(SQLiteDatabase db, long start, long end){
        Cursor cursor = null;
        SQLiteDataDTO dataDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TIMESTAMP + " BETWEEN ? AND ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(start), String.valueOf(end)});
        List<SQLiteDataDTO> dataDTOS = new ArrayList<>();
        while (cursor.moveToNext()){
            dataDTO = new SQLiteDataDTO();
            dataDTO.setTimestamp(cursor.getLong(1));
            dataDTO.setDeviceId(cursor.getLong(2));
            dataDTO.setData(cursor.getString(3));
            dataDTOS.add(dataDTO);
        }
        cursor.close();
        return dataDTOS;
    }

    public List<SQLiteDataDTO> getByDeviceId(SQLiteDatabase db, long deviceId){
        Cursor cursor = null;
        SQLiteDataDTO dataDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DEVICE_ID + " = ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(deviceId)});
        List<SQLiteDataDTO> dataDTOS = new ArrayList<>();
        while (cursor.moveToNext()){
            dataDTO = new SQLiteDataDTO();
            dataDTO.setTimestamp(cursor.getLong(1));
            dataDTO.setDeviceId(cursor.getLong(2));
            dataDTO.setData(cursor.getString(3));
            dataDTOS.add(dataDTO);
        }
        cursor.close();
        return dataDTOS;
    }

    public int deleteByTimestamp(SQLiteDatabase db, long timestamp){
        return db.delete(TABLE_NAME, COLUMN_TIMESTAMP + " = ? ", new String[]{String.valueOf(timestamp)});
    }

    public int deleteByTimestamp(SQLiteDatabase db, long start, long end){
        String sql = COLUMN_TIMESTAMP +  " BETWEEN " + start + " AND " + end;
        return db.delete(TABLE_NAME, sql, new String[]{String.valueOf(start), String.valueOf(end)});
    }

    public int deleteByDeviceId(SQLiteDatabase db, long deviceId){
        return db.delete(TABLE_NAME,COLUMN_DEVICE_ID + " = ?", new String[]{String.valueOf(deviceId)});
    }

    public int deleteAll(SQLiteDatabase db){
        return db.delete(TABLE_NAME, null, null);
    }





//    public String getColumnTimestamp(){
//        return COLUMN_TIMESTAMP;
//    }
//
//    public String getColumnDeviceId(){
//        return COLUMN_DEVICE_ID;
//    }
//
//    public String getColumnValues(){
//        return COLUMN_DATA;
//    }
//
//    public String getSqlCreateTable(){
//        return SQL_CREATE_DATA;
//    }
//
//    public String getSqlDeleteTable(){
//        return SQL_DELETE_DATA;
//    }

//    public long insert(SQLiteDatabase db, long timestamp, long deviceId, String data){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_TIMESTAMP, timestamp);
//        contentValues.put(COLUMN_DEVICE_ID, deviceId);
//        contentValues.put(COLUMN_DATA, data);
//
//        return db.insert(TABLE_NAME, null, contentValues);
//    }
//
//    long getCount(SQLiteDatabase db){
//        return  DatabaseUtils.queryNumEntries(db, TABLE_NAME);
//    }
//
//    long update(SQLiteDatabase db, long timestamp, long deviceId, String data){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_TIMESTAMP, timestamp);
//        contentValues.put(COLUMN_DEVICE_ID, deviceId);
//        contentValues.put(COLUMN_DATA, data);
//
//        return db.update(TABLE_NAME, contentValues, COLUMN_TIMESTAMP + " = ?", new String[]{String.valueOf(timestamp)});
//    }
//
//
//    int delete(SQLiteDatabase db, long timestamp){
//        return db.delete(TABLE_NAME, COLUMN_TIMESTAMP + " = ?", new String[]{String.valueOf(timestamp)});
//    }






//    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
//    @Override
//    public Cursor getAll(SQLiteDatabase db) {
//        return db.rawQuery(SQL_SELECT_ALL, null);
//    }
//
//    private static final String SQL_SELECT_ROW = "SELECT * FROM " + TABLE_NAME + "WHERE " + COLUMN_TIMESTAMP + " = ?";
//    /**
//     * key is long timestamp
//     * @param db
//     * @param contentValues
//     * @return ContentValues 1 row data
//     */
//    public ContentValues get(SQLiteDatabase db, ContentValues contentValues){
//        long timestamp = contentValues.getAsLong(COLUMN_TIMESTAMP);
//        Cursor cursor = db.rawQuery(SQL_SELECT_ROW, new String[]{ String.valueOf(timestamp) });
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
//        contentValues.put(COLUMN_TIMESTAMP, cursor.getLong(1));
//        contentValues.put(COLUMN_DEVICE_ID, cursor.getLong(2));
//        contentValues.put(COLUMN_DATA, cursor.getString(3));
//        return contentValues;
//    }
//
//    public ContentValues setCV(long timestamp, long deviceId, String values){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_TIMESTAMP, timestamp);
//        contentValues.put(COLUMN_DEVICE_ID, deviceId);
//        contentValues.put(COLUMN_DATA, values);
//        return contentValues;
//    }
//
//
//    /**
//     *
//     * @param db database
//     * @param row all
//     * @return added id
//     */
//    @Override
//    public long add(SQLiteDatabase db, ContentValues row) {
//        if(db == null)Log.e(TAG, "db is null");
//        long id = db.insert(TABLE_NAME, null, row);
//
//        if(id == -1){
//            Log.e(TAG, "INSERT出来てないよ。");
//            Log.d(TAG, row.toString());
//        }else{
//            Log.d(TAG, "INSERT出来てるじゃーん : id = " + id);
//        }
//        db.close();
//        return id;
//    }
//
//    /**
//     *
//     * @param db
//     * @param contentValues timestamp or deviceId
//     * @return success: 0-, failed:-1
//     */
//    @Override
//    public int delete(SQLiteDatabase db, ContentValues contentValues) {
//        int res = -1;
//        if(contentValues.containsKey(COLUMN_TIMESTAMP)){
//            long timestamp = contentValues.getAsLong(COLUMN_TIMESTAMP);
//            res = db.delete(TABLE_NAME, COLUMN_DEVICE_ID + " = ?", new String[]{ String.valueOf( timestamp ) });
//        }else if(contentValues.containsKey(COLUMN_DEVICE_ID)){
//            long deviceId = contentValues.getAsLong(COLUMN_DEVICE_ID);
//            res = db.delete(TABLE_NAME, COLUMN_DEVICE_ID + " = ?", new String[]{ String.valueOf( deviceId ) });
//        }
//        db.close();
//        return res;
//    }
//
//    /**
//     *
//     * @param db
//     * @param row timestamp and device id
//     * @return number of updated row
//     */
//    @Override
//    public int update(SQLiteDatabase db, ContentValues row) {
//        int res = db.update(TABLE_NAME, row, COLUMN_TIMESTAMP + " = ? AND " + COLUMN_DEVICE_ID + " = ?",
//                new String[]{ row.getAsString(COLUMN_TIMESTAMP), row.getAsString(COLUMN_DEVICE_ID) });
//        db.close();
//        return res;
//    }
}