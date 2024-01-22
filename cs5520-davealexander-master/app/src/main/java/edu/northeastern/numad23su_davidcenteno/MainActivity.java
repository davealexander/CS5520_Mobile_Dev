package edu.northeastern.numad23su_davidcenteno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnAboutMe;
    Button btnClicky;
    Button btnLinkCollector;
    Button btnPrime;

    Button btnLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAboutMe = findViewById(R.id.about_me_button);
        btnClicky = findViewById(R.id.clickyClicky);
        btnLinkCollector = findViewById(R.id.linkCollector);
        btnPrime = findViewById(R.id.threadActBtn);
        btnLocation = findViewById(R.id.locationBtn);

        btnAboutMe.setOnClickListener(v -> aboutMeActivity());
        btnClicky.setOnClickListener((v -> clickyActivity()));
        btnLinkCollector.setOnClickListener((v -> linkCollectorActivity()));
        btnPrime.setOnClickListener((v -> primeActivity()));
        btnLocation.setOnClickListener((V -> locationActivity()));

    }
    public void clickyActivity(){
        Intent intent = new Intent(this, ClickyClicky.class);
        startActivity(intent);
    }

    public void linkCollectorActivity(){
        Intent linkCollectorAct = new Intent(this, LinkCollector.class);
        startActivity(linkCollectorAct);
    }

    public void aboutMeActivity(){
        Intent aboutMeAct = new Intent(this, AboutMe.class);
        startActivity(aboutMeAct);
    }

    public void primeActivity(){
        Intent primeAct = new Intent(this, FindPrimeActivity.class);
        startActivity((primeAct));
    }

    public void locationActivity(){
        Intent locationAct = new Intent(this, LocationActivity.class);
        startActivity((locationAct));
    }

}