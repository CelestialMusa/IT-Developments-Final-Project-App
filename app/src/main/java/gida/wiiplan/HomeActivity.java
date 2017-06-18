package gida.wiiplan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerL;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private ImageButton imgMenu;
    private NavigationView navigationView;

    private FragmentTransaction fragmentTransaction;

    private TextView txtCount,txtCourse,txtUser;

    String fname="";
    String lname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById((R.id.nav_actionbar));
        setSupportActionBar(mToolbar);

        mDrawerL = (DrawerLayout) findViewById(R.id.drawer_lay);

        mToggle = new ActionBarDrawerToggle(this,mDrawerL,R.string.open,R.string.close);
        mDrawerL.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Wii Planner");

        /**Image Menu Controller**/
        //imgMenu = (ImageButton) findViewById(R.id.imgMenu);
        /** End Image Menu Controller**/

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return changeFragment(item);
            }
        });

        fname = getIntent().getStringExtra("fname");
        lname = getIntent().getStringExtra("lname");
        String count = getIntent().getStringExtra("count");


        String course;

        if(getIntent().getStringExtra("type").equals("Student")){
            course = getIntent().getStringExtra("course");
        }else{
            course = "Lecturer";
        }


        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.txtUser);
        TextView nav_course = (TextView) hView.findViewById(R.id.txtCourse);
        TextView nav_count = (TextView) hView.findViewById(R.id.txtCount);
        final ImageView imgProfile = (ImageView) hView.findViewById(R.id.imgProfile);

        nav_user.setText(fname+" "+lname);
        nav_course.setText(course);
        nav_count.setText(count+" Classes Today");

        File imgFile = new  File(getIntent().getStringExtra("path"));

        Uri uri = Uri.fromFile(imgFile);

        Picasso.with(this).load(uri)
                .resize(96, 96)
                .into(imgProfile, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        imgProfile.setImageDrawable(imageDrawable);
                    }
                    @Override
                    public void onError() {
                        imgProfile.setImageResource(R.drawable.pic);
                    }
                });

        //SharedPreferences prefs = getSharedPreferences("myPreferences", MODE_PRIVATE);
        //String user = prefs.getString("fname","John")+" "+prefs.getString("lname","Doe");
        //nav_user.setText(fname+" "+lname);
        //String a = txtUser.getText().toString();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        LecturerHomeFragment lecturerFragment = new LecturerHomeFragment();
        Bundle bundle = new Bundle();
        lecturerFragment.setArguments(bundle);
        bundle.putString("user",fname+" "+lname);
        fragmentTransaction.add(R.id.main_container, lecturerFragment);
        fragmentTransaction.commit();
        mDrawerL.openDrawer(navigationView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public boolean changeFragment(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
            case R.id.nav_home:
                getSupportActionBar().setTitle("Home");
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                LecturerHomeFragment lecturerFragment = new LecturerHomeFragment();
                Bundle bundle = new Bundle();
                lecturerFragment.setArguments(bundle);
                bundle.putString("user",fname+" "+lname);
                fragmentTransaction.replace(R.id.main_container, lecturerFragment);
                fragmentTransaction.commit();
                item.setChecked(true);
                mDrawerL.closeDrawers();
                break;
            case R.id.nav_venues:
                getSupportActionBar().setTitle("Venues");
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new VenueFragment());
                //fragmentTransaction.addToBackStack("Venues");

                fragmentTransaction.commit();
                item.setChecked(true);
                mDrawerL.closeDrawers();
                break;
            case R.id.nav_modules:
                getSupportActionBar().setTitle("Modules");
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new ModulesFragment());
                fragmentTransaction.commit();
                item.setChecked(true);
                mDrawerL.closeDrawers();
                break;
            case R.id.nav_schedule:
                getSupportActionBar().setTitle("My Schedule");
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new ScheduleFragment());
                fragmentTransaction.commit();
                item.setChecked(true);
                mDrawerL.closeDrawers();
                break;
            case R.id.nav_devblog:
                getSupportActionBar().setTitle("Development Blog");
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new DevblogFragment());
                fragmentTransaction.commit();
                item.setChecked(true);
                mDrawerL.closeDrawers();
                break;
            case R.id.nav_about:
                getSupportActionBar().setTitle("About");
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new AboutFragment());
                fragmentTransaction.commit();
                item.setChecked(true);
                mDrawerL.closeDrawers();
                break;
            case R.id.nav_logout:
                Intent i = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return false;
    }
}