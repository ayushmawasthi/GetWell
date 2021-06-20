package com.pk.getwell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Doc_Appointment_Form extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_appointment_form);
        e1=findViewById(R.id.et_doc_patcon);
        e2=findViewById(R.id.et_doc_patdate);
        b1=findViewById(R.id.btn_doc_sendappoint);
        String resp = getIntent().getStringExtra("phone");
        phone=resp;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact=e1.getText().toString().trim();
                String date=e1.getText().toString().trim();
                send_data(contact,date);
                finish();
            }
        });
    }
    private void send_data(String contact,String date){
        String url;
        url="http://getwell.scienceontheweb.net/doc_set_appointment.php";


        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();

                parms.put("pat_con",contact);
                parms.put("date",date);
                parms.put("doc_con",phone);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}