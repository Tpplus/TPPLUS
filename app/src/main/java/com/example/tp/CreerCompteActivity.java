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

import com.example.tp.Profil.ProfilActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreerCompteActivity extends AppCompatActivity {

    private EditText Nom, Prenom, Pays, Email, Numero, Password,ConfirPass;
    private ProgressDialog loadingBar;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    private CountryCodePicker ccp;
    private Button BtnConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        userRef= FirebaseDatabase.getInstance().getReference().child("Utilisateurs");
        loadingBar= new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();

        Nom= findViewById(R.id.idNom);
        Prenom= findViewById(R.id.idPrenom);
        Pays= findViewById(R.id.idPays);
        Email= findViewById(R.id.idEmail);
        Numero= findViewById(R.id.idPhone);
        Password= findViewById(R.id.idPassword);
        ConfirPass= findViewById(R.id.idConfPassword);
        BtnConnexion= findViewById(R.id.ConnButton);
        ccp= findViewById(R.id.idContryCode);

        BtnConnexion.setOnClickListener(v -> {
            String nom= Nom.getText().toString();
            String prenom= Prenom.getText().toString();
            String pays= Pays.getText().toString();
            String email= Email.getText().toString().trim();
            String numero= Numero.getText().toString();
            String password= Password.getText().toString();
            String confPass= ConfirPass.getText().toString();

            if (TextUtils.isEmpty(nom)){
                Nom.setError("Veuillez entrer votre nom");
                Prenom.setError("");
                Pays.setError("");
                Email.setError("");
                Numero.setError("");
                Password.setError("");
                ConfirPass.setError("");
            }else if (TextUtils.isEmpty(prenom)){
                Prenom.setError("Veuillez entrer votre pr??nom");
                Pays.setError("");
                Email.setError("");
                Numero.setError("");
                Password.setError("");
                ConfirPass.setError("");
            } else if (TextUtils.isEmpty(pays)) {
                Pays.setError("Veuillez entrer le nom de votre pays");
                Email.setError("");
                Numero.setError("");
                Password.setError("");
                ConfirPass.setError("");
            }else if (TextUtils.isEmpty(email)) {
                Email.setError("Veuillez entrer votre email");
                Numero.setError("");
                Password.setError("");
                ConfirPass.setError("");
            }else if (TextUtils.isEmpty(numero)) {
                Numero.setError("Veuillez entrer num??ro de t??l??phone");
                Password.setError("");
                ConfirPass.setError("");
            }else if (TextUtils.isEmpty(password)) {
                Password.setError("Veuillez entrer num??ro de t??l??phone");
                ConfirPass.setError("");
            }else if (password.length()<6){
                Password.setError("Votre mot de passe doit comporter plus de 6 caract??res");
            } else if (TextUtils.isEmpty(confPass)) {
                ConfirPass.setError("Veuillez entrer num??ro de t??l??phone");
            }else if (!confPass.equals(password)){
                ConfirPass.setError("Votre mot de passe ne correspond pas");
                Password.setError("");
            }else {
                loadingBar.setTitle("Cr??ation du compte en cours...");
                loadingBar.setMessage("Veuillez patientez");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

               ccp.registerCarrierNumberEditText(Numero);
                numero= ccp.getFullNumberWithPlus();

                String finalNumero = numero;
                String nom_prenom= ""+nom+" "+prenom;

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                Toast.makeText(CreerCompteActivity.this, "Authentification reussie!!!", Toast.LENGTH_SHORT).show();
                String userId= mAuth.getCurrentUser().getUid();

                HashMap userMap= new HashMap();
                userMap.put("Nom Pr??nom", nom_prenom);
                userMap.put("Pays", pays);
                userMap.put("Email", email);
                userMap.put("Numero", finalNumero);
                userMap.put("Mot de passe", password);

                userRef.child(userId).updateChildren(userMap).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()){
                Toast.makeText(CreerCompteActivity.this, "Op??ration reussie!!!", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(CreerCompteActivity.this, ProfilActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    loadingBar.dismiss();
                }else {
                        Toast.makeText(CreerCompteActivity.this, "Echec du Cr??ation du compte..!", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        }
                    });
                }else {
                        String Message= task.getException().getMessage();
                        Toast.makeText(CreerCompteActivity.this, "Erreur: "+Message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                     }
                 });
            }
        });


        }

}