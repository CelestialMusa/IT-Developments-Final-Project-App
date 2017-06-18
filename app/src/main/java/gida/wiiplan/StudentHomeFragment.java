package gida.wiiplan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gida.wiiplan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentHomeFragment extends Fragment {

    View view;

    public StudentHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_student_home, container, false);

        return view;
    }

}
