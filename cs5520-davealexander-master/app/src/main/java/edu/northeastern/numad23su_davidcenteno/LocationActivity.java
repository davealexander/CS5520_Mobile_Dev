package edu.northeastern.numad23su_davidcenteno;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
//import android.location.LocationRequest;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

public class LocationActivity extends AppCompatActivity {
    private TextView tv_longVal, tv_latVal,  tv_dtValue;
    private Button clearDistanceBtn;
    private static final int RECORD_REQUEST_CODE = 101;

    FusedLocationProviderClient fusedLocationProviderClient;

    //Google location request
    LocationRequest locationRequest;

    private LocationCallback locationCallBack;

    Location pastLocation = new Location("");
    Location newLocation = new Location("");

    float[] locDistResults = new float[10];

    private float currentDistance;
    Boolean isFirstUpdate = true;



    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //Initalize variables
        pastLocation.setLongitude(0);
        pastLocation.setLatitude(0);
        newLocation.setLatitude(0);
        newLocation.setLongitude(0);

        //Ask user for permission
        getUserPermissionLocation();

        //Location Services Client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Reference TextViews
        tv_longVal = findViewById(R.id.longitudeValueTV);
        tv_latVal = findViewById(R.id.latitudeValueTV);
        tv_dtValue = findViewById(R.id.distanceValueTV);
        clearDistanceBtn = findViewById(R.id.clearDistBtn);

        //Save values on rotate
        if (savedInstanceState != null){
            isFirstUpdate = false;
            locDistResults = savedInstanceState.getFloatArray("distanceArray");
            currentDistance = savedInstanceState.getFloat("distanceValue");
            tv_dtValue.setText(String.valueOf(currentDistance) + " meters");
        }

        //Clear distance value
        clearDistanceBtn.setOnClickListener((v -> clearDistance()));

        //Start initial location services
        startLocationService();

        //Create locationRequest settings
         locationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(15000);

         //Callback for requestLocationUpdates
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                updateDistance(locationResult.getLastLocation());
                updateCoordinates(locationResult.getLastLocation());
            }
        };

        //Creates and starts the requestLocationUpdates
        createLocationRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createLocationRequest();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloatArray("distanceArray", locDistResults);
        outState.putFloat("distanceValue", currentDistance);


    }

    private void updateCoordinates(Location location){
        tv_latVal.setText(String.valueOf(location.getLatitude()));
        tv_longVal.setText(String.valueOf(location.getLongitude()));
        if (location == null){
            pastLocation.setLatitude(0);
            pastLocation.setLongitude(0);
        } else {
            pastLocation.setLatitude(location.getLatitude());
            pastLocation.setLongitude(location.getLongitude());
        }
    }

    //Updates the distance value
    private void updateDistance(Location location){
        //First update ignore calculation
        if (isFirstUpdate){
            Log.d("entered first update", "entered first update");
            currentDistance = 0;
            isFirstUpdate = false;
            return;
        }
        else{
            Log.d("Entered else condition", "Else location distanceBetween...");
            Location.distanceBetween(pastLocation.getLatitude(),pastLocation.getLongitude(),location.getLatitude(),location.getLongitude(),locDistResults);
            currentDistance = currentDistance + Math.round(locDistResults[0] * 100) / 100;
        }

        tv_dtValue.setText(String.valueOf(currentDistance) + " meters");
    }

    //Clears distance traveled
    private void clearDistance(){
        tv_dtValue.setText("0 meters");
        currentDistance = 0;
    }

    //getUserPermission for location services
    private void getUserPermissionLocation(){
        //Checks current permission level
        int permission = ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION);

        //if permission is denied log and make request to ask for permission
        if (permission != PackageManager.PERMISSION_GRANTED){
            Log.d("getUserPermissionLocation","Permission Denied!");
            permissionRequest();
        }
    }

    //Prompt user for permissions
    private void permissionRequest(){
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION}, RECORD_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_REQUEST_CODE){
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d("RequestPermissionResult","User has denied permission");
            } else{
                Log.d("RequestPermissionResult", "User has granted permission");
            }
        }
    }

    //get last known location of user
    @SuppressLint("MissingPermission")
    private void startLocationService(){
        //Get last known position of the user
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if ( location != null){
                    updateCoordinates(location);
                }
                else{
                    Log.d("getLastLocation","Could not retrieve last location.");
                }
            }
        });
    }

    //Creates location request and starts locationUpdate
    protected void createLocationRequest() {

        //Makes the request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdate();
            }
        });
    }

    //Polls location service for most recent location
    @SuppressLint("MissingPermission")
    private void startLocationUpdate(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallBack,null);
    }


}