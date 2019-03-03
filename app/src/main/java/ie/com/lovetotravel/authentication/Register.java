package ie.com.lovetotravel.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ie.com.lovetotravel.R;
import ie.com.lovetotravel.activities.Home;

public class Register extends AppCompatActivity {

    EditText etEmail, etPass, etPass2;
    Button btnReg;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        etEmail = (EditText) findViewById(R.id.et_reg_email);
        etPass = (EditText) findViewById(R.id.et_reg_password);
        etPass2 = (EditText) findViewById(R.id.et_reg_password_confirm);
        btnReg = (Button) findViewById(R.id.btn_reg);

        mAuth = FirebaseAuth.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPass.getText().toString().trim();
                String password2 = etPass2.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this, "Email Field is empty", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Password Field is empty", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password2)){
                    Toast.makeText(Register.this, "Password Confirmation Field is empty", Toast.LENGTH_SHORT).show();
                }

                if(password.length() < 6){

                    Toast.makeText(Register.this, "Password Too short.  Please enter at least 6 characters", Toast.LENGTH_LONG).show();
                }

                if(password.equals(password2)){

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Intent intent = new Intent(getApplicationContext(), Home.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(Register.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
