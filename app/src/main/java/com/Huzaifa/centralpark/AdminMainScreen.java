package com.Huzaifa.centralpark;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMainScreen extends AppCompatActivity {

    private CardView searchCard;
    private CardView customerDetailCard;
    private CardView reportCard;
    private LinearLayout admin;
    private LinearLayout search;
    private TextView date;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_screen);

        searchCard=findViewById(R.id.searchCard);
        customerDetailCard=findViewById(R.id.customerDetailCard);
        reportCard=findViewById(R.id.reportCard);
        admin=findViewById(R.id.admin);
        search=findViewById(R.id.search);
        date=findViewById(R.id.t1);


        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        date.setText("Date: "+timeStamp);

        searchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminMainScreen.this,AddParking.class);
                startActivity(intent);
            }
        });

        customerDetailCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminMainScreen.this,CustomerDetails.class);
                startActivity(intent);
            }
        });

        reportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminMainScreen.this,daily_report.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminMainScreen.this,SearchParking.class);
                intent.putExtra("option",1);
                startActivity(intent);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminMainScreen.this,AdminDetails.class);
                startActivity(intent);
            }
        });
    }
}