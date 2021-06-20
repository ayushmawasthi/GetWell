package com.pk.getwell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Pat_Check_Appointment extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5;
    String phone;
    Button b1,b2,b3,b4;
    String coordinates="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pat_check_appointment);
        t1=findViewById(R.id.pat_check_name);
        t2=findViewById(R.id.pat_check_address);
        t3=findViewById(R.id.pat_check_locality);
        t4=findViewById(R.id.pat_check_lat);
        t5=findViewById(R.id.pat_check_long);
        b1=findViewById(R.id.btn_navigate_d);
        b2=findViewById(R.id.btn_navigate_b);
        b3=findViewById(R.id.btn_navigate_w);
        b4=findViewById(R.id.btn_navigate_l);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmap("d");
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmap("b");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmap("w");
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmap("l");
            }
        });
        String resp = getIntent().getStringExtra("phone");
        phone=resp;
        fetchdata();
    }
    public void openmap(String mode)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+coordinates+"&mode="+mode));
        intent.setPackage("com.google.android.apps.maps");
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }

    }

    private void fetchdata() {
        String url;
        url="http://getwell.scienceontheweb.net/fetch_doctor_details.php";


        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("check"+response);
                String [] temp=response.split(",");
                t1.setText(temp[0]);
                t2.setText(temp[1]);
                t3.setText(temp[2]);
                t4.setText(temp[3]);
                t5.setText(temp[4]);
                coordinates=temp[3]+","+temp[4];
                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);
                b4.setVisibility(View.VISIBLE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();

                parms.put("phone",phone);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}