package com.example.tp.Profil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    private EditText Profession, Situation, NiveauEtude, DomaineEtude, DateNaissance, Genre;
    private TextView btn_Save;
    private ImageView JoindreDiplom;


    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        Genre= findViewById(R.id.idSexe);
        Profession= findViewById(R.id.idProfession);
        NiveauEtude= findViewById(R.id.idNiveauEtude);
        DomaineEtude= findViewById(R.id.idDomaineEtude);
        DateNaissance= findViewById(R.id.idDateNaissance);
        btn_Save= findViewById(R.id.btn_save);


        loadingBar= new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();

        userRef= FirebaseDatabase.getInstance().getReference().child("Utilisateurs");
        String userId= mAuth.getCurrentUser().getUid();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userId).exists()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void JoindreDiplome(View view) {

        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 1);
    }

    public void Sauvegarder(View view) {

        String profession= Profession.getText().toString();
        String niveauEtude= NiveauEtude.getText().toString();
        String domaineEtude= DomaineEtude.getText().toString();
        String dateDeNaissance= DateNaissance.getText().toString();
        String genre= Genre.getText().toString();

        if (TextUtils.isEmpty(profession)){
            Profession.setError("Veuillez entrer votre profession");
            Situation.setError("");
            NiveauEtude.setError("");
            DomaineEtude.setError("");
            DateNaissance.setError("");

        } else if (TextUtils.isEmpty(niveauEtude)) {
            NiveauEtude.setError("Veuillez entrer votre niveau d'étude");
            DomaineEtude.setError("");
            DateNaissance.setError("");
        }else if (TextUtils.isEmpty(domaineEtude)) {
            DomaineEtude.setError("Veuillez donner votre domaine d'étude");
            DateNaissance.setError("");
        }else if (TextUtils.isEmpty(dateDeNaissance)) {
            DateNaissance.setError("Veuillez entrer votre date de naissance");

        }else if (TextUtils.isEmpty(genre)) {
            Genre.setError("Veuillez donner votre genre");
        }else {
                loadingBar.setTitle("Opération en cours...");
                loadingBar.setMessage("Veuillez patienter");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();


                String userId= mAuth.getCurrentUser().getUid();

                HashMap userMap= new HashMap();
                userMap.put("Profession", profession);
                userMap.put("Niveau d'étude", niveauEtude);
                userMap.put("Domaine d'étude", domaineEtude);
                userMap.put("Date de naissance", dateDeNaissance);
                userMap.put("Genre", genre);

                userRef.child(userId).child("Profil").updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ProfilActivity.this, "Votre profil a été completé", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProfilActivity.this, ProfilActivityAff.class));
                            loadingBar.dismiss();
                        }else {
                            String Message= task.getException().getMessage();
                            Toast.makeText(ProfilActivity.this, "Erreur: "+Message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });

            }
        }
    }
