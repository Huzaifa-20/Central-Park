package com.Huzaifa.centralpark;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class info_jazzcash extends AppCompatActivity {

    private TextView cost;
    private EditText phoneNumber;
    private EditText mpin;
    private CircleImageView help;
    private String helpMessage;
    private ImageView backArrow;
    private String bookingDoneMessage;
    private FloatingActionButton doneFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_jazzcash);

        cost=findViewById(R.id.costJazzCash);
        phoneNumber=findViewById(R.id.phoneNumJazzCash);
        mpin=findViewById(R.id.mpinJazzCash);
        help=findViewById(R.id.jazzCashQuestionMark);
        helpMessage="- Fill in all relevant fields.\n\n " +
                "- Then tap on the circular green button at the bottom to complete booking procedure.\n\n" +
                "- You can also press back arrow key on top left corner to go back to previous screen to choose different method of payment.\n";
        bookingDoneMessage="- Your booking has been confirmed.\n\n " +
                "- Booking Spot: " + CustomerMainScreen.currentUser.getCurrentBookingSpot()+"\n\n"+
                "- Booking Time: " + CustomerMainScreen.currentUser.getBookingTime()+" minutes\n";
        backArrow=findViewById(R.id.back_arrow_jazzCash);
        doneFab=findViewById(R.id.doneFAB);

        cost.setText("Your charges are PKR "+( (Float.parseFloat(CustomerMainScreen.currentUser.getBookingTime())/60)*45 )+" ");

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(info_jazzcash.this);
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

        doneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String num=phoneNumber.getText().toString();
                String mpinNumber=mpin.getText().toString();

                if(num.equals("") || mpinNumber.equals(""))
                {
                    Toast.makeText(info_jazzcash.this, "Fill all required fields.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final AlertDialog.Builder alert=new AlertDialog.Builder(info_jazzcash.this);
                    View mView=getLayoutInflater().inflate(R.layout.custom_dialogue,null);

                    TextView title=mView.findViewById(R.id.DialogueBoxTitle);
                    TextView content=mView.findViewById(R.id.DialogueBoxContent);
                    Button button=mView.findViewById(R.id.DialogueButton);

                    alert.setView(mView);

                    content.setText(bookingDoneMessage);
                    title.setText("Congratulations!");

                    final AlertDialog alertDialog=alert.create();
                    alertDialog.setCanceledOnTouchOutside(false);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            Intent intent=new Intent(info_jazzcash.this,CustomerMainScreen.class);
                            startActivity(intent);
                        }
                    });

                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();
                }


            }
        });
    }
}