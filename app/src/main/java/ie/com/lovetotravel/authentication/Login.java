package ie.com.lovetotravel.authentication;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ie.com.lovetotravel.R;
import ie.com.lovetotravel.activities.Home;

public class Login extends AppCompatActivity {

    EditText etEmail, etPass;
    RelativeLayout rally1, rally2;
    Button btnReg, btnLogin;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rally1.setVisibility(View.VISIBLE);
            rally2.setVisibility(View.VISIBLE);
        }
    };

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etEmail = (EditText) findViewById(R.id.et_login_email);
        etPass = (EditText) findViewById(R.id.et_login_pass);
        rally1 = (RelativeLayout) findViewById(R.id.rally1);
        rally2 = (RelativeLayout) findViewById(R.id.rally2);
        btnReg = (Button) findViewById(R.id.btn_to_register);
        btnLogin = (Button) findViewById(R.id.btn_login);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(this, Home.class));
        }

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Email Field is empty", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Password Field is empty", Toast.LENGTH_SHORT).show();
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {

                                    Toast.makeText(Login.this, "Email or Password is invalid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
