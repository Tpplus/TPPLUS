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

    private TextView nomPrenom, pays, numero, profession, situationMatri, niveauEtu, domaineEtu, dateNai, genre;

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_aff);

        nomPrenom= findViewById(R.id.pNomPrenom);
        pays= findViewById(R.id.pPays);
        numero= findViewById(R.id.pNumero);
        profession= findViewById(R.id.pProfession);
        situationMatri= findViewById(R.id.pSituationMa);
        niveauEtu= findViewById(R.id.pNiveauEtud);
        domaineEtu= findViewById(R.id.pDomaineEtude);
        dateNai= findViewById(R.id.pDateDeNaiss);
        genre= findViewById(R.id.pSexe);

        mAuth= FirebaseAuth.getInstance();
        String userId= mAuth.getCurrentUser().getUid();



        userRef= FirebaseDatabase.getInstance().getReference().child("Utilisateurs").child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nomPrenom.setText(snapshot.child("Nom Prénom").getValue().toString());
                pays.setText(snapshot.child("Pays").getValue().toString());
                numero.setText(snapshot.child("Numero").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userRef.child("Profil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    profession.setText(snapshot.child("Profession").getValue().toString());
                    situationMatri.setText(snapshot.child("Situation matrimoniale").getValue().toString());
                    niveauEtu.setText(snapshot.child("Niveau d'étude").getValue().toString());
                    domaineEtu.setText(snapshot.child("Domaine d'étude").getValue().toString());
                    dateNai.setText(snapshot.child("Date de naissance").getValue().toString());
                    genre.setText(snapshot.child("Genre").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth= FirebaseAuth.getInstance();
        String userId= mAuth.getCurrentUser().getUid();

        userRef= FirebaseDatabase.getInstance().getReference().child("Utilisateurs").child(userId).child("Profil");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    startActivity(new Intent(ProfilActivityAff.this, ProfilActivity.class));
                    Toast.makeText(ProfilActivityAff.this, "Veuillez completer votre profil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void ModifierLeProfil(View view) {
        startActivity(new Intent(ProfilActivityAff.this, ProfilActivity.class));
    }
}