package no.hin.dt.weatherdataapp;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Julia on 06.03.2016.
 */

// Tabellspesifikt fir stationtabell og opppgraderer seg selv
public class StationTable {

    // //Tabellspesifikt for StationTable:
    public static final String STATION_DATA_TABLE = "StationDataTable";
    public static final String STATION_DATA_ID = "id";
    public static final String STATION_DATA_NAME = "name";
    public static final String STATION_DATA_POSITION = "position";


    // SQL statement for å opprette en ny tabell:
    private static final String STATION_DATA_TABLE_CREATE= "create table "
            + STATION_DATA_TABLE
            + " (" + STATION_DATA_ID + " integer primary key autoincrement, "
            + STATION_DATA_NAME + " text, "
            + STATION_DATA_POSITION + " text" + ");";


    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(STATION_DATA_TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(StationTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        database.execSQL("DROP TABLE IF EXISTS " + STATION_DATA_TABLE);
        onCreate(database);
    }
}










