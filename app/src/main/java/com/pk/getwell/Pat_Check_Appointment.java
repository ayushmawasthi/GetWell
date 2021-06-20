package com.pk.getwell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pat_check_appointment);
        t1=findViewById(R.id.pat_check_name);
        t2=findViewById(R.id.pat_check_address);
        t3=findViewById(R.id.pat_check_locality);
        t4=findViewById(R.id.pat_check_lat);
        t5=findViewById(R.id.pat_check_long);
        String resp = getIntent().getStringExtra("phone");
        phone=resp;
        fetchdata();
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