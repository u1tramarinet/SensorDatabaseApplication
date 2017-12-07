package com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.SQLiteSensorsDTO;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.sqlite.json.JsonParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takumi on 2017/12/02.
 */

public class Sensors extends Table implements BaseColumns {

    private static final String TABLE_NAME              = "table_sensors";
    private static final String COLUMN_SENSOR_ID        = "sensor_id";
    private static final String COLUMN_SENSOR_TYPE_ID   = "sensor_type_id";
    private static final String COLUMN_SENSOR_NAME      = "sensor_name";
    private static final String COLUMN_SENSOR_PARAMETER = "sensor_parameter";
    private static final String BAR_SEP                 = "-";

    private static final String KEY_DEVICE_ID           = "device_id";

    private static final String SQL_CREATE_SENSORS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID                     + INTEGER_TYPE + " PRIMARY KEY"    + COMMA_SEP +
                    COLUMN_SENSOR_ID        + INTEGER_TYPE                     + COMMA_SEP +
                    COLUMN_SENSOR_TYPE_ID   + INTEGER_TYPE                     + COMMA_SEP +
                    COLUMN_SENSOR_NAME      + TEXT_TYPE                        + COMMA_SEP +
                    COLUMN_SENSOR_PARAMETER + TEXT_TYPE                        + " )";

    private static final String SQL_SELECT_ALL_WHERE =
            "SELECT * FROM " + TABLE_NAME + " WHERE ";

    private static final String SQL_WHERE_DEVICE_ID =
            COLUMN_SENSOR_ID + " LIKE ? ";

    private static final String SQL_WHERE_SENSOR_TYPE_ID =
            COLUMN_SENSOR_TYPE_ID + " = ? ";

    private static final String SQL_DELETE_SENSORS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static JsonParameters jsonExchanger = new JsonParameters();

    public Sensors(){}

    public void createTable(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_SENSORS);
    }

    public void deleteTable(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_SENSORS);
    }

    public long insert(SQLiteDatabase db, long lDevId, long lSenTypeId, String sSenName, String sParams){
        long lSenId = getSensorId(lDevId, lSenTypeId);
        ContentValues row = new ContentValues();
        row.put(COLUMN_SENSOR_ID, lSenId);
        row.put(COLUMN_SENSOR_TYPE_ID, lSenTypeId);
        row.put(COLUMN_SENSOR_NAME, sSenName);
        row.put(COLUMN_SENSOR_PARAMETER, sParams);

        long newRowId = db.insert(TABLE_NAME, null, row);

        if(newRowId == -1){
            return -1;
        } else {
            return lSenId;
        }
    }

    private long getSensorId(long deviceId, long sensorNo){
        return deviceId * 100 + sensorNo;
    }

    private long getDeviceId(long sensorId){
        return sensorId / 100;
    }

    private long getSensorNo(long sensorId){
        return sensorId % 100;
    }

    public List<SQLiteSensorsDTO> getAll(SQLiteDatabase db){
        Cursor cursor = null;
        SQLiteSensorsDTO sensorsDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME;
        cursor = db.rawQuery(sql, null);
        List<SQLiteSensorsDTO> sensorsDTOS = new ArrayList<>();
        while (cursor.moveToNext()){
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

    public List<SQLiteSensorsDTO> getByDeviceId(SQLiteDatabase db, long deviceId){
        Cursor cursor = null;
        SQLiteSensorsDTO sensorsDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_SENSOR_ID + " BETWEEN ? AND ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(deviceId), String.valueOf(deviceId + 99)});

        List<SQLiteSensorsDTO> sensorsDTOS = new ArrayList<>();
        while (cursor.moveToNext()){
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

    public SQLiteSensorsDTO getBySensorId(SQLiteDatabase db, long deviceId, long sensorTypeId){
        Cursor cursor = null;
        SQLiteSensorsDTO sensorsDTO = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_SENSOR_ID + " BETWEEN ? AND ? AND " + COLUMN_SENSOR_TYPE_ID + " = ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(deviceId), String.valueOf(deviceId + 99), String.valueOf(sensorTypeId)});

        if(cursor.moveToNext()){
            sensorsDTO = new SQLiteSensorsDTO();
            sensorsDTO.setSensorId(cursor.getLong(1));
            sensorsDTO.setSensorTypeId(cursor.getLong(2));
            sensorsDTO.setSensorName(cursor.getString(3));
            sensorsDTO.setParameter(cursor.getString(4));
        }
        cursor.close();
        return sensorsDTO;
    }


    public int deleteByDeviceId(SQLiteDatabase db, long deviceId){
        return db.delete(TABLE_NAME, COLUMN_SENSOR_ID + " LIKE ? ", new String[]{(deviceId + BAR_SEP + "%")});
    }

    public int deleteBySensorId(SQLiteDatabase db, long deviceId, long sensorTypeId){
        return db.delete(TABLE_NAME,
                COLUMN_SENSOR_ID + " LIKE ? AND " + COLUMN_SENSOR_TYPE_ID + " = ?",
                new String[]{(deviceId + BAR_SEP + "%"), String.valueOf(sensorTypeId)});
    }

    public int deleteAll(SQLiteDatabase db){
        return db.delete(TABLE_NAME, null, null);
    }







//    public String getColumnSensorId(){
//        return COLUMN_SENSOR_ID;
//    }
//
//    public String getColumnSensorTypeId(){
//        return COLUMN_SENSOR_TYPE_ID;
//    }
//
//    public String getColumnSensorName(){
//        return COLUMN_SENSOR_NAME;
//    }
//
//    public String getColumnSensorParameter(){
//        return COLUMN_SENSOR_PARAMETER;
//    }
//
//    public String getKeyDeviceId(){
//        return KEY_DEVICE_ID;
//    }
//
//    public String getSqlCreateTable(){
//        return SQL_CREATE_SENSORS;
//    }
//
//    public String getSqlDeleteTable(){
//        return SQL_DELETE_SENSORS;
//    }
//
//    private boolean exists(SQLiteDatabase db, long deviceId, int sensorTypeId){
//        return getCount(db, deviceId, sensorTypeId) == 1;
//    }
//
//    private long register(SQLiteDatabase db, long deviceId, int sensorTypeId, String sensorName, int nParam){
//        if (!exists(db, deviceId, sensorTypeId)) {
//            //新しいセンサIDを生成する
//            //  デバイスIDを有するセンサIDがあるか調べる
//            long sensorNo = getCount(db, deviceId);
//            String sensorId = format(deviceId, sensorNo);
//            // パラメタ列のJSONを作る
//            String parameters = jsonExchanger.parametersToJson(sensorName, nParam);
//            // 挿入する
//            return 0;//insert(db, sensorId, sensorTypeId, sensorName, parameters);
//        }
//        return -1;
//    }
//
//    private String getSensorId(SQLiteDatabase db, long deviceId, int sensorTypeId){
//        Cursor cursor = db.rawQuery(SQL_SELECT_ALL_WHERE + SQL_WHERE_DEVICE_ID + " AND " + SQL_WHERE_SENSOR_TYPE_ID,
//                new String[]{ deviceId + BAR_SEP + "%", String.valueOf(sensorTypeId)});
//
//        if(1 != cursor.getCount()) return null;
//
//        try{
//            if(cursor.moveToNext()){
//                return cursor.getString(cursor.getColumnIndex(COLUMN_SENSOR_ID));
//            }
//        }catch (SQLException sqle){
//            sqle.printStackTrace();
//        }finally {
//            cursor.close();
//        }
//        return null;
//    }
//
//    //
//    public long getSensorNo(SQLiteDatabase db, long deviceId, int sensorTypeId, String sensorName, int nParam){
//        if(!exists(db, deviceId, sensorTypeId)){
//            return register(db, deviceId,sensorTypeId, sensorName, nParam);
//        }
//        Cursor cursor = db.rawQuery(SQL_SELECT_ALL_WHERE + SQL_WHERE_DEVICE_ID + " AND " + SQL_WHERE_SENSOR_TYPE_ID,
//                new String[]{deviceId + BAR_SEP + "%", String.valueOf(sensorTypeId)});
//        if(1 != cursor.getCount()) return -1;
//        try{
//            if(cursor.moveToNext()){
//                String sensorId = cursor.getString(cursor.getColumnIndex(COLUMN_SENSOR_ID));
//                return getSensorNo(sensorId);
//            }
//
//        }catch (SQLException sqle){
//            sqle.printStackTrace();
//        }finally {
//            cursor.close();
//        }
//        return -1;
//    }
//
//    // センサIDへの整形　"deviceID-sensorNo" = sensorId
//    private String format(long deviceId, long sensorNo){
//        return String.format(Locale.JAPAN, "%d"+ BAR_SEP +"%d", deviceId, sensorNo);
//    }
//
//    // センサIDからデバイスIDを取得する
//    long getDeviceId(String sensorId){
//        return Long.parseLong((sensorId.split(BAR_SEP))[0]);
//    }
//
//    // センサIDからセンサNOを取得する
//    private long getSensorNo(String sensorId){
//        return Long.parseLong((sensorId.split(BAR_SEP))[1]);
//    }
//
//    private boolean exists(SQLiteDatabase db, long deviceId){
//        return getCount(db, deviceId) > 0;
//    }
//
//    private boolean exists(SQLiteDatabase db, int sensorTypeId){
//        return getCount(db, sensorTypeId) > 0;
//    }
//
//    // 登録済みのセンサの総数
//    private long getCount(SQLiteDatabase db){
//        return  DatabaseUtils.queryNumEntries(db, TABLE_NAME);
//    }
//
//    // 指定したデバイスIDのセンサはいくつあるか　そのデバイスの登録済みセンサの総数
//    private long getCount(SQLiteDatabase db, long deviceId){
//        return DatabaseUtils.queryNumEntries(db, TABLE_NAME,
//                SQL_WHERE_DEVICE_ID,
//                new String[]{deviceId + BAR_SEP + "%"});
//    }
//
//    // 指定したセンサタイプIDのセンサはいくつあるか　デバイスを跨ぐため複数あるのも普通
//    private long getCount(SQLiteDatabase db, int sensorTypeId){
//        return DatabaseUtils.queryNumEntries(db, TABLE_NAME,
//                SQL_WHERE_SENSOR_TYPE_ID,
//                new String[]{String.valueOf(sensorTypeId)});
//    }
//
//    // 指定したデバイスID、センサIDのセンサはいくつあるか。原則１つ以下
//    private long getCount(SQLiteDatabase db, long deviceId, int sensorTypeId){
//        return DatabaseUtils.queryNumEntries(db, TABLE_NAME,
//                SQL_WHERE_DEVICE_ID + " AND " + SQL_WHERE_SENSOR_TYPE_ID,
//                new String[]{(deviceId + BAR_SEP + "%"), String.valueOf(sensorTypeId)});
//    }
//
//
//
//    // センサNameを変更する
//    long setSensorName(SQLiteDatabase db, long deviceId, int sensorTypeId, String sensorName){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_SENSOR_NAME, sensorName);
//
//        return db.update(TABLE_NAME, contentValues,
//                SQL_WHERE_DEVICE_ID + " AND " + SQL_WHERE_SENSOR_TYPE_ID,
//                new String[]{deviceId + BAR_SEP + "%", String.valueOf(sensorTypeId)});
//    }
//
//    // センサIDをもとに1センサ
//    int delete(SQLiteDatabase db, String sensorId){
//        return db.delete(TABLE_NAME, COLUMN_SENSOR_ID + " = ?", new String[]{sensorId});
//    }
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
//
//    private static final String SQL_SELECT_ROW = "SELECT * FROM " + TABLE_NAME + "WHERE";
//
//    /**
//     *
//     * @param db
//     * @param contentValues deviceId and sensorTypeId or device id
//     * @return 1 row
//     */
//    @Override
//    public ContentValues get(SQLiteDatabase db, ContentValues contentValues) {
//        Cursor cursor = null;
//
//        if(contentValues.containsKey(KEY_DEVICE_ID) && contentValues.containsKey(COLUMN_SENSOR_TYPE_ID)){
//            long deviceId = contentValues.getAsLong(KEY_DEVICE_ID);
//            int sensorTypeId = contentValues.getAsInteger(COLUMN_SENSOR_TYPE_ID);
//            cursor = db.rawQuery(SQL_SELECT_ALL_WHERE + SQL_WHERE_DEVICE_ID + " AND " + SQL_WHERE_SENSOR_TYPE_ID,
//                    new String[]{(deviceId + BAR_SEP + "%"), String.valueOf(sensorTypeId)});
//        }else if(contentValues.containsKey(KEY_DEVICE_ID)){
//            long deviceId = contentValues.getAsLong(KEY_DEVICE_ID);
//            cursor = db.rawQuery(SQL_SELECT_ALL_WHERE + SQL_WHERE_DEVICE_ID,
//                    new String[]{(deviceId + BAR_SEP + "%")});
//        }
//        if(cursor == null)return null;
//        ContentValues row = null;
//        if(cursor.moveToNext()){
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
//        contentValues.put(COLUMN_SENSOR_ID, cursor.getString(1));
//        contentValues.put(COLUMN_SENSOR_TYPE_ID, cursor.getLong(2));
//        contentValues.put(COLUMN_SENSOR_NAME, cursor.getString(3));
//        contentValues.put(COLUMN_SENSOR_PARAMETER, cursor.getString(4));
//        return contentValues;
//    }
//
//    public ContentValues setCV(String sensorId, long sensorTypeId, String sensorName, String parameter){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_SENSOR_ID, sensorId);
//        contentValues.put(COLUMN_SENSOR_TYPE_ID, sensorTypeId);
//        contentValues.put(COLUMN_SENSOR_NAME, sensorName);
//        contentValues.put(COLUMN_SENSOR_PARAMETER, parameter);
//        return contentValues;
//    }
//
//    /**
//     *
//     * @param db
//     * @param row all
//     * @return
//     */
//    @Override
//    public long add(SQLiteDatabase db, ContentValues row) {
//        long id = db.insert(TABLE_NAME, null, row);
//        db.close();
//        return id;
//    }
//
//
//
//    /**
//     *
//     * @param db
//     * @param contentValues deviceId && sensorTypeId or sensorId
//     * @return
//     */
//    @Override
//    public int delete(SQLiteDatabase db, ContentValues contentValues) {
//        int res = -1;
//        if(contentValues.containsKey(KEY_DEVICE_ID) && contentValues.containsKey(COLUMN_SENSOR_TYPE_ID)){
//            long deviceId = contentValues.getAsLong(KEY_DEVICE_ID);
//            int sensorTypeId = contentValues.getAsInteger(COLUMN_SENSOR_TYPE_ID);
//            res = db.delete(TABLE_NAME, SQL_WHERE_DEVICE_ID + "AND" + SQL_WHERE_SENSOR_TYPE_ID,
//                    new String[]{(deviceId + BAR_SEP + "%"), String.valueOf(sensorTypeId)});
//        }else if(contentValues.containsKey(COLUMN_SENSOR_ID)){
//            String sensorId = contentValues.getAsString(COLUMN_SENSOR_ID);
//            res = db.delete(TABLE_NAME, COLUMN_SENSOR_ID + " = ?", new String[]{ sensorId });
//        }
//        db.close();
//        return res;
//    }
//
//
//    @Override
//    public int update(SQLiteDatabase db, ContentValues row) {
//        long deviceId = row.getAsLong(KEY_DEVICE_ID);
//        int sensorTypeId = row.getAsInteger(COLUMN_SENSOR_TYPE_ID);
//        int res = db.update(TABLE_NAME, row, SQL_WHERE_DEVICE_ID + "AND" + SQL_WHERE_SENSOR_TYPE_ID,
//                new String[]{(deviceId + BAR_SEP + "%"), String.valueOf(sensorTypeId) });
//        db.close();
//        return res;
//    }
}
