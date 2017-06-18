package gida.wiiplan;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by NWUUser on 2016/12/06.
 */

public class RetrieveAsync extends AsyncTask<String, Void, String> {

    Activity activity;
    private CustomDisplayAdapter customDisplayAdapter;
    ListView listView;
    Spinner spinner;

    int imgRes = R.drawable.adduser;

    String type;

    RetrieveAsync(Activity activity, CustomDisplayAdapter customDisplayAdapter, ListView listView){
        this.activity = activity;
        this.customDisplayAdapter = customDisplayAdapter;
        this.listView = listView;
    }

    RetrieveAsync(Activity activity, CustomDisplayAdapter customDisplayAdapter, Spinner spinner){
        this.activity = activity;
        this.customDisplayAdapter = customDisplayAdapter;
        this.spinner = spinner;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        type = params[0];

        if(type.equals("RetrieveVenues")){
            try {
                url = new URL("http://rkv-lnx3.puk.ac.za/~v24191566/wiiPlan/get_venue.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else if(type.equals("RetrieveModules")){
            try {
                url = new URL("http://rkv-lnx3.puk.ac.za/~v24191566/wiiPlan/get_module.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else if(type.equals("RetrieveLecturers")){
            try {
                url = new URL("http://rkv-lnx3.puk.ac.za/~v24191566/wiiPlan/getLecturer.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return retrieve(url);
    }

    protected String retrieve(URL url){
        String result = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            String line = "";

            while((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line+"\n");
            }
            inputStream.close();
            return result = stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No Internet Connection";
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            JSONObject jsonObject;

            for(int i = 0; i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);

                if(type.equals("RetrieveVenues")){
                    VenueClass venueClass = new VenueClass(imgRes,jsonObject.getString("venue_id"),jsonObject.getString("building"),jsonObject.getString("room"),jsonObject.getString("venue_name"),jsonObject.getString("capacity"));

                    customDisplayAdapter.add(venueClass);
                }else if(type.equals("RetrieveModules")){
                    ModuleClass moduleClass = new ModuleClass(imgRes,jsonObject.getString("module_descript"),jsonObject.getString("varsity_num"),jsonObject.getString("module_creds"),jsonObject.getString("module_name"),jsonObject.getString("module_code"));

                    customDisplayAdapter.add(moduleClass);
                }else if(type.equals("RetrieveLecturers")){
                    User user = new User(jsonObject.getString("varsity_num"),jsonObject.getString("fname"),jsonObject.getString("lname"));

                    customDisplayAdapter.add(user);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}