package com.andela.motustracker.manager;

import com.andela.motustracker.dbParser.DbHandler;
import com.andela.motustracker.model.LocationData;

import java.util.ArrayList;

/**
 * Created by Spykins on 06/04/16.
 */
public class DbManager {
    private static DbHandler dbHandler;

    public DbManager() {
        dbHandler = new DbHandler();

    }

    public void insertIntoDb(LocationData locationData) {
        dbHandler.insertLocationInDb(locationData);
    }

    public ArrayList<LocationData> readDataFromDb() {
       return dbHandler.readLocationFromDb();
    }
}
