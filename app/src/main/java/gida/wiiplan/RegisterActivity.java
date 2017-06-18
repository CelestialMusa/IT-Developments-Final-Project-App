package gida.wiiplan;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;


public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton imgBtnBack;
    Button btnSignup;
    TextView txtFirst;
    TextView txtLast;
    TextView txtUsername;
    TextView txtPw;
    TextView txtConfirmPass;
    TextView txtCell;
    TextView txtAdminPass;
    TextView txtCourse;

    TextView lblUser;

    ImageView iv;

    String userTask = "";

    Spinner s;

    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lblUser = (TextView) findViewById(R.id.lblUser);

        txtCourse = (TextView) findViewById(R.id.txtCourse);

        txtAdminPass = (TextView) findViewById(R.id.txtAdminPass);
        iv  = (ImageView) findViewById(R.id.ivProfile);

        imgBtnBack = (ImageButton) findViewById((R.id.imgBack));
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        /**Start Spinner**/
        String[] array_spinner = new String[2];
        array_spinner[0] = "Student";
        array_spinner[1] = "Lecturer";

        s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
        /**End Spinner**/

        /**Button Signup**/
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtFirst = (TextView) findViewById(R.id.txtFirst);
                txtLast = (TextView) findViewById(R.id.txtLast);
                txtUsername = (TextView) findViewById(R.id.txtUsername);
                txtPw = (TextView) findViewById(R.id.txtPw);
                txtConfirmPass = (TextView) findViewById(R.id.txtRepeat);
                txtCell = (TextView) findViewById(R.id.txtCell);

                String first, last, username, pw, cPw,cell,adminPw,course;

                try{
                    first = txtFirst.getText().toString();
                    last = txtLast.getText().toString();
                    username = txtUsername.getText().toString();
                    pw = txtPw.getText().toString();
                    cPw = txtConfirmPass.getText().toString();
                    cell = txtCell.getText().toString();
                    course = txtCourse.getText().toString();


                    UserInsertWorker userInsertWorker;

                    if(first.length()>0){
                        if(last.length()>0){
                            if(username.length()>0){
                                if(pw.length()>0){
                                    if(cPw.length()>0){
                                        if(cell.length()>0){
                                            if(pw.equals(cPw)){
                                                if(s.getSelectedItem().toString().equals("Student")){
                                                    if(course.length()>0){

                                                        userInsertWorker = new UserInsertWorker(RegisterActivity.this);
                                                        userInsertWorker.execute("StudentInsert",first,last,username,pw,cell,course,path);

                                                        gotoHome();
                                                        Toast.makeText(RegisterActivity.this, "Registration successful, you can now Login", Toast.LENGTH_SHORT).show();

                                                    }else{
                                                        Toast.makeText(RegisterActivity.this, "Course of Study required.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                else{
                                                    adminPw = txtAdminPass.getText().toString();
                                                    if(adminPw.length()>0){

                                                        userInsertWorker = new UserInsertWorker(RegisterActivity.this);
                                                        userInsertWorker.execute("LecturerInsert",first,last,username,pw,cell,path);

                                                        gotoHome();
                                                        Toast.makeText(RegisterActivity.this, "Registration successful, you can now Login", Toast.LENGTH_SHORT).show();

                                                    }
                                                    else{
                                                        Toast.makeText(RegisterActivity.this, "Administrator password required", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                            else{
                                                Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this, "Cell Number cannot be empty.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Please confirm Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Last name cannot be empty.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "First name cannot be empty.", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){

                }
            }
        });
        /**End Button Signup**/

        /**Set Profile Picture**/
        ImageButton imgProPic = (ImageButton) findViewById(R.id.imgProfilePic);
        imgProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });
    }


    public void gotoHome(){
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                txtAdminPass.animate().translationXBy(-1000).start();
                txtCourse.setVisibility(v.VISIBLE);
                break;
            case 1:
                txtCourse.setVisibility(v.INVISIBLE);
                txtAdminPass.animate().translationXBy(1000).start();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void choosePic(){
        final CharSequence [] diagOps = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Choose Image");
        builder.setItems(diagOps,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface diag, int item){
                boolean r = Utility.checkPermission(RegisterActivity.this);

                if(diagOps[item].equals("Take Photo")){
                    userTask = "Take Photo";
                    if(r){
                        cameraIntent();
                    }
                }
                else if(diagOps[item].equals("Choose from Library")){
                    userTask = "Choose from Library";
                    if(r){
                        galleryIntent();
                    }
                }
                else if(diagOps[item].equals("Cancel")){
                    diag.dismiss();
                }
            }
        });
        builder.show();
    }

    public void cameraIntent(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 0);
    }

    public void galleryIntent(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Image"),1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String [] permissions, int[] grantResults){
        switch(requestCode){
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if(userTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userTask.equals("Choose from Library"))
                        galleryIntent();
                    else
                        Toast.makeText(RegisterActivity.this, "Failed to obtain file permissions", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode == 1)
                selectFromG(data);
            else if(requestCode == 0)
                captureImage(data);
        }

            path = getPath(data.getData());
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }

    @SuppressWarnings("deprecation")
    private void selectFromG(Intent data){
        Bitmap bm = null;
        if(data!=null){
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            }
            catch(IOException ioe){

            }
        }
        iv.setImageBitmap(bm);
    }

    private void captureImage(Intent data){
        Bitmap thumb = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumb.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
        FileOutputStream fs;
        try{
            destination.createNewFile();
            fs = new FileOutputStream(destination);
            fs.write(bytes.toByteArray());
            fs.close();
        }
        catch (FileNotFoundException ex){

        }
        catch (IOException ex){

        }
        iv.setImageBitmap(thumb);
    }
    /**End Set Profile Picture**/
}
