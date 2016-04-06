package com.andela.motustracker.dbParser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.model.LocationData;

import java.util.ArrayList;

/**
 * Created by Spykins on 06/04/16.
 */
public class DbHandler extends SQLiteOpenHelper {

    public DbHandler() {
        super(AppContext.get(), DbConfig.DATABASE_NAME.getRealName(), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DbConfig.TABLE_NAME.getRealName() + " (" +
                DbConfig.COLUMN_ID.getRealName() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbConfig.COLUMN_ADDRESS.getRealName() + " TEXT, " +
                DbConfig.COLUMN_DATE.getRealName() + " DATE , " +
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
        ContentValues values = new ContentValues();
        values.put(DbConfig.COLUMN_DATE.getRealName(), locationData.getDate());
        values.put(DbConfig.COLUMN_TIMESPENT.getRealName(), locationData.getTimeSpent());
        values.put(DbConfig.COLUMN_LONGITUDE.getRealName(), locationData.getLongitude());
        values.put(DbConfig.COLUMN_LATITUDE.getRealName(), locationData.getLatitude());
        values.put(DbConfig.COLUMN_ADDRESS.getRealName(), locationData.getAddress());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DbConfig.TABLE_NAME.getRealName(), null, values);
        db.close();

        Log.d("waleola", "called intertIntoDb..in the DbHandler....");

    }

    public ArrayList<LocationData> readLocationFromDb() {
        ArrayList<LocationData> allLocationInDb = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + DbConfig.TABLE_NAME.getRealName();
        Cursor cursorHandle = db.rawQuery(query, null);
        cursorHandle.moveToFirst();
        while (cursorHandle.moveToNext()) {
            String address = cursorHandle.getString(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_ADDRESS.getRealName()));
            double latitude = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_LATITUDE.getRealName()));
            double longititude = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_LONGITUDE.getRealName()));
            Double timeSpent = cursorHandle.getDouble(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_TIMESPENT.getRealName()));
            String date = cursorHandle.getString(cursorHandle.getColumnIndex(
                    DbConfig.COLUMN_LATITUDE.getRealName()));


            allLocationInDb.add(new LocationData(address, date, latitude, longititude, timeSpent.longValue()));
        }
        cursorHandle.close();
        db.close();
        Log.d("waleola", "called readLocation..in the DbHandler....");
        return allLocationInDb;
    }

    public boolean hasDataBase(Context context) {
        SQLiteDatabase checkDB = null;
        try {
            String db_full_path = context.getDatabasePath(
                    DbConfig.DATABASE_NAME.getRealName()).getPath();

            checkDB = SQLiteDatabase.openDatabase(db_full_path, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return checkDB != null;
    }

    public void deleteFromDatabase(String address, double longitude, double latitude, double timeSpent) {
        SQLiteDatabase sq = getWritableDatabase();
        String query = "DELETE FROM " + DbConfig.TABLE_NAME.getRealName() + " WHERE " +
                DbConfig.COLUMN_ADDRESS.getRealName() + " = " + "'" + address + "' AND " +
                DbConfig.COLUMN_LONGITUDE.getRealName() + " = " + "'" + longitude + "' AND " +
                DbConfig.COLUMN_LATITUDE.getRealName() + " = " + "'" + latitude + "' AND " +
                DbConfig.COLUMN_TIMESPENT.getRealName() + " = " + "'" + timeSpent + "'";
        sq.execSQL(query);
    }

}

