package com.example.min_proyecto4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, userName, password;
    private TextView signInLink;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.idEmailSignUp);
        userName = findViewById(R.id.IdUserNameSignUp);
        password = findViewById(R.id.idPasswordSignUp);
        signInLink = findViewById(R.id.idReallyAcount);
        mAuth = FirebaseAuth.getInstance();
        btnSignUp = findViewById(R.id.idSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInViewTransition();
            }
        });
    }

    private void signUp() {
        System.out.println(email.getText().toString());
        System.out.println(userName.getText().toString());
        System.out.println(password.getText().toString());
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Se registro correctamente", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SignUp.this, "Falló registro, intentarlo mas tarde", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void signInViewTransition() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
