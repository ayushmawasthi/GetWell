package com.pk.getwell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;
    String phone,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=findViewById(R.id.phone_login);
        e2=findViewById(R.id.pass_login);
        b1=findViewById(R.id.btn_d_login);
        b2=findViewById(R.id.btn_p_login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=e1.getText().toString().trim();
                pass=e2.getText().toString().trim();
                if(phone.length()>0 && pass.length()>0) {
                    loginfn(1);
                }
                else
                {
                    Toast.makeText(Login.this, "Please do not leave blank spaces", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=e1.getText().toString().trim();
                pass=e2.getText().toString().trim();
                if(phone.length()>0 && pass.length()>0) {
                    loginfn(2);
                }
                else
                {
                    Toast.makeText(Login.this, "Please do not leave blank spaces", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginfn(int user) {
        String url;
        if(user==1)
        {
            url="http://getwell.scienceontheweb.net/login.php";
        }
        else
        {
            url="http://getwell.scienceontheweb.net/pat_login.php";
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  System.out.println(response);
                if(response.equals("0"))
                {
                    Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(user==1) {


                        Intent intent = new Intent(Login.this, DoctorHome.class);
                        intent.putExtra("response", response);

                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Login.this, PatientHome.class);
                        intent.putExtra("response", response);

                        startActivity(intent);

                    }
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
                parms.put("username",phone);
                parms.put("password",pass);



                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}