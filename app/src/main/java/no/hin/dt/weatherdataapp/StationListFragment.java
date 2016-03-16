package no.hin.dt.weatherdataapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
/**
 * Created by Julia on 05.03.2016.
 */




public class StationListFragment extends Fragment implements AdapterView.OnItemClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Station> stationList;
    private ArrayAdapter<String> arrayAdapter;
    private ListView lv;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public interface OnSelectionChanged {
        public void onIndexChanged(Station returnedStation);
    }

    private OnSelectionChanged parentActivity;



    public StationListFragment() {
        // Required empty public constructor
    }

    public void FillStationList(ArrayList<Station> stationList){
        this.stationList = stationList;
        ArrayList<String> stationNames = new ArrayList<>();
        for (Station station: stationList) {
            stationNames.add(station.getName());
        }

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stationNames);

            lv =(ListView)getActivity().findViewById(R.id.lv_Station_Selector);
            lv.setAdapter(arrayAdapter);
            lv.setOnItemClickListener(this);


        }


    public static StationListFragment newInstance(String param1, String param2) {
        StationListFragment fragment = new StationListFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_station_list, container, false);
    }


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
        if (context instanceof OnSelectionChanged) {
            parentActivity = (OnSelectionChanged) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSelectionChanged");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        // her skal man bruke interface metoden til og sende data tilbake til mainactivity
    if (stationList != null && stationList.size() >= position){
        parentActivity.onIndexChanged(stationList.get(position));
    }
//        parentActivity.onIndexChanged(position);


    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
