package gida.wiiplan;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class VenueFragment extends Fragment{

    View v;
    ListView lv;
    CustomDisplayAdapter vad;
    VenueClass vc;

    private FragmentTransaction fragmentTransaction;

    public VenueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_venue, container, false);
        lv = (ListView) v.findViewById(R.id.list);

        vad = new CustomDisplayAdapter(v.getContext(), R.layout.row_layout);
        lv.setAdapter(vad);

        retrieveVenues(getActivity(), vad, lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vc = (VenueClass)  vad.getItem(position);
                //Create View for displaying Venue Details
                fragmentTransaction = getFragmentManager().beginTransaction();
                VenueViewFragment lecturerFragment = new VenueViewFragment();

                Bundle bundle = new Bundle();
                lecturerFragment.setArguments(bundle);
                bundle.putString("venue_name",vc.getVenue_name());
                bundle.putString("building",vc.getBuilding());
                bundle.putString("room",vc.getRoom());
                bundle.putString("capacity",vc.getCapacity());

                fragmentTransaction.replace(R.id.main_container,lecturerFragment);
                fragmentTransaction.addToBackStack("VenueViewFragment");
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    public void retrieveVenues(Activity activity, CustomDisplayAdapter customDisplayAdapter, ListView listView){
        new RetrieveAsync(activity,customDisplayAdapter,listView).execute("RetrieveVenues");
    }
}
