package com.Huzaifa.centralpark;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private ImageView help;
    public static String helpMessage;
    private EditText firstNameSignUp;
    private EditText lastNameSignUp;
    private EditText emailSignUp;
    private EditText passwordSignUp;
    private EditText rePasswordSignUp;
    private Button signUp;
    FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        help=findViewById(R.id.SignUpQuestionMark);
        helpMessage="- Fill in all the required information.\n\n " +
                "- Tap the Sign Up button to head on to the application's main screen.\n";
        firstNameSignUp=findViewById(R.id.signUpFirstName);
        lastNameSignUp=findViewById(R.id.signUpLastName);
        emailSignUp=findViewById(R.id.signUpEmail);
        passwordSignUp=findViewById(R.id.signUpPassword);
        rePasswordSignUp=findViewById(R.id.signUpRePassword);
        signUp=findViewById(R.id.SignUpButton);

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(SignUp.this);
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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname=firstNameSignUp.getText().toString();
                String lname=lastNameSignUp.getText().toString();
                String mail=emailSignUp.getText().toString();
                String passA=passwordSignUp.getText().toString();
                String passB=rePasswordSignUp.getText().toString();

                if(passA.equals(passB) && passA!=null)
                {
                    if(fname.equals("") || lname.equals("") || mail.equals(""))
                    {
                        Toast.makeText(SignUp.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mAuth.createUserWithEmailAndPassword(mail,passA)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        user=mAuth.getCurrentUser();
                                        reference = database.getReference("Users").child(""+user.getUid());

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("myId", ""+user.getUid());
                                        hashMap.put("bookingTime", "0");
                                        hashMap.put("currentBookingSpot", "0");


                                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUp.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        });



                                        Intent intent=new Intent(SignUp.this,CustomerMainScreen.class);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUp.this,"Failed to create User",Toast.LENGTH_LONG).show();
                                    }
                            });
                    }
                }
                else
                {
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}