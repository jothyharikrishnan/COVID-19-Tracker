package com.example.covid19.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.covid19.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import static com.example.covid19.activity.MainActivity.*;

public class DetailActivity extends AppCompatActivity {

    PieChart pieChart;
    TextView tvCountry, tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths;
    ImageView flag;
    private int positionCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position", 0);

        getSupportActionBar().setTitle("Details of " + countryModelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tvCountry = findViewById(R.id.affectedcountryName);
        pieChart = findViewById(R.id.pieChart);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.deaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        flag = findViewById(R.id.flagicon);


        pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(countryModelList.get(positionCountry).getCases()), Color.parseColor("#E617BA")));
        pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(countryModelList.get(positionCountry).getRecovered()), Color.parseColor("#28A305")));
        pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(countryModelList.get(positionCountry).getDeaths()), Color.parseColor("#F60602")));
        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(countryModelList.get(positionCountry).getActive()), Color.parseColor("#05A0E6")));
        pieChart.startAnimation();

        Glide.with(getApplicationContext()).load(countryModelList.get(positionCountry).getFlag()).into(flag);

        tvCountry.setText(countryModelList.get(positionCountry).getCountry());
        tvCases.setText(countryModelList.get(positionCountry).getCases());
        tvRecovered.setText(countryModelList.get(positionCountry).getRecovered());
        tvCritical.setText(countryModelList.get(positionCountry).getCritical());
        tvActive.setText(countryModelList.get(positionCountry).getActive());
        tvTodayCases.setText(countryModelList.get(positionCountry).getTodayCases());
        tvTotalDeaths.setText(countryModelList.get(positionCountry).getDeaths());
        tvTodayDeaths.setText(countryModelList.get(positionCountry).getTodayDeaths());


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}

