package com.expence.em;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView forget;
    EditText loginid,password;
    Button loginButton,registerButton;
    String id,pass ;
    Intent Switcha;

    private void init(){
        loginButton = (Button)  findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.register);
        forget =(TextView) findViewById(R.id.forgot_pass);
        loginid = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utils.blackIconStatusBar(LoginActivity.this,R.color.white);
        init();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            Switcha = new Intent(getApplicationContext(),Dashbord.class);
            Switcha.putExtra("UID",uid);
            startActivity(Switcha);
        }
        loginButton.setOnClickListener(view -> LogIN());
        registerButton.setOnClickListener(view -> Register());
        forget.setOnClickListener(view -> ForgetPass());

    }
    private void LogIN() {
        id = loginid.getText().toString().trim();
        pass = password.getText().toString();
        /*  if(Patterns.EMAIL_ADDRESS.matcher(id).matches()){
            loginid.setError("Enter Valid Email");
            return;
        }*/
        if (TextUtils.isEmpty(id)) {
            loginid.setError("Enter Email");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            password.setError("Enter Password");
            return;
        }
        mAuth.signInWithEmailAndPassword(id,pass).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();//current loged in user
                if (user.isEmailVerified()){
                    String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Switcha = new Intent(getApplicationContext(),Dashbord.class);
                    Switcha.putExtra("UID",uid);
                    startActivity(Switcha);
                }else{
                    user.sendEmailVerification();
                    Toast.makeText(getApplicationContext(),"Check Email For Verification Link",Toast.LENGTH_LONG).show();
                }
            }else {
                try {
                    throw task.getException();
                } catch(FirebaseAuthInvalidCredentialsException e) {
                    loginid.setError("Invalid Id And Password");
                    password.setError("Invalid Id And Password");
                    Log.e("Error", e.getMessage());
                    return;
                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occur", Toast.LENGTH_SHORT).show();
                    Log.e("Error", e.getMessage());
                }
                Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void Register() {
        Switcha = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(Switcha);
    }
    private void ForgetPass() {
        Switcha = new Intent(getApplicationContext(),ForgetPasswordActivity.class);
        startActivity(Switcha);
    }
}