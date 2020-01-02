package com.istiak.recyclerviewinmysqldatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    public String userName[];
    public String contact[];
    public String email[];

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        //call showData function
        showData();
    }


    private void showData() {
        //for showing progress dialog
        loading = new ProgressDialog(MainActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.VIEW_DATA_URL;

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
                Log.d("data", response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toast.makeText(MainActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }


    private void showJSON(String response) {
        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);


            if (result.length() == 0) {
                Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
            } else {


                int length=result.length();
                Log.d("length",""+result.length());
                userName=new String[length];
                contact=new String[length];
                email=new String[length];

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String name = jo.getString(Constant.KEY_USER_NAME);
                    String userContact = jo.getString(Constant.KEY_CONTACT);
                    String userEmail = jo.getString(Constant.KEY_EMAIL);

                    Log.d("info",userName[i]+" "+contact[i]+" "+email[i]);

                    //put value into Hashmap
                    HashMap<String, String> user_data = new HashMap<>();
                    user_data.put(Constant.KEY_USER_NAME, name);
                    user_data.put(Constant.KEY_CONTACT, userContact);
                    user_data.put(Constant.KEY_EMAIL, userEmail);

                    list.add(user_data);
                }



                //  call the constructor of CustomAdapter to send the reference and data to Adapter
                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,list);
                recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
