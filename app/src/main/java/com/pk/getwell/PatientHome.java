package com.pk.getwell;

import androidx.appcompat.app.AppCompatActivity;

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

public class PatientHome extends AppCompatActivity {
    String phone,problem;
    Button b1;
    EditText e1;
    LinearLayout l1,l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        String resp = getIntent().getStringExtra("response");
        System.out.println(resp);
        phone="12345";
        problem="No data";
        e1=findViewById(R.id.problem_pathome);
        b1=findViewById(R.id.btnsubmit_pathome);
        l1=findViewById(R.id.linear_pathome);
        l2=findViewById(R.id.linear2_pathome);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senddata();
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
            }
        });
    }
    private void senddata() {
        String url;
        url="http://getwell.scienceontheweb.net/updateproblem.php";
        problem=e1.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                  System.out.println(response);
                if(response.equals("0"))
                {
                    Toast.makeText(PatientHome.this, "", Toast.LENGTH_SHORT).show();
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

                parms.put("phone",phone);
                parms.put("prob",problem);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}