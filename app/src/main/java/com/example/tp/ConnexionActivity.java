package com.example.tp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConnexionActivity extends AppCompatActivity {

    private EditText Email, Password;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private TextView CrBtn, passOublie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        mAuth= FirebaseAuth.getInstance();

        Email= findViewById(R.id.Conn_Email);
        Password= findViewById(R.id.Conn_Password);
        CrBtn= findViewById(R.id.join_in);
        passOublie= findViewById(R.id.forgetPass);
        loadingBar= new ProgressDialog(this);

        CrBtn.setOnClickListener(v -> startActivity(new Intent(ConnexionActivity.this, CreerCompteActivity.class)));
        passOublie.setOnClickListener(v -> Toast.makeText(ConnexionActivity.this, "Mot de passe oublié...", Toast.LENGTH_SHORT).show());
    }

    public void CliqueSurBtnConnexion(View view) {
       // startActivity(new Intent(ConnexionActivity.this, MainActivity.class));
        String email= Email.getText().toString();
        String password= Password.getText().toString();

        if (TextUtils.isEmpty(email)){
            Email.setError("Veuillez entrer votre email..!");
            Password.setError("");
        }else if (TextUtils.isEmpty(password)){
            Password.setError("Veuillez enter votre mot de passe..!");
        }else {
            loadingBar.setTitle("Connexion en cours...");
            loadingBar.setMessage("Veuillez patienter");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ConnexionActivity.this, "Connexion reussie...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ConnexionActivity.this, MainActivity.class));
                        finish();
                        loadingBar.dismiss();
                    }else {
                        String Message= task.getException().getMessage();
                        Toast.makeText(ConnexionActivity.this, "Erreur: "+Message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                });

        }
    }
}