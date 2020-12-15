package com.Huzaifa.centralpark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

import java.util.HashMap;

public class CustomerMainScreen extends AppCompatActivity {

    private ImageView help;
    public static String helpMessage;
    private CardView searchSpotCard;
    private CardView navigationCard;
    private FloatingActionButton paymentFAB;
    private TextView bookingTime;
    private SeekBar seekBar;
    private String bookingSlot;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main_screen);

        help=findViewById(R.id.custMainQuestionMark);
        helpMessage="- Tap the Search Spot Card to pick a parking spot.\n\n " +
                "- Scroll the minutes bar to set your booking duration.\n\n" +
                "- Tap the Get Directions card to receive directions to parking lot.\n";
        searchSpotCard=findViewById(R.id.searchCard);
        navigationCard=findViewById(R.id.navigationCard);
        paymentFAB=findViewById(R.id.paymentFab);
        bookingTime=findViewById(R.id.bookingTimeCustMain);
        seekBar=findViewById(R.id.minutesBar);
        bookingSlot="0";
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Users");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User temp=new User(dataSnapshot.getValue(User.class));
                System.out.println("\n\n<======================IM HEREEEEEE===================================>\n\n");
                System.out.println(temp.getMyId());
                System.out.println(temp.getBookingTime());
                System.out.println(temp.getCurrentBookingSpot());
                if(temp.getMyId().equals(mAuth.getCurrentUser().getUid()) )
                {
                    currentUser=new User(temp);
                    seekBar.setProgress(Integer.parseInt(currentUser.getBookingTime()));
                    bookingTime.setText(currentUser.getBookingTime()+" Minutes");
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


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(CustomerMainScreen.this);
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


        searchSpotCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerMainScreen.this,SearchParking.class);
                startActivityForResult(intent,123);
            }
        });

        navigationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerMainScreen.this,Navigation.class);
                startActivity(intent);
            }
        });

        paymentFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser.getCurrentBookingSpot().equals("0"))
                {
                    Toast.makeText(CustomerMainScreen.this, "Tap on Search Spot Card to reserve Booking Slot", Toast.LENGTH_SHORT).show();
                }
                else if(seekBar.getProgress()==0)
                {
                    Toast.makeText(CustomerMainScreen.this, "Scroll the bar to set Booking Time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(CustomerMainScreen.this,ChoosePayment.class);
                    startActivity(intent);
                }
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress=0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress=i;
                bookingTime.setText(""+progress+" Minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                bookingTime.setText(""+progress+" Minutes");

                reference=database.getReference("Parking Lot").child(currentUser.getCurrentBookingSpot());

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("booker Id", ""+currentUser.getMyId());
                hashMap.put("booking time", ""+progress);
                hashMap.put("spot", currentUser.getCurrentBookingSpot());
                hashMap.put("status", "1");

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CustomerMainScreen.this, "Time Set", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CustomerMainScreen.this, "Time not set", Toast.LENGTH_SHORT).show();
                    }
                });


                reference = database.getReference("Users").child(currentUser.getMyId());

                hashMap = new HashMap<>();
                hashMap.put("myId", ""+currentUser.getMyId());
                hashMap.put("bookingTime", ""+progress);
                hashMap.put("currentBookingSpot", currentUser.getCurrentBookingSpot());

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // Toast.makeText(CustomerMainScreen.this, "Your Booking Spot is edited!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(SearchParking.this, "Your Booking Spot cant be edited!", Toast.LENGTH_SHORT).show();
                    }
                });

                currentUser.setBookingTime(""+progress);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==RESULT_OK && data!=null)
        {
            bookingSlot=data.getStringExtra("bookingSpot");
            Toast.makeText(this, "Booking Spot-"+bookingSlot, Toast.LENGTH_SHORT).show();
        }
    }
}