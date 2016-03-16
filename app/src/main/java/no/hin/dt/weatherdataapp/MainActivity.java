package no.hin.dt.weatherdataapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

//Demonstrerer bruk av Gson, HttpUrlConnection og POST.
//Laster ned ArrayList<User> som json.
public class MainActivity extends AppCompatActivity implements StationListFragment.OnFragmentInteractionListener, StationListFragment.OnSelectionChanged {

    private final Handler handler = new Handler();
    private StringBuilder serverResponse;
     private StationListFragment stationListFragment;
    private WeatherInfoFragment weatherInfoFragment;
    private DataSource dataSource;
   private CookieManager cookieManager;

    private SimpleCursorAdapter listViewAdapter = null;
    private ListView stationListVeiw;
    private int selectedStation;
    private String selectedStationName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        CookieHandler.setDefault(cookieManager);
        this.downloadUsingWorkerThread();
        /*
        GraphView graph = (GraphView) findViewById(R.id.graphTemperature);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_download:
                this.downloadUsingWorkerThread();
                break;

            case R.id.action_end:
                this.finish();
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

   // denne metoden oppretter databasen ved oppstart av applikasjonen

    @Override
    protected  void onStart(){
        super.onStart();

        // creates a new DataSource-object that we use to interact with the database
        dataSource = new DataSource(this);

        dataSource.open(); // opens database connection

        Cursor categoryCursor = dataSource.getAllStations();
        dataSource.getAllStations();





    }
    // closes database connectio
    @Override
    protected  void onStop(){
        super.onStop();
        dataSource.close();
    }



    // runs when station is selete, shows the weatherdata for the selected station
    @Override
    public void onStationSelected(AdapterView<?> parent, View view, String position, int id){
        ArrayList<Station> tempList = new ArrayList<>();
        Cursor cursor = dataSource.getAllStations(); // gets all stations
        cursor.getColumnIndex(StationTable.STATION_DATA_NAME);

        // goes trough all the stations and creates a list of stations
        while(!cursor.isAfterLast()){
            tempList.add(dataSource.cursorToStation(cursor));
            cursor.getColumnIndex(StationTable.STATION_DATA_NAME);

        }

        cursor.close();




    }














    // lag en metodet start og stop i actionbar som skal laste ned weatherdata info og graf utifra tid


    private void downloadUsingWorkerThread() {
        // Flytter krevede prosessering til egen tråd:
        Thread thread = new Thread(null, runInBackground, "Bakgrunn");
        thread.start();
    }

    // Runnable som inneholder metoden som bakgrunnstråden starter:
    private final Runnable runInBackground = new Runnable() {
        public void run() {
            connectToServer();
        }
    };

    //Laster ned og viser i TextView:
    private void connectToServer() {
        String params = "";
        String myURL = "http://kark.hin.no/~wfa/fag/android/2016/weather/vstations.php";
        //String myURL = "http://10.0.2.2:8080/getTestUsers"; //Fra emulator mot localhost.
        HttpURLConnection conn=null;
        serverResponse = new StringBuilder();
        try {
            URL url = new URL(myURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Connection", "close");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            //Eventuelle parametre:
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(params);
            writer.flush();
            writer.close();
            os.close();
            // Utfører HTTP-forespørsel:
            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                //Leser inputstream...
                readServerResponse(conn.getInputStream(), serverResponse);
            } else {
                serverResponse.append("Ingen/feil svar fra server...");
            }

            //Kun hovedtråden kan oppdatere GUI-komponenter. Vha. handler.post(enMetode) sørger vi for at enMetode kjøres av hovedtråden.
            handler.post(oppdaterGUI); //NB!!

        } catch (SocketTimeoutException ste) {
            ste.printStackTrace();
            serverResponse.append("Socket timeout exception:" + ste.getMessage());
        } catch (MalformedURLException e) {
            Log.d("HTTP-test", "Malformed URL Exception.");
            serverResponse.append(e.getMessage());
        } catch (IOException e) {
            Log.d("HTTP-test", "IO Exception: " + e.getMessage());
            serverResponse.append(e.getMessage());
        } catch (Exception e) {
            Log.d("HTTP-test", "Exception: " + e.getMessage());
            serverResponse.append(e.getMessage());
        } finally {
            if (conn!=null)
                conn.disconnect();
        }

    }

    //Leser svar fra server vha. InputStream-objektet:
    private void readServerResponse(InputStream is, StringBuilder serverResponse) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                serverResponse.append(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Kjores av GUI-tråden:
    protected final Runnable oppdaterGUI = new Runnable() {
        public void run() {
            //Gjør om til User-objekt:
            Gson gson = new Gson();

            Type type = new TypeToken<ArrayList<Station>>(){}.getType();
            ArrayList<Station> stationList = new Gson().fromJson(String.valueOf(serverResponse), type);
/*
            ArrayList<String> stationNames = new ArrayList<>();
            for (Station station: stationList) {
                stationNames.add(station.getName());
            }
*/
        //    sendStationListData(stationNames);
            sendStationListData(stationList);          }
    };

    public void sendStationListData(ArrayList<Station> stationList){

        FragmentManager fm = getSupportFragmentManager();
        //StationListFragment stationListFragment;
        stationListFragment = (StationListFragment) fm.findFragmentById(R.id.fragment_station_list);

        stationListFragment.FillStationList(stationList);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override

    public void onIndexChanged(Station returnedStation) {
     //   Toast toas = Toast.makeText(getApplicationContext(), "clicked: " + returnedStation.getName(), Toast.LENGTH_SHORT);
       // toas.show();
        new  DownloadWeatherDataASyncTask(this).execute(returnedStation);

    }

    public void returnedStationData(String s){

        Gson gson = new Gson();
        Weather weather = gson.fromJson(s, Weather.class);

        // send weather data to both fragments from here
       FragmentManager fm2 = getSupportFragmentManager();
       // WeatherInfoFragment weatherInfoFragment;
        weatherInfoFragment = (WeatherInfoFragment) fm2.findFragmentById(R.id.fragment_weather_info);

        weatherInfoFragment.displayWeatherData(weather);



    }

}


