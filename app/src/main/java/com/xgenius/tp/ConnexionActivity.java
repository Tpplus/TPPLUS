package com.xgenius.tp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xgenius.tp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.xgenius.tp.Profil.PassOub;

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

        CrBtn.setOnClickListener(v -> startActivity(
                new Intent(ConnexionActivity.this, CreerCompteActivity.class)));
        passOublie.setOnClickListener(view -> startActivity(
                new Intent(ConnexionActivity.this, PassOub.class)));

    }

    public void CliqueSurBtnConnexion(View view) {
       // startActivity(new Intent(ConnexionActivity.this, MainActivity.class));
        String email= Email.getText().toString().trim();
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
                        Intent intent= new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
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