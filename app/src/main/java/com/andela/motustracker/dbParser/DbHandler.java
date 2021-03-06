package com.andela.motustracker.dbParser;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.model.LocationData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Spykins on 06/04/16.
 */
public class DbHandler extends SQLiteOpenHelper {
    private Double timeSpent;

    public DbHandler() {
        super(AppContext.get(), DbConfig.DATABASE_NAME.getRealName(), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DbConfig.TABLE_NAME.getRealName() + " (" +
                DbConfig.COLUMN_ID.getRealName() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbConfig.COLUMN_ADDRESS.getRealName() + " TEXT, " +
                DbConfig.COLUMN_DATE.getRealName() + " TEXT , " +
                DbConfig.COLUMN_LATITUDE.getRealName() + " DOUBLE, " +
                DbConfig.COLUMN_LONGITUDE.getRealName() + " DOUBLE, " +
                DbConfig.COLUMN_TIMESPENT.getRealName() + " DOUBLE " +

                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  " + DbConfig.TABLE_NAME.getRealName());
        onCreate(db);
    }

    public void insertLocationInDb(LocationData locationData) {

        if(!hasDataInDb(locationData)) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DbConfig.COLUMN_DATE.getRealName(), locationData.getDate());
            values.put(DbConfig.COLUMN_TIMESPENT.getRealName(), locationData.getTimeSpent());
            values.put(DbConfig.COLUMN_LONGITUDE.getRealName(), locationData.getLongitude());
            values.put(DbConfig.COLUMN_LATITUDE.getRealName(), locationData.getLatitude());
            values.put(DbConfig.COLUMN_ADDRESS.getRealName(), locationData.getAddress());

            db.insert(DbConfig.TABLE_NAME.getRealName(), null, values);
            db.close();

        } else {
            updateDatabase(locationData.getAddress(),locationData.getTimeSpent(), locationData.getDate());
        }

    }

    public boolean hasDataInDb(LocationData locationData) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+DbConfig.TABLE_NAME.getRealName() +
                " WHERE "+DbConfig.COLUMN_ADDRESS.getRealName() +" = '" + locationData.getAddress() +"'"+
                " AND " + DbConfig.COLUMN_DATE.getRealName() + " = '"+ locationData.getDate() + "'";

        Cursor cursorHandle = db.rawQuery(query, null);
        if(cursorHandle.getCount() > 0 ) {
            cursorHandle.moveToFirst();
            timeSpent = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_TIMESPENT.getRealName()));

            cursorHandle.close();
            return true;
        }
        cursorHandle.close();
        return false;
    }

    public void updateDatabase(String address, Double time, String date) {
        double totalTime = time + timeSpent;
        SQLiteDatabase sq = getWritableDatabase();
        String query = "UPDATE " + DbConfig.TABLE_NAME.getRealName() + " " + "SET " +
                DbConfig.COLUMN_TIMESPENT.getRealName() + " = " + totalTime + " WHERE " +
                DbConfig.COLUMN_ADDRESS.getRealName() + " = " + "'" + address + "'" + " AND " +
                DbConfig.COLUMN_DATE.getRealName() + " = '"+ date + "'";
        sq.execSQL(query);
    }


    public ArrayList<LocationData> readLocationFromDb() {
        ArrayList<LocationData> allLocationInDb = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + DbConfig.TABLE_NAME.getRealName() + " ORDER BY " +
                "  DATE(" +DbConfig.COLUMN_DATE.getRealName()  + ")  ASC";

        Cursor cursorHandle = db.rawQuery(query, null);
        cursorHandle.moveToFirst();
        while (!cursorHandle.isAfterLast()) {
            String address = cursorHandle.getString(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_ADDRESS.getRealName()));
            double latitude = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_LATITUDE.getRealName()));
            double longitude = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_LONGITUDE.getRealName()));
            Double timeSpent = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_TIMESPENT.getRealName()));
            String date = cursorHandle.getString(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_DATE.getRealName()));
            allLocationInDb.add(new LocationData(address, date, latitude, longitude, timeSpent.longValue()));
            cursorHandle.moveToNext();
        }
        cursorHandle.close();
        db.close();
        return allLocationInDb;
    }

    public void deleteFromDatabase(LocationData data) {
        SQLiteDatabase sq = getWritableDatabase();
        String query = "DELETE FROM " + DbConfig.TABLE_NAME.getRealName() + " WHERE " +
                DbConfig.COLUMN_ADDRESS.getRealName() + " = " + "'" + data.getAddress() + "' AND " +
                DbConfig.COLUMN_LONGITUDE.getRealName() + " = " + "'" + data.getLongitude() + "' AND " +
                DbConfig.COLUMN_LATITUDE.getRealName() + " = " + "'" + data.getLatitude() + "' AND " +
                DbConfig.COLUMN_TIMESPENT.getRealName() + " = " + "'" + data.getTimeSpent() + "'";
        sq.execSQL(query);
    }

    public void deleteAllLocationOccurence(LocationData data) {
        SQLiteDatabase sq = getWritableDatabase();
        String query = "DELETE FROM " + DbConfig.TABLE_NAME.getRealName() + " WHERE " +
                DbConfig.COLUMN_ADDRESS.getRealName() + " = " + "'" + data.getAddress() + "'";
        sq.execSQL(query);
    }

    public ArrayList<LocationData> readListBaseOnLocation() {
        HashMap<String,LocationData> addressAndLocationInfo = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + DbConfig.TABLE_NAME.getRealName();
        Cursor cursorHandle = db.rawQuery(query, null);

        cursorHandle.moveToFirst();
        while (!cursorHandle.isAfterLast()) {
            String address = cursorHandle.getString(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_ADDRESS.getRealName()));
            double latitude = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_LATITUDE.getRealName()));
            double longitude = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_LONGITUDE.getRealName()));
            Double timeSpent = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_TIMESPENT.getRealName()));
            String date = cursorHandle.getString(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_DATE.getRealName()));
            if(addressAndLocationInfo.containsKey(address)) {
                LocationData locationData = addressAndLocationInfo.get(address);
                Double previousTimeSpent = locationData.getTimeSpent();
                long newTime = previousTimeSpent.longValue() + timeSpent.longValue();
                locationData.setTimeSpent(newTime);
                addressAndLocationInfo.put(address,locationData);
            } else {
                LocationData locationData = new LocationData(address,date,latitude,
                        longitude,timeSpent.longValue());
                addressAndLocationInfo.put(address,locationData);
            }
            cursorHandle.moveToNext();
        }
        cursorHandle.close();
        db.close();

        ArrayList<LocationData> list = new ArrayList<>();
        for(Map.Entry<String, LocationData> entry : addressAndLocationInfo.entrySet()){
            list.add(entry.getValue());
        }
        return list;
    }

}

