package gida.wiiplan;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import gida.wiiplan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemoveVenueFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    View view;

    TextView txtRemoveBuilding,txtRemoveRoom;
    Spinner spinnerRemoveVenue;

    CustomDisplayAdapter vad;

    Button btnRemoveVenue;

    VenueClass vc;

    public RemoveVenueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_remove_venue, container, false);



        txtRemoveBuilding = (TextView) view.findViewById(R.id.txtRemoveBuilding);
        txtRemoveRoom = (TextView) view.findViewById(R.id.txtRemoveRoom);


        spinnerRemoveVenue = (Spinner) view.findViewById(R.id.spinnerRemoveVenue);

        vad = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);

        retrieveVenues(getActivity(),vad, spinnerRemoveVenue);

        spinnerRemoveVenue.setAdapter(vad);
        spinnerRemoveVenue.setOnItemSelectedListener(this);

        btnRemoveVenue = (Button) view.findViewById(R.id.btnRemoveVenue);
        btnRemoveVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String building = txtRemoveBuilding.getText().toString();
                String room = txtRemoveRoom.getText().toString();

                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                backgroundWorker.execute("RemoveVenue",building,room,vc.getVenue_name(),vc.getCapacity(),vc.getVenue_id());

                vad = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);

                retrieveVenues(getActivity(),vad, spinnerRemoveVenue);

                spinnerRemoveVenue.setAdapter(vad);
            }
        });

        return view;
    }

    protected void retrieveVenues(Activity activity, CustomDisplayAdapter customDisplayAdapter, Spinner spinner){
        new RetrieveAsync(activity,customDisplayAdapter,spinner).execute("RetrieveVenues");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vc = (VenueClass) vad.getItem(position);
        txtRemoveBuilding.setText(vc.getBuilding());
        txtRemoveRoom.setText(vc.getRoom());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
