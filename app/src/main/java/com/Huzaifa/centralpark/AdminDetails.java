package com.Huzaifa.centralpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminDetails extends AppCompatActivity {

    private Button fineButton;
    private ImageView home;
    private ImageView search;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);

        fineButton=findViewById(R.id.fineButtonInAdmin);
        home=findViewById(R.id.homeInAdminDetails);
        search=findViewById(R.id.searchInAdminDetails);
        backArrow=findViewById(R.id.back_arrow_admin_details);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDetails.this,AdminMainScreen.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDetails.this,AdminMainScreen.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDetails.this,SearchParking.class);
                intent.putExtra("option",1);
                startActivity(intent);
            }
        });
    }
}