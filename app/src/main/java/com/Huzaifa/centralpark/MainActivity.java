package com.Huzaifa.centralpark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ImageView help;
    private Button logIn;
    private FloatingActionButton signUp;
    private EditText logInEmail;
    private EditText logInPassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        help=findViewById(R.id.LogInQuestionMark);
        logIn=findViewById(R.id.LogInButton);
        signUp=findViewById(R.id.signUp_fab);
        logInEmail=findViewById(R.id.logInEmail);
        logInPassword=findViewById(R.id.logInPassword);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                View mView=getLayoutInflater().inflate(R.layout.custom_dialogue,null);

                TextView title=mView.findViewById(R.id.DialogueBoxTitle);
                TextView content=mView.findViewById(R.id.DialogueBoxContent);
                Button button=mView.findViewById(R.id.DialogueButton);

                alert.setView(mView);

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

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mail=logInEmail.getText().toString();
                final String pass=logInPassword.getText().toString();

                if(mail.equals("admin@gmail.com") && pass.equals("admin123"))
                {
                    Toast.makeText(MainActivity.this, "Welcome to admin mode", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,AdminMainScreen.class);
                    startActivity(intent);
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(mail,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "Sign Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(MainActivity.this,CustomerMainScreen.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
    }
}