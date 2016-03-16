package no.hin.dt.weatherdataapp;

import com.google.gson.Gson;

/**
 * Created by Julia on 06.03.2016.
 */
public class Weather {
    private int id;
    private String station_name;
    private String station_position;
    private String timestamp;
    private double temperature;
    private double pressure;
    private double humidity;
    private int weatherId;


    public Weather() {
        this.id = 0;
        this.station_name = "";
        this.station_position = "";
        this.timestamp = "";
        this.temperature = 0;
        this.pressure = 0;
        this.humidity = 0;


    }


    public Weather(String station_name, String station_position, String timestamp, double temperature, double pressure, double humidity ,int weatherId) {

        this.station_name = station_name;
        this.station_position = station_position;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weatherId = weatherId;


    }

    public Weather(String station_name, String station_position, String timestamp, double temperature, double pressure, double humidity) {
        this.station_name = station_name;
        this.station_position = station_position;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getStation_position() {
        return station_position;
    }

    public void setStation_position(String station_position) {
        this.station_position = station_position;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String toJSONString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}