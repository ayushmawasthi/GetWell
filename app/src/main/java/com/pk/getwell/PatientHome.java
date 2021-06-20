package com.pk.getwell;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.HashMap;
import java.util.Map;

public class PatientHome extends AppCompatActivity {
    String phone,problem;
    Button b1;
    EditText e1;
    LinearLayout l1,l2;
    ListView listView;
    String doctorname[]={"Auchi"};
    String date[]={"14 June 2021"};
    String doc_phoneNumber[]={"123"};
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        String resp = getIntent().getStringExtra("response");
        System.out.println("Response from previous to Pat"+resp);
        phone=resp;
        problem="No data";
        e1=findViewById(R.id.problem_pathome);
        b1=findViewById(R.id.btnsubmit_pathome);
        l1=findViewById(R.id.linear_pathome);
        l2=findViewById(R.id.linear2_pathome);
        listView=findViewById(R.id.listdoctors_pathome);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senddata();
                l1.setVisibility(View.GONE);
                fetchdoctors();
            //    adapter.notifyDataSetChanged();
                l2.setVisibility(View.VISIBLE);
            }
        });
      //   adapter=new MyAdapter(this,doctorname,date);
    //    listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(doc_phoneNumber[position]);
                requestappointment(doc_phoneNumber[position]);


            }
        });
    }
    private void requestappointment( String number)
    {
        //TODO: MAke email
        String url;
        url="http://getwell.scienceontheweb.net/updateproblem.php";
        problem=e1.getText().toString().trim();

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

                parms.put("phone",phone);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private void senddata() {
        String url;
        url="http://getwell.scienceontheweb.net/updateproblem.php";
        problem=e1.getText().toString().trim();

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

                parms.put("phone",phone);
                parms.put("prob",problem.toLowerCase());
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void fetchdoctors() {
        String url;
        url="http://getwell.scienceontheweb.net/fetch_doctor.php";
        problem=e1.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                String [] temp=response.split("00");

                doctorname=temp[0].split(",");
                doc_phoneNumber=temp[1].split(",");
                date=temp[2].split(",");
                adapter=new MyAdapter(PatientHome.this,doctorname,date);
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

                parms.put("prob",problem);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String docNames[], docDate[];
        MyAdapter(Context c,String docN[], String docD[])
        {
            super(c,R.layout.doctorlist,R.id.doctorname_custlist,docN);
            this.context=c;
            this.docNames=docN;
            this.docDate=docD;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.doctorlist,parent,false);
            TextView tv1=row.findViewById(R.id.doctorname_custlist);
            TextView tv2=row.findViewById(R.id.date_custlist);

            tv1.setText(docNames[position]);
            tv2.setText(docDate[position]);


            return row;
        }
    }


}