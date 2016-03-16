package no.hin.dt.weatherdataapp;

import com.google.gson.Gson;

/**
 * Created by Julia on 05.03.2016.
 */
public class Station {

    // primærnøkkeltfelt
    private int id;
    private String name;
    private String position;



    public Station() {
        this.id = 0;
        this.name = "";
        this.position = "";



    }

    public Station(int id, String name, String position){
        this.id = id;
        this.name = name;
        this.position = position;

    }

    public Station(String name, String position){
        this.name = name;
        this.position = position;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String toJSONString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}


