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
        dbHandler = new DbHandler();
        return dbHandler.readLocationFromDb();
    }

    public ArrayList<LocationData> readListBaseOnLocation() {
        return dbHandler.readListBaseOnLocation();
    }

    public void deleteAllLocationOccurence(LocationData data) {
        dbHandler.deleteAllLocationOccurence(data);
    }

    public void deleteInstanceOfLocation(LocationData data) {
        dbHandler.deleteFromDatabase(data);
    }

}
