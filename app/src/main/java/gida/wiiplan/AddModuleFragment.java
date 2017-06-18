package gida.wiiplan;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import gida.wiiplan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddModuleFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    View view;

    TextView txtModuleCode, txtModuleName,txtModuleDescript, txtModuleCredits;
    String moduleCode, moduleName,moduleDescript, moduleCredits, moduleLecturer;

    Button btnInsertModule;

    Spinner spnLecturer;

    CustomDisplayAdapter vad;
    User user;

    String varsity_num = "";

    public AddModuleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_module, container, false);

        spnLecturer = (Spinner) view.findViewById(R.id.spinnerAddLecturer);

        vad = new CustomDisplayAdapter(view.getContext(), android.R.layout.simple_spinner_dropdown_item);

        retrieveLecturers(getActivity(),vad, spnLecturer);

        spnLecturer.setAdapter(vad);
        spnLecturer.setOnItemSelectedListener(this);

        txtModuleCode = (TextView) view.findViewById(R.id.txtModuleCode);
        txtModuleName = (TextView) view.findViewById(R.id.txtModuleName);
        txtModuleDescript = (TextView) view.findViewById(R.id.txtModuleDescript);
        txtModuleCredits = (TextView) view.findViewById(R.id.txtModuleCredits);

        btnInsertModule = (Button) view.findViewById(R.id.btnInsertModule);
        btnInsertModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moduleCode = txtModuleCode.getText().toString();
                moduleName = txtModuleName.getText().toString();
                moduleDescript = txtModuleDescript.getText().toString();
                moduleCredits = txtModuleCredits.getText().toString();

                if(moduleCode.length()>0){
                    if(moduleName.length()>0){
                        if(moduleDescript.length()>0){
                            if(moduleCredits.length()>0){
                                try {
                                    String post = URLEncoder.encode("moduleCode","UTF-8")+"="+URLEncoder.encode(moduleCode,"UTF-8")+"&"
                                            +URLEncoder.encode("moduleName","UTF-8")+"="+URLEncoder.encode(moduleName,"UTF-8")+"&"
                                            +URLEncoder.encode("moduleDescript","UTF-8")+"="+URLEncoder.encode(moduleDescript,"UTF-8")+"&"
                                            +URLEncoder.encode("moduleCredits","UTF-8")+"="+URLEncoder.encode(moduleCredits,"UTF-8")+"&"
                                            +URLEncoder.encode("varsity_num","UTF-8")+"="+URLEncoder.encode(varsity_num,"UTF-8")+"&"
                                            +URLEncoder.encode("query_type","UTF-8")+"="+URLEncoder.encode("Insert","UTF-8");

                                    BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                                    backgroundWorker.execute("AddModule",post);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Toast.makeText(getContext(), "Module Credits required", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getContext(), "Module Description Required", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Module Name Required", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Module Code Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    protected void retrieveLecturers(Activity activity,CustomDisplayAdapter customDisplayAdapter,Spinner spinner){
        new RetrieveAsync(activity,customDisplayAdapter,spinner).execute("RetrieveLecturers");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        user = (User) vad.getItem(position);
        varsity_num = user.getVarsity_num();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
