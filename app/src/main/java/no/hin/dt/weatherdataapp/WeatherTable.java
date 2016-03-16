package no.hin.dt.weatherdataapp;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Julia on 06.03.2016.
 */
public class WeatherTable {

    public static final String WEATHER_DATA_TABLE = "WeatherDataTable";
    public static final String WEATHER_DATA_ID= "id";
    public static final String WEATHER_STATION_NAME = "station_name";
    public static final String WEATHER_STATION_POSITION = "station_position";
    public static final String WEATHER_TIMESTAMP = "timestamp";
    public static final String WEATHER_TEMPERATURE ="temperature";
    public static final String WEATHER_PRESSURE = "pressure";
    public static final String WEATHER_HUMIDITY ="humidity";
    public static final String WEATHER_STATION_ID = "station_id";


    // SQL statement for å opprette en ny tabell:
    private static final String WEATHER_DATA_TABLE_CREATE= "create table "
            + WEATHER_DATA_TABLE
            + " (" + WEATHER_DATA_ID + " integer primary key autoincrement, "
            + WEATHER_STATION_NAME + " text, "
            + WEATHER_STATION_POSITION + " text, "
            + WEATHER_TIMESTAMP + " text, "
            + WEATHER_TEMPERATURE + " real, "
            + WEATHER_PRESSURE + " real, "
            + WEATHER_HUMIDITY + " real, "
            + WEATHER_STATION_ID + " integer, "
            + " FOREIGN KEY(" + WEATHER_STATION_ID + ") REFERENCES " + StationTable.STATION_DATA_TABLE
            + "(" + StationTable.STATION_DATA_ID + ") ON DELETE CASCADE);";



    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(WEATHER_DATA_TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(WeatherTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        database.execSQL("DROP TABLE IF EXISTS " + WEATHER_DATA_TABLE);
        onCreate(database);


    }
}








