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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private FirebaseAuth mAuth;
    private TextView recoveryPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.idEmail);
        edtPassword = findViewById(R.id.idPassword);
        TextView signUpLink = findViewById(R.id.signupLink);
        Button btnSignIn = findViewById(R.id.idSignIn);
        recoveryPassword = findViewById(R.id.recoveryPassword);
        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpViewTransition();
            }
        });
        recoveryPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoveryPasswordTransition();
            }
        });
    }

    private void signIn() {
        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Se ingresaron correctamente las credenciales", Toast.LENGTH_LONG).show();
                    ingresoApp();
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(MainActivity.this, RemindersActivity.class);
                    startActivity(intent);
                    //FirebaseUser user = mAuth.getCurrentUser();
                    //System.out.println(user.getUid());
                } else {
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void ingresoApp() {
        Intent intent = new Intent(this, RemindersActivity.class);
        startActivity(intent);
    }

    private void signUpViewTransition() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    private void recoveryPasswordTransition() {
        Intent intent = new Intent(this, recoveryPassword.class);
        startActivity(intent);
    }
}
