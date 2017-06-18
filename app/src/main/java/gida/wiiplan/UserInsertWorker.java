package gida.wiiplan;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

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
 * Created by Zacharia Manyoni on 2016/11/22.
 */

public class UserInsertWorker extends AsyncTask<String, Void, String> {
    public Context context;
    public AlertDialog alertDialog;

    UserInsertWorker(Context context){
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String lecturerInsert = "http://rkv-lnx3.puk.ac.za/~v24191566/wiiPlan/insert_lecturer.php";
        String studentInsert = "http://rkv-lnx3.puk.ac.za/~v24191566/wiiPlan/insert_student.php";

        String first = params[1];
        String last = params[2];
        String username = params[3];
        String pw = params[4];
        String cell = params[5];

        if(type.equals("StudentInsert")){
            URL url = null;
            try {
                url = new URL(studentInsert);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String course = params[6];
            String path = params[7];

            try {
                String post = URLEncoder.encode("varsity_num","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(first,"UTF-8")+"&"
                        +URLEncoder.encode("lname", "UTF-8")+"="+URLEncoder.encode(last,"UTF-8")+"&"
                        +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pw,"UTF-8")+"&"
                        +URLEncoder.encode("cell","UTF-8")+"="+URLEncoder.encode(cell,"UTF-8")+"&"
                        +URLEncoder.encode("path","UTF-8")+"="+URLEncoder.encode(path,"UTF-8")+"&"
                        +URLEncoder.encode("course","UTF-8")+"="+URLEncoder.encode(course,"UTF-8");

                return databaseConn(post, url);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if(type.equals("LecturerInsert")){
            URL url = null;
            try {
                url = new URL(lecturerInsert);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String path = params[6];

            try {
                String post = URLEncoder.encode("varsity_num","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(first,"UTF-8")+"&"
                        +URLEncoder.encode("lname", "UTF-8")+"="+URLEncoder.encode(last,"UTF-8")+"&"
                        +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pw,"UTF-8")+"&"
                        +URLEncoder.encode("cell","UTF-8")+"="+URLEncoder.encode(cell,"UTF-8")+"&"
                        +URLEncoder.encode("path","UTF-8")+"="+URLEncoder.encode(path,"UTF-8");

                return databaseConn(post, url);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return "No Internet Connection";
    }

    protected String databaseConn(String post, URL url){
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            bufferedWriter.write(post);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            //Response
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

            String result = "";
            String line;

            while((line=bufferedReader.readLine())!=null){
                result +=line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "No Internet Connection";
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login");
    }

    @Override
    protected void onPostExecute(String s) {
        alertDialog.setMessage(s);
        //alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
