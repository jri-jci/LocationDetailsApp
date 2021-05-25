package com.example.locationdetailsapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView txtLat, txtLang;
	private String name = "Ilayaraja";
	private String name1 = "Ilayaraja1";
	private String name2 = "Ilayaraja2";
    Button btnLoc;
    private FusedLocationProviderClient locProvClient;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        locProvClient = LocationServices.getFusedLocationProviderClient(this);
        txtLang = findViewById(R.id.txtLang);
        txtLat = findViewById(R.id.txtLatt);
        btnLoc = findViewById(R.id.btnLocation);


        btnLoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locProvClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();

                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            txtLang.setText(String.valueOf(addressList.get(0).getLongitude()));
                                            txtLat.setText(String.valueOf(addressList.get(0).getLatitude()));
                                        }
                                    });

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtLang.setText("null");
                                        txtLat.setText("null");
                                    }
                                });
                            }
                        }
                    });
                } else {
                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }
}