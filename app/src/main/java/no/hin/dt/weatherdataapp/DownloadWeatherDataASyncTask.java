package no.hin.dt.weatherdataapp;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

// DwoloadWeatherDataAsynTaskbruker ASYcTask for og laste ned og hente WeatherData linken fra web.

/**
 * Created by Julia on 06.03.2016.
 *
 *
 */

// AsyncTask that handles the download of the Weatherdata infro from the web whenever it is needed
public class DownloadWeatherDataASyncTask extends AsyncTask<Station, Void , String> {

    private Context context = null;
    private  String WeatherSource = "http://kark.hin.no/~wfa/fag/android/2016/weather/vdata.php?id=";


    public DownloadWeatherDataASyncTask(Context context){
        this.context = context;
    }

    // dowload the Wheater info link from the web,


    @Override
    protected String doInBackground(Station... params) {
          WeatherSource += params[0].getId();

        URL weatherUrl;
        String stringText = "";

        try{
            weatherUrl = new URL(WeatherSource);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(weatherUrl.openStream()));
            String StringBuffer;

            while ((StringBuffer = bufferedReader.readLine())!= null){
                stringText += StringBuffer;
            }

            bufferedReader.close();
          //  Toast toas = Toast.makeText(context, stringText, Toast.LENGTH_LONG);
            //toas.show();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         return stringText;
    }

    protected void onPostExecute(String s){
        ((MainActivity) context).returnedStationData(s);
    }


}
