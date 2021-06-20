package com.pk.getwell;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DoctorHome extends AppCompatActivity {
    private static final int REQUEST_LOCATION=44;
    String lat="0",lon="0",country="0",address="0",locality="0", phone,speciality;
    LocationManager locationManager;
    Button b1,b2;
    EditText e1;
    LinearLayout l1,l2;
    ListView listView;
    String patient[]={"Auchi", "Aman", "Akshay"};
    String date[]={"14 June 2021","15 June 2021", "16 June 2021"};
    MyAdapter adapter;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        String resp = getIntent().getStringExtra("response");
    //    System.out.println("Response from Prev to Doc"+resp);
        phone=resp;
        e1=findViewById(R.id.speciality_doctorhome);
        l1=findViewById(R.id.linear_doctorhome);
        l2=findViewById(R.id.linear2_doctorhome);

        b1=findViewById(R.id.location_doctorhome);
        b2=findViewById(R.id.btn_make_appoint_doc);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DoctorHome.this,Doc_Appointment_Form.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });
        listView=findViewById(R.id.listappoint_dochome);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //check for gps


                senddata();
                
                l1.setVisibility(View.GONE);
                fetchappoint();
                l2.setVisibility(View.VISIBLE);
                speciality=e1.getText().toString().trim();



            }
        });

    }

    private void fetchappoint() {
        String url;
        url="http://getwell.scienceontheweb.net/fetch_appointment.php";
        speciality=e1.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                String [] temp=response.split("00");

                patient=temp[0].split(",");
                date=temp[1].split(",");
                adapter=new DoctorHome.MyAdapter(DoctorHome.this,patient,date);
                listView.setAdapter(adapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();

                parms.put("spec",speciality);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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
                    Toast.makeText(DoctorHome.this, "Updated", Toast.LENGTH_SHORT).show();
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
                System.out.println("Latitude "+lat);
                System.out.println("Longitude "+lon);
                System.out.println("Locality "+locality);
                System.out.println("Address "+address);
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("lat",lat);
                parms.put("long",lon);
                parms.put("locality",locality);
                parms.put("address",address);
                parms.put("phone",phone);
                parms.put("spec",speciality.toLowerCase());



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
            System.out.println("Permission Not given ");

        }
        else
        {


            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Location> task) {
                    Location location=task.getResult();
                    System.out.println(location);
                    if(location !=null)
                    {

                        try {
                            Geocoder geocoder=new Geocoder(DoctorHome.this, Locale.getDefault());
                            List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            lat= String.valueOf(addressList.get(0).getLatitude());
                            lon= String.valueOf(addressList.get(0).getLongitude());
                            country=String.valueOf(addressList.get(0).getCountryName());
                            address=String.valueOf(addressList.get(0).getAddressLine(0));
                            locality=String.valueOf(addressList.get(0).getLocality());
                            System.out.println("Latitude is "+lat);
                            System.out.println("Longitude is "+lon);
                            System.out.println("Country is "+country);
                            System.out.println("Locality is "+locality);
                            System.out.println("Address is "+address);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    }
    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String patNames[], patDate[];
        MyAdapter(Context c,String docN[], String docD[])
        {
            super(c,R.layout.doctorlist,R.id.doctorname_custlist,docN);
            this.context=c;
            this.patNames=docN;
            this.patDate=docD;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.doctorlist,parent,false);
            TextView tv1=row.findViewById(R.id.doctorname_custlist);
            TextView tv2=row.findViewById(R.id.date_custlist);
            tv1.setText(patNames[position]);
            tv2.setText(patDate[position]);
            return row;
        }
    }

}