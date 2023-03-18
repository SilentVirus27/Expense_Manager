package com.expence.em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgetPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email;
    Button cancel,reset;
    String loginid;
    Intent Switcha;

    private void init(){
        email=findViewById(R.id.et_email);
        cancel=(Button) findViewById(R.id.cancel);
        reset=(Button) findViewById(R.id.reset);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mAuth = FirebaseAuth.getInstance();
        init();
        Utils.blackIconStatusBar(this,R.color.light_background);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginid = email.getText().toString();
                if (TextUtils.isEmpty(loginid)) {
                    email.setError("Enter Email");
                    return;
                }
                mAuth.sendPasswordResetEmail(loginid).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Password Reset Link Send Successfully",Toast.LENGTH_LONG).show();
                            Switcha = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(Switcha);
                        }else{
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e){
                                email.setError("Invalid Id");
                            } catch(Exception e) {
                                Log.e("Error", e.getMessage());
                            }
                            Toast.makeText(getApplicationContext(), "failed! Please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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


}