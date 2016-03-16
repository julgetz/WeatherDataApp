package no.hin.dt.weatherdataapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Julia on 06.03.2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    //Databasespesifikt:
    private static final String DATABASE_NAME = "WeatherData.db";
    private static final int DATABASE_VERSION =22;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Kalles når databasen ikke eksisterer og må opprettes
    @Override
    public void onCreate(SQLiteDatabase database) {
        StationTable.onCreate(database);
        WeatherTable.onCreate(database);
    }

    // Kalles ved behov for oppgradering, dvs. mismatch mellom ny og gammel
    // versjon
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        StationTable.onUpgrade(database, oldVersion, newVersion);
        WeatherTable.onUpgrade(database, oldVersion, newVersion);
    }

    // Enables support for foreign keys
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


}
