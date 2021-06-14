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

public class Signup extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b1,b2;
    String name,phone,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        e1=findViewById(R.id.name_signup);
        e2=findViewById(R.id.phone_signup);
        e3=findViewById(R.id.pass_signup);
        b1=findViewById(R.id.btn_d_signup);
        b2=findViewById(R.id.btn_p_signup);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=e1.getText().toString().trim();
                phone=e2.getText().toString().trim();
                pass=e3.getText().toString().trim();
                if(phone.length()>0 && pass.length()>0 && name.length()>0) {
                    signfn(1);
                }
                else
                {
                    Toast.makeText(Signup.this, "Please do not leave blank spaces", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=e1.getText().toString().trim();
                pass=e2.getText().toString().trim();
                if(phone.length()>0 && pass.length()>0) {
                    signfn(2);
                }
                else
                {
                    Toast.makeText(Signup.this, "Please do not leave blank spaces", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signfn(int user) {
        String url;
        if(user==1)
        {
            url="http://getwell.scienceontheweb.net/signup.php";
        }
        else
        {
            url="http://getwell.scienceontheweb.net/pat_signup.php";
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                if(response.equals("0"))
                {
                    Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();
                }
                else if(response.equals("exit"))
                {
                    Toast.makeText(Signup.this, "Phone Number Already Exists", Toast.LENGTH_SHORT).show();
                }
                else
                {

                        Intent intent = new Intent(Signup.this, Login.class);
                        startActivity(intent);


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
                parms.put("name",name);
                parms.put("phone",phone);
                parms.put("password",pass);
                parms.put("extra","tbd");
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}