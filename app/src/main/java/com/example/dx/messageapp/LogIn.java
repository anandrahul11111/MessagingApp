package com.example.dx.messageapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    private EditText logmail,logpassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mdata;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        progressDialog=new ProgressDialog(this);
        logmail=(EditText)findViewById(R.id.maillog);
        logpassword=(EditText)findViewById(R.id.passwordlog);
        mAuth=FirebaseAuth.getInstance();
        mdata= FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void userlog(View view){
        progressDialog.setMessage("Logging In.....");
        final String email,userpassword;
        email=logmail.getText().toString().trim();
        userpassword=logpassword.getText().toString().trim();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(userpassword)){
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        final String user_id=mAuth.getCurrentUser().getUid();
                        mdata.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(user_id)){
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Check your Email or Password !!!",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Email/Password can't be empty !!!",Toast.LENGTH_LONG).show();
        }
    }

    public void sign(View view){
        startActivity(new Intent(getApplicationContext(),Registration.class));
    }
}
