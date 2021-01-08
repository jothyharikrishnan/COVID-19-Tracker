package com.example.covid19.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19.R;
import com.example.covid19.adapter.CustomAdapter;
import com.example.covid19.adapter.MyCustomAdapter;
import com.example.covid19.model.CountryModel;
import com.example.covid19.model.ItemModel;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<CountryModel> countryModelList = new ArrayList<>();
    ArrayList<ItemModel> arrayList;
    CustomAdapter adapter;
    TextView tele;
    RecyclerView recyclerView;
    int icons[] = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};


    String iconsName[] = {"total cases", "recovered", "today Cases", "deaths", "today Deaths", "affected Countries", "active", "critical"};
    EditText editText;
    ListView listView;
    SimpleArcLoader simpleArcLoader;
    CountryModel countryModel;
    MyCustomAdapter myCustomAdapter;


     PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editSearchList);
        listView = findViewById(R.id.listView);
        tele = findViewById(R.id.telephone);
        simpleArcLoader = findViewById(R.id.searchLoader);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getSupportActionBar().setTitle("Affected Countries");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                listView.setVisibility(View.VISIBLE);

                myCustomAdapter.getFilter().filter(s);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fetchCountries();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("position", position));
            }
        });







        pieChart = findViewById(R.id.pieChart);


        recyclerView = findViewById(R.id.recycler_view);


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        arrayList = new ArrayList<>();
        adapter = new CustomAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
        fetchData();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            listView.setVisibility(View.GONE);
            getSupportActionBar().setTitle("COVID 19 Tracker");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (listView.getVisibility() == View.VISIBLE) {
            listView.setVisibility(View.GONE);
            getSupportActionBar().setTitle("COVID 19 Tracker");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            onStop();
        }
    }


    private void fetchCountries() {

        String url = "https://corona.lmao.ninja/v2/countries";
        simpleArcLoader.setVisibility(View.VISIBLE);
        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("response", "Response==" + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String active = jsonObject.getString("active");
                                String critical = jsonObject.getString("critical");

                                JSONObject Object = jsonObject.getJSONObject("countryInfo");
                                String flagUrl = Object.getString("flag");

                                countryModel = new CountryModel(flagUrl, countryName, cases, todayCases, deaths, todayDeaths, recovered, active, critical);
                                countryModelList.add(countryModel);


                            }

                            myCustomAdapter = new MyCustomAdapter(MainActivity.this, countryModelList);
                            listView.setAdapter(myCustomAdapter);
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            myCustomAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);

                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/all/";

        //simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            String cases, recovered, todayCases, deaths, todayDeaths, affectedCountries, active, critical;
                            cases = jsonObject.getString("cases");
                            todayCases = jsonObject.getString("todayCases");
                            recovered = jsonObject.getString("recovered");
                            critical = jsonObject.getString("critical");
                            deaths = jsonObject.getString("deaths");
                            todayDeaths = jsonObject.getString("todayDeaths");
                            affectedCountries = jsonObject.getString("affectedCountries");
                            active = jsonObject.getString("active");



                            String str[] = {cases, recovered, todayCases, deaths, todayDeaths, affectedCountries, active, critical};

                            for (int i = 0; i < iconsName.length; i++) {
                                ItemModel it = new ItemModel(iconsName[i], str[i]);
                                arrayList.add(it);
                            }
                            pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(cases), Color.parseColor("#E617BA")));
                            pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recovered), Color.parseColor("#28A305")));
                            pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(deaths), Color.parseColor("#F60602")));
                            pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(active), Color.parseColor("#05A0E6")));
                            pieChart.startAnimation();
                            // simpleArcLoader.stop();
                            //simpleArcLoader.setVisibility(View.GONE);
                            //scrollView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //simpleArcLoader.stop();
                            //simpleArcLoader.setVisibility(View.GONE);
                            //scrollView.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //simpleArcLoader.stop();
                //simpleArcLoader.setVisibility(View.GONE);
                //scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


}