package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Category;
import models.FoursquareResponse;
import models.Venue;


public class FoursquareListView extends AppCompatActivity {
    TextView addressView;
    ImageButton ib;
    ExpandableListView expandableListView;
    private ObjectMapper mapper = new ObjectMapper();
    FoursquareResponse foursquareResponse = new FoursquareResponse();
    HashMap<String,List<Venue>> categories = new HashMap<>();
    List<Category> listgroup = new ArrayList<>();
    FoursquareExpandableListAdapter foursquareExpandableListAdapter;


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foursquare_list_view);
        addressView = findViewById(R.id.textView2);
        ib = findViewById(R.id.imageButton2);
        ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        double longtitude = intent.getDoubleExtra("lng",0.0);
        double lantitude = intent.getDoubleExtra("lan",0.0);
        String address = intent.getStringExtra("address");
        addressView.setText(address);

        /**
         *  Here I call the Foursquare Api with specific library Volley. Also in this request i fill hash map and list with category restaurants and name restaurants
         */
        String url =  "https://api.foursquare.com/v2/venues/search?client_id=XTXYWUGYAU1DZQ5LLTTH3U22LYCQZE5WLIT002STKNOGKQHJ&client_secret=33HM0JQ0ZKGGTWG1BXDHI1Q2M2152A00PQHZRHUXK1B0RNTD&v=20180323&ll="+lantitude+","+longtitude+"&limit=100&categoryId=4d4b7105d754a06374d81259";
        RequestQueue requestQueue;
                            requestQueue = Volley.newRequestQueue(this);
                            StringRequest
                                    stringRequest
                                    = new StringRequest(
                                    Request.Method.GET,
                                    url,
                                    new Response.Listener() {
                                        @Override
                                        public void onResponse(Object response) {
                                            try {
                                                foursquareResponse = mapper.readValue(response.toString(), FoursquareResponse.class);
                                                for (Venue item:foursquareResponse.getResponse().getVenues() ) {
                                                    if (categories.containsKey(item.getCategories().get(0).getName())){
                                                        categories.get(item.getCategories().get(0).getName()).add(item);
                                                    }
                                                    else {
                                                        List<Venue> venues = new ArrayList<>();
                                                        venues.add(item);
                                                        categories.put(item.getCategories().get(0).getName(),venues);
                                                        listgroup.add(item.getCategories().get(0));
                                                    }
                                                }
                                                foursquareExpandableListAdapter = new FoursquareExpandableListAdapter(FoursquareListView.this,listgroup,categories);
                                                expandableListView = findViewById(R.id.categoryList);
                                                expandableListView.setAdapter(foursquareExpandableListAdapter);
                                                for (int i = 0 ; i<listgroup.size(); i++){
                                                    expandableListView.expandGroup(i);
                                                }
                            foursquareExpandableListAdapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }}
                    ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    }
                });
        requestQueue.add(stringRequest);
    }
}
