package com.example.tp.Profil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilActivityAff extends AppCompatActivity {

    private TextView nomPrenom, pays, numero, section,
            ville, niveauEtu, domaineEtu, dateNai, email, genre;

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_aff);

        nomPrenom= findViewById(R.id.pNomPrenom);
        pays= findViewById(R.id.pPays);
        numero= findViewById(R.id.pNumero);
        section= findViewById(R.id.pSection);
        ville= findViewById(R.id.pVille);
        niveauEtu= findViewById(R.id.pNiveauEtud);
        domaineEtu= findViewById(R.id.pDomaineEtude);
        dateNai= findViewById(R.id.pDateDeNaiss);
        genre= findViewById(R.id.pSexe);
        email= findViewById(R.id.pEmail);

        mAuth= FirebaseAuth.getInstance();
        String userId= mAuth.getCurrentUser().getUid();

        userRef= FirebaseDatabase.getInstance().getReference().child("Utilisateurs").child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nom_prenom= snapshot.child("Nom Prénom").getValue().toString();
                    String ppays= snapshot.child("Pays").getValue().toString();
                    String eemail= snapshot.child("Email").getValue().toString();
                    String nnumero= snapshot.child("Numero").getValue().toString();
                    String vville= snapshot.child("Ville").getValue().toString();
                    String ggenre= snapshot.child("Genre").getValue().toString();
                    String ddateNaiss= snapshot.child("Date de naissance").getValue().toString();
                    String ssection= snapshot.child("Section").getValue().toString();
                    String nnivEtud= snapshot.child("Niveau d'étude").getValue().toString();
                    String ddomainEtud= snapshot.child("Domaine d'étude").getValue().toString();

                    nomPrenom.setText(""+nom_prenom);
                    pays.setText(""+ppays);
                    email.setText(""+eemail);
                    numero.setText(""+nnumero);

                    ville.setText(""+vville);
                    genre.setText(""+ggenre);
                   dateNai.setText(""+ddateNaiss);
                    section.setText(""+ssection);
                    niveauEtu.setText(""+nnivEtud);
                    domaineEtu.setText(""+ddomainEtud);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }});
    }

    public void ModifierLeProfil(View view) {
        startActivity(new Intent(ProfilActivityAff.this, ProfilActivity.class));
    }
}