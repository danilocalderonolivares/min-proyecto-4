package com.example.min_proyecto4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class recoveryPassword extends AppCompatActivity {
    private Button cancel, recovery;
    private EditText email;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        mAuth = FirebaseAuth.getInstance();
        email =  findViewById(R.id.idEmailRecovery);
        cancel =  findViewById(R.id.backBtn);
        recovery = findViewById(R.id.recoveryPassword);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTransition();
            }
        });
        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    recoveryPassword();
            }
        });
    }
    private void loginTransition() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void recoveryPassword() {
        mAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(recoveryPassword.this, "Se ha enviado una contrasenna nueva a su correo", Toast.LENGTH_LONG).show();
                FirebaseUser user = mAuth.getCurrentUser();
                Intent intent = new Intent(recoveryPassword.this, MainActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(recoveryPassword.this, "Se ha fallado el cambio de contrasenna intentelo otra vez", Toast.LENGTH_LONG).show();
            }
        });
    }
}
