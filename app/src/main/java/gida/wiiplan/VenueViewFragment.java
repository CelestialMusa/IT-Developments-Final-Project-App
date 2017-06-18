package gida.wiiplan;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gida.wiiplan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VenueViewFragment extends Fragment {

    View v;

    public VenueViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_venue_view, container, false);

        Bundle bundle = getArguments();
        String venue_name = bundle.getString("venue_name");
        String building = bundle.getString("building");
        String room = bundle.getString("room");
        String capacity = bundle.getString("capacity");

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) v.findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle(venue_name);


        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));

        return v;
    }

}
