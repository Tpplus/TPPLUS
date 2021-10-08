package com.example.tp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerifyActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);

        mAuth= FirebaseAuth.getInstance();
        loadingBar= new ProgressDialog(this);
    }

    public void SendNewVerifyEmail(View view) {
        loadingBar.setTitle("Demande en cours de traitement...");
        loadingBar.setMessage("Veuillez patienter");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        FirebaseUser user= mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(EmailVerifyActivity.this, "L'email de vérification a été envoyé à nouveau...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EmailVerifyActivity.this, ConnexionActivity.class));
                finish();
                loadingBar.dismiss();
            }else {
                String message= task.getException().getMessage();
                Toast.makeText(EmailVerifyActivity.this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}