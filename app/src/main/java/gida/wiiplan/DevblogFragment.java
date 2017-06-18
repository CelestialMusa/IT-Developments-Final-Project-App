package gida.wiiplan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class DevblogFragment extends Fragment {

    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4,expandableLayout5,expandableLayout6,expandableLayout7,expandableLayout8;
    Button expandableButton1,expandableButton2,expandableButton3,expandableButton4,expandableButton5,expandableButton6,expandableButton7,expandableButton8;

    View v;


    public DevblogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_devblog, container, false);


        expandableButton1 = (Button) v.findViewById(R.id.expandableButton1);
        expandableButton1.setOnClickListener(onClick((ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout1),v));

        expandableButton2 = (Button) v.findViewById(R.id.expandableButton2);
        expandableButton2.setOnClickListener(onClick((ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout2),v));

        expandableButton3 = (Button) v.findViewById(R.id.expandableButton3);
        expandableButton3.setOnClickListener(onClick((ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout3),v));

        expandableButton4 = (Button) v.findViewById(R.id.expandableButton4);
        expandableButton4.setOnClickListener(onClick((ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout4),v));

        expandableButton5 = (Button) v.findViewById(R.id.expandableButton5);
        expandableButton5.setOnClickListener(onClick((ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout5),v));

        expandableButton6 = (Button) v.findViewById(R.id.expandableButton6);
        expandableButton6.setOnClickListener(onClick((ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout6),v));

        expandableButton7 = (Button) v.findViewById(R.id.expandableButton7);
        expandableButton7.setOnClickListener(onClick((ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout7),v));

        expandableButton8 = (Button) v.findViewById(R.id.expandableButton8);
        expandableButton8.setOnClickListener(onClick((ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout8),v));

        return v;
    }

    public View.OnClickListener onClick(final ExpandableRelativeLayout expandableLayout, final View v){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout.toggle();
            }
        };
    }
}
