package com.Huzaifa.centralpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomerDetails extends AppCompatActivity {

    private ImageView backArrow;
    private Button fineButton;
    private LinearLayout admin;
    private LinearLayout home;
    private LinearLayout search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        backArrow=findViewById(R.id.back_arrow_cust_details);
        fineButton=findViewById(R.id.fineButton);
        admin=findViewById(R.id.adminInCustomerDetails);
        home=findViewById(R.id.homeInCustomerDetails);
        search=findViewById(R.id.searchInCustomerDetails);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetails.this,AdminMainScreen.class);
                startActivity(intent);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetails.this,AdminDetails.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetails.this,AdminMainScreen.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetails.this,SearchParking.class);
                intent.putExtra("option",1);
                startActivity(intent);
            }
        });
    }
}