package gida.wiiplan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    TextView txtUsername;
    TextView txtPW;

    Button btnLogin;

    TextView lblRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = (TextView) findViewById((R.id.txtLoginUsername));
        txtPW = (TextView) findViewById((R.id.txtLoginPW));

        btnLogin = (Button) findViewById((R.id.btnLoginSignin));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String username = txtUsername.getText().toString();
                    String password = txtPW.getText().toString();

                    attemptLogin(username,password);
                }catch (Exception ex){

                }
            }
        });

        lblRegister = (TextView) findViewById((R.id.lblReg));
        lblRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    protected void attemptLogin(String username, String pw){
        if(!(username.length()>0)){
            Toast.makeText(LoginActivity.this, "Username is required", Toast.LENGTH_LONG).show();
        }
        else if(!(pw.length()>0)){
            Toast.makeText(LoginActivity.this, "Password is required", Toast.LENGTH_LONG).show();
        }
        else{
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute("Login",username,pw);
        }
    }

    protected void validateLogin(Context context, JSONObject jsonObject,String username, String password){
        try{
            if(username.equals(jsonObject.getString("username")) && password.equals(jsonObject.getString("password"))){
                gotoHome(jsonObject,context);
            }
            else{
                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    protected void gotoHome(JSONObject json, Context context){
        try{
            Intent i = new Intent(context, HomeActivity.class);

            i.putExtra("fname", json.getString("fname"));
            i.putExtra("lname", json.getString("lname"));
            i.putExtra("pic", json.getString("pic"));
            i.putExtra("count", json.getString("count"));
            i.putExtra("cell", json.getString("cell"));
            i.putExtra("type", json.getString("type"));
            i.putExtra("path",json.getString("pic"));

            if(json.getString("type").equals("Student")){
                i.putExtra("course", json.getString("course"));
                context.startActivity(i);
                finish();
            }
            else if(json.getString("type").equals("Lecturer")){
                /**SharedPreferences.Editor prefs = getSharedPreferences("myPreferences",MODE_PRIVATE).edit();
                prefs.putString("fname", json.getString("fname"));
                prefs.putString("lname", json.getString("lname"));
                prefs.putString("pic", json.getString("pic"));
                prefs.putString("count", json.getString("count"));
                prefs.putString("cell", json.getString("cell"));
                prefs.putString("type", json.getString("type"));
                prefs.commit();**/

                context.startActivity(i);
                finish();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}