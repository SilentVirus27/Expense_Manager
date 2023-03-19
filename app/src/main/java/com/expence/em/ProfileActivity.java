package com.expence.em;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    TextView name,email,contact;
    String uname,uemail,ucontact,uuid;
    Button logout;

    FirebaseAuth mAuth;
    private void init(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        init();
        Utils.blackIconStatusBar(this,R.color.light_background);
        Intent pintent=getIntent();
        name=findViewById(R.id.p_et_name);
        email=findViewById(R.id.p_et_email);
        contact=findViewById(R.id.p_et_contactno);
        logout=(Button) findViewById(R.id.logout);

        uname=pintent.getStringExtra("UName");
        uemail=pintent.getStringExtra("UEmail");
        uuid=pintent.getStringExtra("CUUID");
        ucontact=pintent.getStringExtra("UContact");

        name.setText(uname);
        email.setText(uemail);
        contact.setText(ucontact);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                return;
            }
        });
    }
}