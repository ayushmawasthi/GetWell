package com.pk.getwell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DoctorHome extends AppCompatActivity {
    private static final int REQUEST_LOCATION=1;
    String lat="0",lon="0", phone,speciality;
    LocationManager locationManager;
    Button b1;
    EditText e1;
    LinearLayout l1,l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        String resp = getIntent().getStringExtra("response");
        System.out.println(resp);
        phone="9889733252";
        e1=findViewById(R.id.speciality_doctorhome);
        l1=findViewById(R.id.linear_doctorhome);
        l2=findViewById(R.id.linear2_doctorhome);

        b1=findViewById(R.id.location_doctorhome);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //check for gps
                getLocation();
                System.out.println(lat+" "+lon);
                senddata();
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                speciality=e1.getText().toString().trim();



            }
        });

    }

    private void senddata() {
        String url;
        url="http://getwell.scienceontheweb.net/updatelocation.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  System.out.println(response);
                if(response.equals("0"))
                {
                    Toast.makeText(DoctorHome.this, "", Toast.LENGTH_SHORT).show();
                }
                else
                {



                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("lat",lat);
                parms.put("long",lon);
                parms.put("phone",phone);
                parms.put("spec",speciality);



                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(DoctorHome.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DoctorHome.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        else
        {
            Location locationGPS=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if(locationGPS!=null)
            {
                double latitudev=locationGPS.getLatitude();
                double longitudev=locationGPS.getLongitude();
                lat=String.valueOf(latitudev);
                lon=String.valueOf(longitudev);


            }
            else if(locationNetwork!=null)
            {
                double latitudev=locationNetwork.getLatitude();
                double longitudev=locationNetwork.getLongitude();
                lat=String.valueOf(latitudev);
                lon=String.valueOf(longitudev);


            }
            else if(locationPassive!=null)
            {
                double latitudev=locationPassive.getLatitude();
                double longitudev=locationPassive.getLongitude();
                lat=String.valueOf(latitudev);
                lon=String.valueOf(longitudev);


            }
            else
            {
                Toast.makeText(this, "Cant get yor location", Toast.LENGTH_SHORT).show();
            }

        }
    }

}