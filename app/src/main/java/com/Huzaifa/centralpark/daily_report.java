package com.Huzaifa.centralpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class daily_report extends AppCompatActivity {


    private ImageView backArrow;
    private LinearLayout admin;
    private LinearLayout home;
    private LinearLayout search;
    private TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);

        backArrow=findViewById(R.id.back_arrow_daily_report);
        admin=findViewById(R.id.adminInDailyReport);
        home=findViewById(R.id.homeInDailyReport);
        search=findViewById(R.id.searchInDailyReport);
        date=findViewById(R.id.TV1);

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        date.setText("Date: "+timeStamp);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(daily_report.this,AdminDetails.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(daily_report.this,AdminMainScreen.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(daily_report.this,SearchParking.class);
                intent.putExtra("option",1);
                startActivity(intent);
            }
        });
    }
}