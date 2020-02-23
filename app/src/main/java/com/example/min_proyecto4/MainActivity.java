package com.example.min_proyecto4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    private EditText edtEmail, edtUserName, edtPassword;
    private Button  btnSignIn;
    private TextView signUpLink;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inputs
        edtEmail = findViewById(R.id.idEmail);
//        edtUserName = findViewById(R.id.idUserName);
        edtPassword = findViewById(R.id.idPassword);
        signUpLink = findViewById(R.id.signupLink);
        // botones
        btnSignIn = findViewById(R.id.idSignIn);

        //
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //siguiente actividad
            ingresoApp();

        }
    }
//    private void signUp() {
//        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "Autenticación Correcta", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Falló de autenticación", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//    }
    private void signIn () {
        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Se ingresaron correctament las credenciales", Toast.LENGTH_LONG).show();
                    ingresoApp();
                } else {
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void ingresoApp() {

        Intent intent = new Intent(this, ingresoAppTest.class);
        startActivity(intent);
    }
    private void signUpViewTransition() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
