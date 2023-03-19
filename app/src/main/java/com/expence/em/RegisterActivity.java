package com.expence.em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private LinearLayout layout_register ;
    private Animation animation_fadein;
    private ProgressBar signupProgress;
    private FirebaseAuth mAuth;
    EditText name,email,contactno,password;
    Button cancel,register;
    String mname,memail,mcontactno,mpassword;
    Intent Switcha;


    private void init(){
        cancel = (Button)  findViewById(R.id.cancel);
        register = (Button) findViewById(R.id.register);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        contactno = findViewById(R.id.et_contactno);
        password = findViewById(R.id.et_password);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        init();
        Utils.blackIconStatusBar(this,R.color.light_background);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switcha = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(Switcha);
            }
        });
    }

    private void register() {
        mname=name.getText().toString();
        memail=email.getText().toString().trim();
        mcontactno=contactno.getText().toString();
        mpassword=password.getText().toString();
        if (TextUtils.isEmpty(mname)) {
            name.setError("Please Enter Name");
            return;
        }
        if (TextUtils.isEmpty(memail)) {
            email.setError("Please Enter Email");
            return;
        }
        if(TextUtils.isEmpty(memail) && Patterns.EMAIL_ADDRESS.matcher(memail).matches()){
            email.setError("Enter Valid Email");
            return;
        }
        if (TextUtils.isEmpty(mcontactno)) {
            contactno.setError("Please Enter Contact");
            return;
        }
        if (TextUtils.isEmpty(mpassword)) {
            password.setError("Please Enter Password");
            return;
        }
        if (mpassword.length()<8) {
            password.setError("Minimum Password Length 8 Character");
            return;
        }
        if (mpassword.equals(mname)) {
            password.setError("Password should not contain name");
            return;
        }

        mAuth.createUserWithEmailAndPassword(memail,mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()){
                 FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
                 fuser.sendEmailVerification();
                 String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                 User user = new User(mname,memail,mcontactno);
                 FirebaseDatabase.getInstance().getReference("Users").child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                             Switcha = new Intent(getApplicationContext(),LoginActivity.class);
                             Switcha.putExtra("UNAME",mname);
                             Switcha.putExtra("EMAIL",memail);
                             Switcha.putExtra("Contact",mcontactno);
                             startActivity(Switcha);
                         }else{
                             Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_LONG).show();
                         }
                     }
                 });
             }
             else {
                 try{
                     throw task.getException();
                 }
                 catch (FirebaseAuthUserCollisionException e){
                     email.setError("User Already Exist");
                 }
                 catch (Exception e) {
                     Toast.makeText(getApplicationContext(), "Failed! try again later", Toast.LENGTH_LONG).show();
                     Log.e("Error",e.getMessage());
                 }
                    /*Toast.makeText(getApplicationContext(), "Failed! try again later", Toast.LENGTH_LONG).show();*/

             }
            }
        });
    }
}