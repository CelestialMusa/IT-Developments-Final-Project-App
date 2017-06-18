package gida.wiiplan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import gida.wiiplan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddVenueFragment extends Fragment {

    View view;

    TextView txtBuilding,txtRoom,txtVenueName,txtVenueCapacity;
    Button btnInsert;

    public AddVenueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_venue, container, false);

        txtBuilding = (TextView) view.findViewById(R.id.txtBuilding);
        txtRoom = (TextView) view.findViewById(R.id.txtRoom);
        txtVenueName = (TextView) view.findViewById(R.id.txtVenueName);
        txtVenueCapacity = (TextView) view.findViewById(R.id.txtVenueCapacity);


        btnInsert = (Button) view.findViewById(R.id.btnInsertVenue);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String building = txtBuilding.getText().toString();
                String room = txtRoom.getText().toString();
                String vname = txtVenueName.getText().toString();
                String vcap = txtVenueCapacity.getText().toString();

                if(building.length()>0){
                    if(room.length()>0){
                        if(vname.length()>0){
                            if(vcap.length()>0){
                                BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                                backgroundWorker.execute("AddVenue",building+" "+room,building,room,vname,vcap);
                            }else {
                                Toast.makeText(getContext(), "Venue Capacity Required", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "Venue name Required", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), "Room Required", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Building Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
