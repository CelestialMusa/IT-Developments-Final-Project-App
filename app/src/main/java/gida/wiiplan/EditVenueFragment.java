package gida.wiiplan;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditVenueFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    View view;
    TextView txtEditBuilding;
    TextView txtEditVenueRoom;
    TextView txtEditVenueName;
    TextView txtEditVenueCapacity;

    Spinner s;

    Button btnEditVenue;

    CustomDisplayAdapter vad;
    VenueClass vc;

    String venue_id = "";

    public EditVenueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_venue, container, false);

        txtEditBuilding = (TextView) view.findViewById(R.id.txtEditBuilding);
        txtEditVenueRoom = (TextView) view.findViewById(R.id.txtEditRoom);
        txtEditVenueName = (TextView) view.findViewById(R.id.txtEditVenueName);
        txtEditVenueCapacity = (TextView) view.findViewById(R.id.txtEditVenueCapacity);

        s = (Spinner) view.findViewById(R.id.spinnerVenue);

        vad = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);

        retrieveVenues(getActivity(),vad, s);

        s.setAdapter(vad);
        s.setOnItemSelectedListener(this);

        btnEditVenue = (Button) view.findViewById(R.id.btnEditVenue);
        btnEditVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String building = txtEditBuilding.getText().toString();
                String room = txtEditVenueRoom.getText().toString();
                String venue_name = txtEditVenueName.getText().toString();
                String capacity = txtEditVenueCapacity.getText().toString();

                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                backgroundWorker.execute("EditVenue",building,room,venue_name,capacity,vc.getVenue_id());

                vad = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);

                retrieveVenues(getActivity(),vad, s);

                s.setAdapter(vad);
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
            txtEditBuilding.setText(vc.getBuilding());
            txtEditVenueRoom.setText(vc.getRoom());
            txtEditVenueName.setText(vc.getVenue_name());
            txtEditVenueCapacity.setText(vc.getCapacity());
            venue_id = vc.getVenue_id();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
