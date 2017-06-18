package gida.wiiplan;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Zacharia Manyoni on 2016/11/20.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String>{

    Context context;
    AlertDialog alertDialog;

    String username;
    String pwd;

    BackgroundWorker(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://rkv-lnx3.puk.ac.za/~v24191566/wiiPlan/app_login.php";
        String addVenue_url = "http://rkv-lnx3.puk.ac.za/~v24191566/wiiPlan/app_insert_venue.php";
        String moduleURL = "http://rkv-lnx3.puk.ac.za/~v24191566/wiiPlan/app_insert_module.php";

        URL url;

        String post="";

        if(type.equals("Login")){
            try {
                url = new URL(login_url);
                username = params[1];
                pwd = params[2];

                post = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("pwd","UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8");

                return postData(post,url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(type.equals("AddModule")){
            try {
                url = new URL(moduleURL);

                post = params[1];

                return postData(post, url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else{
            try {
                String building = params[1];
                String room = params[2];
                String vname = params[3];
                String vcap = params[4];
                String v_id = params[5];

                post = URLEncoder.encode("building","UTF-8")+"="+URLEncoder.encode(building,"UTF-8")+"&"
                        +URLEncoder.encode("room","UTF-8")+"="+URLEncoder.encode(room,"UTF-8")+"&"
                        +URLEncoder.encode("venue_name","UTF-8")+"="+URLEncoder.encode(vname,"UTF-8")+"&"
                        +URLEncoder.encode("capacity","UTF-8")+"="+URLEncoder.encode(vcap,"UTF-8")+"&"
                        +URLEncoder.encode("venue_id","UTF-8")+"="+URLEncoder.encode(v_id,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(type.equals("AddVenue")){
                try {
                    url = new URL(addVenue_url);

                    post +="&"+URLEncoder.encode("query_type","UTF-8")+"="+URLEncoder.encode("Insert","UTF-8");

                    return postData(post,url);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(type.equals("EditVenue")){
                try {
                    url = new URL(addVenue_url);

                    post +="&"+URLEncoder.encode("query_type","UTF-8")+"="+URLEncoder.encode("Edit","UTF-8");

                    return postData(post,url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(type.equals("RemoveVenue")){
                try {
                    url = new URL(addVenue_url);

                    post +="&"+URLEncoder.encode("query_type","UTF-8")+"="+URLEncoder.encode("RemoveVenue","UTF-8");

                    return postData(post,url);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        return "No internet connection";
    }

    protected String postData(String post, URL url){
        String result = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(post);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            //Response
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

            String line;

            while((line=bufferedReader.readLine())!=null){
                result +=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No internet connection";
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.trim().equals("Login Attempt Failednull")){
            alertDialog.setMessage("Incorrect username or password");
            alertDialog.show();
        }else if(s.trim().equals("No internet connection")){
            alertDialog.setTitle("No Internet Connection");
            alertDialog.setMessage("Please check your internet settings.");
            alertDialog.show();
        }else if(s.trim().equals("Venue Insert successful")){
            alertDialog.setTitle("Data Commit Successful");
            alertDialog.setMessage("Operation Completed with no errors.");
            alertDialog.show();
        }else if(s.trim().equals("Venue Insert attempt failed")){
            alertDialog.setTitle("Data Commit Unsuccessful");
            alertDialog.setMessage("Operation Completed with errors, contact administrator.");
            alertDialog.show();
        }else if(s.trim().equals("Module Insert successful"))
        {
            alertDialog.setTitle("Operation Completed with no errors.");
            alertDialog.setMessage("You can now add the module to your schedule.");
            alertDialog.show();
        }
        else{
            try{
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                LoginActivity l = new LoginActivity();
                l.validateLogin(context, jsonObject,username,pwd);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Attempt");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}