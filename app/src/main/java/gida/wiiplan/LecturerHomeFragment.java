package gida.wiiplan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LecturerHomeFragment extends Fragment {
    View view;
    ImageView addModule,editModule,removeModule,addVenue,editVenue,removeVenue;
    TextView txtUser;

    private FragmentTransaction fragmentTransaction;
    Bundle bundle;
    public LecturerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_lecturer_home, container, false);

        bundle = getArguments();

        txtUser  = (TextView) view.findViewById(R.id.logged_user);
        txtUser.setText(bundle.getString("user"));

        addModule = (ImageView) view.findViewById(R.id.addModule);
        addModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new AddModuleFragment());
                fragmentTransaction.commit();
            }
        });

        editModule = (ImageView) view.findViewById(R.id.editModule);
        editModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new EditModuleFragment());
                fragmentTransaction.commit();
            }
        });

        removeModule = (ImageView) view.findViewById(R.id.removeModule);
        removeModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new RemoveModuleFragment());
                fragmentTransaction.commit();
            }
        });

        addVenue = (ImageView) view.findViewById(R.id.addVenue);
        addVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new AddVenueFragment());
                fragmentTransaction.commit();
            }
        });

        editVenue = (ImageView) view.findViewById(R.id.editVenue);
        editVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new EditVenueFragment());
                fragmentTransaction.commit();
            }
        });
        removeVenue = (ImageView) view.findViewById(R.id.removeVenue);
        removeVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new RemoveVenueFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
