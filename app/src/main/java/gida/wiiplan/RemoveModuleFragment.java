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

import java.net.URLEncoder;

import gida.wiiplan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemoveModuleFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    View view;
    Spinner spinner;

    TextView txtRmModule,txtRmName,txtRmCreds,lblLecturer;

    CustomDisplayAdapter customDisplayAdapter;

    Button btnRemove;

    String module_code,module_name,module_descript,module_credits,varsity_num;

    public RemoveModuleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_remove_module, container, false);

        txtRmModule = (TextView) view.findViewById(R.id.txtRmModCod);
        txtRmName = (TextView) view.findViewById(R.id.txtRmModName);
        txtRmCreds = (TextView) view.findViewById(R.id.txtRmModCreds);
        lblLecturer = (TextView) view.findViewById(R.id.lblRmLecturer);

        spinner = (Spinner) view.findViewById(R.id.spnRmModule);

        customDisplayAdapter = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);

        retrieveModules(getActivity(),customDisplayAdapter, spinner);

        spinner.setAdapter(customDisplayAdapter);
        spinner.setOnItemSelectedListener(this);

        btnRemove = (Button) view.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String post = URLEncoder.encode("moduleCode","UTF-8")+"="+URLEncoder.encode(module_code,"UTF-8")+"&"
                            +URLEncoder.encode("moduleName","UTF-8")+"="+URLEncoder.encode(module_name,"UTF-8")+"&"
                            +URLEncoder.encode("moduleDescript","UTF-8")+"="+URLEncoder.encode(module_descript,"UTF-8")+"&"
                            +URLEncoder.encode("moduleCredits","UTF-8")+"="+URLEncoder.encode(module_credits,"UTF-8")+"&"
                            +URLEncoder.encode("varsity_num","UTF-8")+"="+URLEncoder.encode(varsity_num,"UTF-8")+"&"
                            +URLEncoder.encode("query_type","UTF-8")+"="+URLEncoder.encode("Delete","UTF-8");

                    BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                    backgroundWorker.execute("AddModule",post);

                    customDisplayAdapter = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);
                    retrieveModules(getActivity(),customDisplayAdapter, spinner);
                    spinner.setAdapter(customDisplayAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        return view;
    }

    protected void retrieveModules(Activity activity,CustomDisplayAdapter customDisplayAdapter, Spinner spinner){
        new RetrieveAsync(activity,customDisplayAdapter,spinner).execute("RetrieveModules");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ModuleClass moduleClass = (ModuleClass) customDisplayAdapter.getItem(position);
        module_code = moduleClass.getModuleCode();
        module_name = moduleClass.getModuleName();
        module_descript = moduleClass.getModuleDescr();
        module_credits = moduleClass.getModuleCredits();
        varsity_num = moduleClass.getModuleLecturer();

        txtRmModule.setText(module_code);
        txtRmName.setText(module_name);
        txtRmCreds.setText(module_credits);
        lblLecturer.setText(varsity_num);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
