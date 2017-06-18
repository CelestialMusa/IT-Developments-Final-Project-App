package gida.wiiplan;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    View v;
    ImageView iv;
    ImageButton btnFb;
    ImageButton btnInsta;
    ImageButton btnLinkedin;
    ImageButton btnGithub;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_about, container, false);
        iv = (ImageView) v.findViewById(R.id.imgDeveloperImage);

        btnGithub  = (ImageButton) v.findViewById(R.id.btnGithub);
        btnLinkedin = (ImageButton) v.findViewById(R.id.btnLinkedin);
        btnFb = (ImageButton) v.findViewById(R.id.btnFacebook);
        btnInsta = (ImageButton) v.findViewById(R.id.btnInstagram);

        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBrowser("https://github.com/CelestialMusa/");
            }
        });

        btnLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBrowser("https://za.linkedin.com/in/zmanyoni");
            }
        });

        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBrowser("https://facebook.com/musawenkosi.zakes");
            }
        });

        btnInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBrowser("https://www.instagram.com/celestial_musa/");
            }
        });

        return v;
    }

    public void launchBrowser(String url){
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
