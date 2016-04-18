package com.andela.motustracker.dbParser;

/**
 * Created by Spykins on 06/04/16.
 */
public enum DbConfig {
    DATABASE_NAME ("location.db"),
    TABLE_NAME ("user_location"),
    COLUMN_ID ("_id"),
    COLUMN_ADDRESS ("address"),
    COLUMN_LONGITUDE("longitude"),
    COLUMN_LATITUDE("latitude"),
    COLUMN_TIMESPENT("duration"),
    COLUMN_DATE("dateCreated");

    DbConfig(String realName){
        this.realName = realName;
    }

    public String getRealName(){
        return realName;
    }
    private final String realName;

}
