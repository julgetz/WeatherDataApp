package no.hin.dt.weatherdataapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Julia on 06.03.2016.
 */
public class DataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
   //private Fragment parent;
    private Activity parent;


    public String[] stationColumns = {
            StationTable.STATION_DATA_ID,
            StationTable.STATION_DATA_NAME,
            StationTable.STATION_DATA_POSITION
    };

    private String[] WeatherColumns = {
            WeatherTable.WEATHER_DATA_ID,
            WeatherTable.WEATHER_STATION_NAME,
            WeatherTable.WEATHER_STATION_POSITION,
            WeatherTable.WEATHER_TIMESTAMP,
            WeatherTable.WEATHER_TEMPERATURE,
            WeatherTable.WEATHER_PRESSURE,
            WeatherTable.WEATHER_HUMIDITY,
            WeatherTable.WEATHER_STATION_ID
    };



    //Instansierer et MySQLiteHelper-objekt:

    /*public DataSource(Fragment parentFragment) {
        parent = parentFragment;
        dbHelper = new MySQLiteHelper(parent.getActivity());
    }*/

    public DataSource(MainActivity mainActivity) {
        parent = mainActivity;
        dbHelper = new MySQLiteHelper(parent);
    }


    //"Åpner" databasen, har nå et SQLiteDatabase-objekt:
    public void open() throws SQLException {
        //Dersom databasen ikke eksisterer opprettes den. Ellers returneres en referanse til eksisterende database.
        database = dbHelper.getWritableDatabase();
    }

    //Lukker databasen:

    public void close() {
        dbHelper.close();
    }


    // adds a new station to the database
    public boolean createStationData(Station sta) {
        ContentValues values = new ContentValues();
        values.put(StationTable.STATION_DATA_NAME, sta.getName());
        values.put(StationTable.STATION_DATA_POSITION, sta.getPosition());

        long insertId = database.insert(StationTable.STATION_DATA_TABLE, null, values);
        if (insertId >= 0)
            return true;
        else
            return false;

    }

    // adds  new WeatherData to the database
    public boolean createWeatherData(Weather wea){
        ContentValues values = new ContentValues();
        values.put(WeatherTable.WEATHER_STATION_NAME, wea.getStation_name());
        values.put(WeatherTable.WEATHER_STATION_POSITION,wea.getStation_position());
        values.put(WeatherTable.WEATHER_TIMESTAMP,wea.getTimestamp());
        values.put(WeatherTable.WEATHER_TEMPERATURE, wea.getTemperature());
        values.put(WeatherTable.WEATHER_PRESSURE, wea.getPressure());
        values.put(WeatherTable.WEATHER_HUMIDITY, wea.getHumidity());
        values.put(WeatherTable.WEATHER_STATION_ID,wea.getId());

        long insertId = database.insert(WeatherTable.WEATHER_DATA_TABLE, null, values);
        if(insertId>=0)
            return true;
        else
            return false;

    }


    //Henter alle stasjonene
    public Cursor getAllStations() {
       Cursor cursor = database.query(StationTable.STATION_DATA_TABLE, stationColumns, null, null, null, null, null);
    return cursor;
    }

    //Henter stasjonsværdata:
    public Cursor getStationWeatherDataByStationId(int id) {
        String selectQuery= "select * from WeatherDataTable where station_id = ?";
        String [] selectParams = new String[] { String.valueOf(id) };
        Cursor cursor = database.rawQuery(selectQuery, selectParams);
        return cursor;
    }

    // sletter stasjoner  med  statsjons-id fra databasen

    public void deleteStation(Station station) {
        long id = station.getId();
        database.delete(StationTable.STATION_DATA_TABLE, StationTable.STATION_DATA_ID + "=" + id, null);
    }




    // creats and returns a station object based on the currently selected pointer in referred cursor
    public Station cursorToStation(Cursor cursor){
        Station station = new Station();

        int keyIndex = cursor.getColumnIndexOrThrow(StationTable.STATION_DATA_ID);
        int nameIndex = cursor.getColumnIndexOrThrow(StationTable.STATION_DATA_NAME);
        int positionIndex = cursor.getColumnIndexOrThrow(StationTable.STATION_DATA_POSITION);

        station.setId(cursor.getInt(keyIndex));
        station.setName(cursor.getString(nameIndex));
        station.setPosition(cursor.getString(positionIndex));

        return  station;

    }

    // get Sation name based on id
    public void getStationById(ArrayList<Station> stations,String searchStation ){
        searchStation =  "" + searchStation + "";

        Cursor cursor = database.rawQuery("Select * from" + StationTable.STATION_DATA_TABLE + " where" + StationTable.STATION_DATA_NAME + " like"
        , new String[] {searchStation,searchStation});
        cursor.moveToFirst();
        stations.clear();
        while(!cursor.isAfterLast()){
            stations.add(cursorToStation(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        {

        }

    }

    // gets all Weatherdat for a certain station (in descending order by id)
    public Cursor getAllWeatherData(int _stationID) {

        Cursor cursor = database.query(WeatherTable.WEATHER_DATA_TABLE, WeatherColumns, WeatherTable.WEATHER_STATION_ID
                + "=" + _stationID, null, null, null, WeatherTable.WEATHER_DATA_ID + " DESC");

        return cursor;
    }

    // creats and return a Weatherdata object based on the currently selected pointer in referred cursor
    public Weather cursorToWeather(Cursor cursor) {
        Weather weather = new Weather();

        int keyIndex = cursor.getColumnIndexOrThrow(WeatherTable.WEATHER_DATA_ID);
        int stationNameIndex = cursor.getColumnIndexOrThrow(WeatherTable.WEATHER_STATION_NAME);
        int stationPositionIndex = cursor.getColumnIndexOrThrow(WeatherTable.WEATHER_STATION_POSITION);
        int timestampIndex = cursor.getColumnIndexOrThrow(WeatherTable.WEATHER_TIMESTAMP);
        int temperatureIndex = cursor.getColumnIndexOrThrow(WeatherTable.WEATHER_TEMPERATURE);
        int pressureIndex = cursor.getColumnIndexOrThrow(WeatherTable.WEATHER_PRESSURE);
        int humidityIndex = cursor.getColumnIndexOrThrow(WeatherTable.WEATHER_HUMIDITY);
        int weatherIdIndex = cursor.getColumnIndexOrThrow(WeatherTable.WEATHER_STATION_ID);

        weather.setId(cursor.getInt(keyIndex));
        weather.setStation_name(cursor.getString(stationNameIndex));
        weather.setStation_position(cursor.getString(stationPositionIndex));
        weather.setTimestamp(cursor.getString(timestampIndex));
        weather.setTemperature(cursor.getDouble(temperatureIndex));
        weather.setPressure(cursor.getDouble(pressureIndex));
        weather.setHumidity(cursor.getDouble(humidityIndex));
        weather.setWeatherId(cursor.getInt(weatherIdIndex));

        return weather;


    }


    }















