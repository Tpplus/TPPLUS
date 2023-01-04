package com.xgenius.tp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.xgenius.tp.R;
import com.xgenius.tp.Profil.ProfilActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class CreerCompteActivity extends AppCompatActivity {

    private TextInputEditText Nom, Prenom, Pays, Email, Numero, Password,ConfirPass;
    private ProgressDialog loadingBar;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

   // private CountryCodePicker ccp;
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
       // ccp= findViewById(R.id.idContryCode);

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

            }else if (TextUtils.isEmpty(prenom)){
                Prenom.setError("Veuillez entrer votre prénom");

            } else if (TextUtils.isEmpty(pays)) {
                Pays.setError("Veuillez entrer le nom de votre pays");

            }else if (TextUtils.isEmpty(email)) {
                Email.setError("Veuillez entrer votre email");

            }else if (TextUtils.isEmpty(numero)) {
                Numero.setError("Veuillez entrer numéro de téléphone");

            }else if (TextUtils.isEmpty(password)) {
                Password.setError("Veuillez entrer votre mot de passe");

            }else if (password.length()<6){
                Password.setError("Votre mot de passe doit comporter plus de 6 caractères");
            } else if (TextUtils.isEmpty(confPass)) {
                ConfirPass.setError("Veuillez confirmer votre mot de passe");
            }else if (!confPass.equals(password)){
                ConfirPass.setError("Votre mot de passe ne correspond pas");

            }else {
                loadingBar.setTitle("Création du compte en cours...");
                loadingBar.setMessage("Veuillez patientez");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

              // ccp.registerCarrierNumberEditText(Numero);
               // numero= ccp.getFullNumberWithPlus();

               // String finalNumero = numero;
                String nom_prenom= ""+nom+" "+prenom;

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                Toast.makeText(CreerCompteActivity.this, "Authentification reussie!!!", Toast.LENGTH_SHORT).show();
                String userId= mAuth.getCurrentUser().getUid();

                HashMap userMap= new HashMap();
                userMap.put("Nom Prénom", nom_prenom);
                userMap.put("Pays", pays);
                userMap.put("Email", email);
                userMap.put("Numero", numero);
                userMap.put("Mot de passe", password);

                userRef.child(userId).updateChildren(userMap).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()){
                Toast.makeText(CreerCompteActivity.this, "Opération reussie!!!", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(CreerCompteActivity.this, ProfilActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    loadingBar.dismiss();
                }else {
                        Toast.makeText(CreerCompteActivity.this, "Echec du Création du compte..!", Toast.LENGTH_SHORT).show();
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