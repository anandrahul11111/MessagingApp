package com.example.dx.messageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText username,email,password;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressDialog=new ProgressDialog(this);
        username=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.useremail);
        password=(EditText)findViewById(R.id.userpassword);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void signup(View view){
        progressDialog.setMessage("Signing In.....");
        final String name_content,email_content,password_content;
        name_content=username.getText().toString().trim();
        email_content=email.getText().toString().trim();
        password_content=password.getText().toString().trim();
        if (!TextUtils.isEmpty(name_content) && !TextUtils.isEmpty(email_content) && !TextUtils.isEmpty(password_content)){
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email_content,password_content).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        String user_id=firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db=databaseReference.child(user_id);
                        current_user_db.child("Name").setValue(name_content);
                        startActivity(new Intent(getApplicationContext(),LogIn.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Check your Email or Password !!!",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(),"Username/Email/Password can't be empty !!!",Toast.LENGTH_LONG).show();
        }
    }
}
