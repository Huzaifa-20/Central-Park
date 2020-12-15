package com.Huzaifa.centralpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AddParking extends AppCompatActivity {


    private ImageView backArrow;
    private ImageView parkingAdded;
    private LinearLayout admin;
    private LinearLayout home;
    private LinearLayout search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking);

        backArrow=findViewById(R.id.back_arrow_add_parking);
        parkingAdded=findViewById(R.id.parkingAdded);
        admin=findViewById(R.id.adminInAddParking);
        home=findViewById(R.id.homeInAddParking);
        search=findViewById(R.id.searchInAddParking);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        parkingAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddParking.this,AdminMainScreen.class);
                startActivity(intent);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddParking.this,AdminDetails.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddParking.this,AdminMainScreen.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddParking.this,SearchParking.class);
                intent.putExtra("option",1);
                startActivity(intent);
            }
        });
    }
}