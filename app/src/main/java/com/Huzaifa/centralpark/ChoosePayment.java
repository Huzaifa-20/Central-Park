package com.Huzaifa.centralpark;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChoosePayment extends AppCompatActivity {

    private CircleImageView help;
    private String helpMessage;
    private ImageView backArrow;
    private CardView jazzCash;
    private CardView payPal;
    private CardView creditCard;
    private FloatingActionButton doneFab;
    int option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment);


        help=findViewById(R.id.choosePaymentQuestionMark);
        helpMessage="- Tap on any one payment option card.\n\n " +
                "- Once your desired payment option is highlighted.\n\n" +
                "- Tap the notepad circular green button at the bottom to proceed to payment procedure.\n";
        backArrow=findViewById(R.id.back_arrow_choose_payment);
        jazzCash=findViewById(R.id.jazzCash);
        payPal=findViewById(R.id.payPal);
        creditCard=findViewById(R.id.creditCard);
        doneFab=findViewById(R.id.doneFAB);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(ChoosePayment.this);
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

        jazzCash.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                option=1;
                jazzCash.setCardBackgroundColor(getResources().getColor(R.color.selected));
                payPal.setCardBackgroundColor(getResources().getColor(R.color.unselected));
                creditCard.setCardBackgroundColor(getResources().getColor(R.color.unselected));
            }
        });

        payPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option=2;
                payPal.setCardBackgroundColor(getResources().getColor(R.color.selected));
                jazzCash.setCardBackgroundColor(getResources().getColor(R.color.unselected));
                creditCard.setCardBackgroundColor(getResources().getColor(R.color.unselected));
            }
        });

        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option=3;
                creditCard.setCardBackgroundColor(getResources().getColor(R.color.selected));
                jazzCash.setCardBackgroundColor(getResources().getColor(R.color.unselected));
                payPal.setCardBackgroundColor(getResources().getColor(R.color.unselected));
            }
        });

        doneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option==1){
                    Intent intent=new Intent(ChoosePayment.this,info_jazzcash.class);
                    startActivity(intent);
                }
                else if(option==2){
                    Intent intent=new Intent(ChoosePayment.this,info_paypal.class);
                    startActivity(intent);
                }
                else if(option==3){
                    Intent intent=new Intent(ChoosePayment.this,info_creditcard.class);
                    startActivity(intent);
                }
            }
        });
    }
}