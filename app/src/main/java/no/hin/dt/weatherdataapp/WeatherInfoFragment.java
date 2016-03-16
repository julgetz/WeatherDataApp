package no.hin.dt.weatherdataapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class WeatherInfoFragment extends Fragment {

    private ArrayList<Weather> weathers = new ArrayList<>();
    private TextView textView;
    private Weather weather;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public WeatherInfoFragment() {
        // Required empty public constructor
    }
/*
    public interface WeatherInfoShown {
        public void onShowIndex(Weather retunrWeather);

    }
*/
    public ArrayList<Weather> getWeatherInfo() {
        Gson gson = new Gson();
        String jsonOutput = "http://kark.hin.no/~wfa/fag/android/2016/weather/vdata.php?id=2";
        Type listType = new TypeToken<List<Weather>>() {
        }.getType();
        List<Weather> weather = (List<Weather>) gson.fromJson(jsonOutput, listType);
        return getWeatherInfo();
    }

    public void displayWeatherData(Weather weather){

        String str = "WEATHER INFO" + "\n\n"+
                "STATION:" + "\n" + weather.getStation_name() + "\n" +
                "POSITION: " + "\n" + weather.getStation_position() + "\n" +
                "TIMESTAMP: " + "\n"  + weather.getTimestamp() + "\n" +
                "TEMPERATURE: " + "\n" +  weather.getTemperature() + "\n" +
                "PRESSURE: " + "\n" + weather.getPressure() + "\n" +
                "HUMIDITY: " + "\n" + weather.getHumidity() + "%";
        textView.setText(str);
     //   tv = new TextView(getActivity());
       // tv =(TextView)getView().findViewById(R.id.tv_weather_data);
        //tv.setText(weather.getTemperature() + " - " + weather.getStation_name());


    }




    public static WeatherInfoFragment newInstance(String param1, String param2) {
        WeatherInfoFragment fragment = new WeatherInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         textView = new TextView(getActivity());
      //  textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }
/*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
    */
}
