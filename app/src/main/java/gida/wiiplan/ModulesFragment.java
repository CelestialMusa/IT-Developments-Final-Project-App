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
public class ModulesFragment extends Fragment {

    private View v;
    private ListView lv;
    private CustomDisplayAdapter mad;
    private ModuleClass mc;

    private FragmentTransaction fragmentTransaction;

    public ModulesFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_venue, container, false);
        lv = (ListView) v.findViewById(R.id.list);

        mad = new CustomDisplayAdapter(v.getContext(), R.layout.row_layout);
        lv.setAdapter(mad);

        retrieveModules(getActivity(),mad,lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mc = (ModuleClass)  mad.getItem(position);
                //Create View for displaying module Details
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new ModuleViewFragment());
                fragmentTransaction.addToBackStack("VenueViewFragment");
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    public void retrieveModules(Activity activity, CustomDisplayAdapter customDisplayAdapter, ListView listView){
        new RetrieveAsync(activity,customDisplayAdapter,listView).execute("RetrieveModules");
    }
}
