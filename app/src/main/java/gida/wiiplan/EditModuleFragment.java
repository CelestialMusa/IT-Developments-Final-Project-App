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
import android.widget.Toast;

import java.net.URLEncoder;

import gida.wiiplan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditModuleFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    View view;

    Spinner spinnerModule,spnLecturer;

    Button btnMod;

    TextView txtEditModuleCode,txtEditModuleName,txtEditModuleCredits,txtEditModuleDescript;

    CustomDisplayAdapter mad;
    CustomDisplayAdapter lad;

    ModuleClass mc;

    String varsity_num;

    public EditModuleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_module, container, false);

        txtEditModuleCode = (TextView) view.findViewById(R.id.txtModCode);
        txtEditModuleName = (TextView) view.findViewById(R.id.txtModName);
        txtEditModuleCredits = (TextView) view.findViewById(R.id.txtModCredits);
        txtEditModuleDescript = (TextView) view.findViewById(R.id.txtModDescript);

        spinnerModule = (Spinner) view.findViewById(R.id.spinnerModule);

        mad = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);

        retrieveModules(getActivity(),mad, spinnerModule);

        spinnerModule.setAdapter(mad);
        spinnerModule.setOnItemSelectedListener(this);

        spnLecturer = (Spinner) view.findViewById(R.id.spinnerEditLecturer);

        lad = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);

        retrieveLecturers(getActivity(),lad, spnLecturer);

        spnLecturer.setAdapter(lad);
        spnLecturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                User user = (User) lad.getItem(position);
                varsity_num = user.getVarsity_num();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnMod = (Button) view.findViewById(R.id.btnMod);
        btnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String module_code = txtEditModuleCode.getText().toString();
                String module_name = txtEditModuleName.getText().toString();
                String module_credits = txtEditModuleCredits.getText().toString();
                String module_descript = txtEditModuleDescript.getText().toString();

                if(module_code.length()>0){
                    if(module_name.length()>0){
                        if(module_descript.length()>0){
                            if (module_credits.length() > 0) {
                                try {
                                    String post = URLEncoder.encode("moduleCode","UTF-8")+"="+URLEncoder.encode(module_code,"UTF-8")+"&"
                                            +URLEncoder.encode("moduleName","UTF-8")+"="+URLEncoder.encode(module_name,"UTF-8")+"&"
                                            +URLEncoder.encode("moduleDescript","UTF-8")+"="+URLEncoder.encode(module_descript,"UTF-8")+"&"
                                            +URLEncoder.encode("moduleCredits","UTF-8")+"="+URLEncoder.encode(module_credits,"UTF-8")+"&"
                                            +URLEncoder.encode("varsity_num","UTF-8")+"="+URLEncoder.encode(varsity_num,"UTF-8")+"&"
                                            +URLEncoder.encode("query_type","UTF-8")+"="+URLEncoder.encode("Edit","UTF-8");

                                    BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                                    backgroundWorker.execute("AddModule",post);

                                    mad = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);
                                    retrieveModules(getActivity(),mad, spinnerModule);
                                    spinnerModule.setAdapter(mad);


                                    lad = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);
                                    retrieveLecturers(getActivity(),lad, spnLecturer);
                                    spnLecturer.setAdapter(lad);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                Toast.makeText(getContext(), "Module Credits Required.", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(getContext(), "Module Description Required", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "Module Name Required", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Module Code Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    protected void retrieveModules(Activity activity, CustomDisplayAdapter customDisplayAdapter,Spinner spinner){
        new RetrieveAsync(activity,customDisplayAdapter,spinner).execute("RetrieveModules");
    }

    protected void retrieveLecturers(Activity activity,CustomDisplayAdapter customDisplayAdapter,Spinner spinner){
        new RetrieveAsync(activity,customDisplayAdapter,spinner).execute("RetrieveLecturers");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ModuleClass obj = (ModuleClass) mad.getItem(position);
        txtEditModuleCode.setText(obj.getModuleCode());
        txtEditModuleName.setText(obj.getModuleName());
        txtEditModuleCredits.setText(obj.getModuleCredits());
        txtEditModuleDescript.setText(obj.getModuleDescr());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
