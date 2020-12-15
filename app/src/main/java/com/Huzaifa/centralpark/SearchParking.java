package com.Huzaifa.centralpark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchParking extends AppCompatActivity {

    private CircleImageView help;
    private String helpMessage;
    private CardView A1, A2, A3,  A4, A5, A6, A7, A8, A9;
    private ImageView backArrow;
    private int option;
    private FloatingActionButton doneFAB;
    private ArrayList<ParkingSpot> parkingSpots;

    FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_parking);

        help=findViewById(R.id.searchParkingQuestionMark);
        helpMessage="- Tap on any one parking spot.\n\n " +
                "- Note: You can only select from parking spots that are currently available, i.e., they are Green in color.\n\n" +
                "- Tap the thumbs up circular button at the bottom to proceed with booking parking spot.\n\n"+
                "- To return back to main screen, simply tap the back arrow icon on the screen's top left.\n";

        option=0;
        A1=findViewById(R.id.A1);
        A2=findViewById(R.id.A2);
        A3=findViewById(R.id.A3);
        A4=findViewById(R.id.A4);
        A5=findViewById(R.id.A5);
        A6=findViewById(R.id.A6);
        A7=findViewById(R.id.A7);
        A8=findViewById(R.id.A8);
        A9=findViewById(R.id.A9);
        backArrow=findViewById(R.id.back_arrow_search_parking);
        doneFAB=findViewById(R.id.doneFAB);
        parkingSpots=new ArrayList<>();

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(SearchParking.this);
                View mView=getLayoutInflater().inflate(R.layout.custom_dialogue,null);

                TextView title=mView.findViewById(R.id.DialogueBoxTitle);
                TextView content=mView.findViewById(R.id.DialogueBoxContent);
                Button button=mView.findViewById(R.id.DialogueButton);

                alert.setView(mView);

                content.setText(helpMessage);

                final AlertDialog alertDialog=alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Parking Lot");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ParkingSpot temp=new ParkingSpot(dataSnapshot.getValue(ParkingSpot.class));
                parkingSpots.add(temp);
                if(temp.getStatus().equals("1"))
                {
                    setBookedSpots(temp.getSpot());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingSpots.get(0).status.equals("0") )
                {
                    if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("0")){
                        option=1;
                        A1.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("1");
                        changeDB(1,1);
                    }
                    else if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("1")){
                        option=0;
                        A1.setCardBackgroundColor(getResources().getColor(R.color.unselectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("0");
                        changeDB(1,0);
                    }
                }
                else
                {
                    Toast.makeText(SearchParking.this, "You have already booked Spot:"+
                            CustomerMainScreen.currentUser.getCurrentBookingSpot(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingSpots.get(1).status.equals("0"))
                {
                    if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("0")){
                        option=2;
                        A2.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("2");
                        changeDB(2,1);
                    }
                    else if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("2"))
                    {
                        option=0;
                        A2.setCardBackgroundColor(getResources().getColor(R.color.unselectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("0");
                        changeDB(2,0);
                    }
                }
                else
                {
                    Toast.makeText(SearchParking.this, "You have already booked Spot:"+
                            CustomerMainScreen.currentUser.getCurrentBookingSpot(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingSpots.get(2).status.equals("0"))
                {
                    if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("0")){
                        option=3;
                        A3.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("3");
                        changeDB(3,1);
                    }
                    else if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("3")){
                        option=0;
                        A3.setCardBackgroundColor(getResources().getColor(R.color.unselectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("0");
                        changeDB(3,0);
                    }
                }
                else
                {
                    Toast.makeText(SearchParking.this, "You have already booked Spot:"+
                            CustomerMainScreen.currentUser.getCurrentBookingSpot(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingSpots.get(3).status.equals("0"))
                {
                    if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("0")){
                        option=4;
                        A4.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("4");
                        changeDB(4,1);
                    }
                    else if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("4")){
                        option=0;
                        A4.setCardBackgroundColor(getResources().getColor(R.color.unselectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("0");
                        changeDB(4,0);
                    }
                }
                else
                {
                    Toast.makeText(SearchParking.this, "You have already booked Spot:"+
                            CustomerMainScreen.currentUser.getCurrentBookingSpot(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        A5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingSpots.get(4).status.equals("0"))
                {
                    if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("0")){
                        option=5;
                        A5.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("5");
                        changeDB(5,1);
                    }
                    else if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("5")){
                        option=0;
                        A5.setCardBackgroundColor(getResources().getColor(R.color.unselectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("0");
                        changeDB(5,0);
                    }
                }
                else
                {
                    Toast.makeText(SearchParking.this, "You have already booked Spot:"+
                            CustomerMainScreen.currentUser.getCurrentBookingSpot(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        A6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingSpots.get(5).status.equals("0"))
                {
                    if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("0")){
                        option=6;
                        A6.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("6");
                        changeDB(6,1);
                    }
                    else if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("6")){
                        option=0;
                        A6.setCardBackgroundColor(getResources().getColor(R.color.unselectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("0");
                        changeDB(6,0);
                    }
                }
                else
                {
                    Toast.makeText(SearchParking.this, "You have already booked Spot:"+
                            CustomerMainScreen.currentUser.getCurrentBookingSpot(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        A7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingSpots.get(6).status.equals("0"))
                {
                    if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("0")){
                        option=7;
                        A7.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("7");
                        changeDB(7,1);
                    }
                    else if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("7")){
                        option=0;
                        A7.setCardBackgroundColor(getResources().getColor(R.color.unselectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("0");
                        changeDB(7,0);
                    }
                }
                else
                {
                    Toast.makeText(SearchParking.this, "You have already booked Spot:"+
                            CustomerMainScreen.currentUser.getCurrentBookingSpot(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        A8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingSpots.get(7).status.equals("0"))
                {
                    if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("0")){
                        option=8;
                        A8.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("8");
                        changeDB(8,1);
                    }
                    else if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("8")){
                        option=0;
                        A8.setCardBackgroundColor(getResources().getColor(R.color.unselectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("0");
                        changeDB(8,0);
                    }
                }
                else
                {
                    Toast.makeText(SearchParking.this, "You have already booked Spot:"+
                            CustomerMainScreen.currentUser.getCurrentBookingSpot(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        A9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingSpots.get(8).status.equals("0"))
                {
                    if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("0")){
                        option=9;
                        A9.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("9");
                        changeDB(9,1);
                    }
                    else if(CustomerMainScreen.currentUser.getCurrentBookingSpot().equals("9")){
                        option=0;
                        A9.setCardBackgroundColor(getResources().getColor(R.color.unselectParking));
                        CustomerMainScreen.currentUser.setCurrentBookingSpot("0");
                        changeDB(9,0);
                    }
                }
                else
                {
                    Toast.makeText(SearchParking.this, "You have already booked Spot:"+
                            CustomerMainScreen.currentUser.getCurrentBookingSpot(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int goToAdmin=getIntent().getIntExtra("option",0);
                if(goToAdmin==1){
                    Intent intent=new Intent(SearchParking.this,AdminMainScreen.class);
                    startActivity(intent);
                }
                else{

                    Intent returnIntent=new Intent();
                    returnIntent.putExtra("bookingSpot",CustomerMainScreen.currentUser.getCurrentBookingSpot());
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
    }

    public void changeDB(int pSpot,int status)
    {
        reference = database.getReference("Parking Lot").child(""+pSpot);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("booking time", "0");
        hashMap.put("spot", ""+pSpot);
        if(status==0)
        {
            hashMap.put("booker Id", "-1");
            hashMap.put("status", "0");
        }
        else
        {
            hashMap.put("booker Id", user.getUid());
            hashMap.put("status", "1");
        }

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SearchParking.this, "Parking Spot Edited!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SearchParking.this, "Parking Spot created", Toast.LENGTH_SHORT).show();
            }
        });


        reference = database.getReference("Users").child(CustomerMainScreen.currentUser.getMyId());

        hashMap = new HashMap<>();
        hashMap.put("myId", CustomerMainScreen.currentUser.getMyId());
        hashMap.put("bookingTime", CustomerMainScreen.currentUser.getBookingTime());
        hashMap.put("currentBookingSpot", CustomerMainScreen.currentUser.getCurrentBookingSpot());

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SearchParking.this, "Your Booking Spot is edited!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SearchParking.this, "Your Booking Spot cant be edited!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setBookedSpots(String spot)
    {
        if(spot.equals("1")) {
            A1.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
        }
        if(spot.equals("2")) {
            A2.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
        }
        if(spot.equals("3")) {
            A3.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
        }
        if(spot.equals("4")) {
            A4.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
        }
        if(spot.equals("5")) {
            A5.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
        }
        if(spot.equals("6")) {
            A6.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
        }
        if(spot.equals("7")) {
            A7.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
        }
        if(spot.equals("8")) {
            A8.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
        }
        if(spot.equals("9")) {
            A9.setCardBackgroundColor(getResources().getColor(R.color.selectParking));
        }


    }
}