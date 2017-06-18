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
public class ModuleViewFragment extends Fragment {
    View view;

    public ModuleViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_module_view, container, false);



        return view;
    }

}
