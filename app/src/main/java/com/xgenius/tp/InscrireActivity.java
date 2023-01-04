package com.xgenius.tp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.xgenius.tp.R;

public class InscrireActivity extends AppCompatActivity {
    private TextInputEditText nom,prenom,date,genre,pays;
    private Button abandon,continuer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincrire);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        date = findViewById(R.id.date);
        genre = findViewById(R.id.genre);
        pays = findViewById(R.id.pays);
        abandon = findViewById(R.id.abandon);
        continuer = findViewById(R.id.continuer);

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Infos();
            }
        });

        abandon.setOnClickListener(v -> {

        });
    }

    private void Infos() {
        String namme = nom.getText().toString();
        String prennom = prenom.getText().toString();
        String datte = date.getText().toString();
        String gennre = genre.getText().toString();
        String payss = pays.getText().toString();

        if (TextUtils.isEmpty(namme) ){
            nom.setError("Veuillez saisir votre nom");
            return;
        } else  if (TextUtils.isEmpty(prennom) ){
            prenom.setError("Veuillez saisir votre prenom");
            return;
        } else  if (TextUtils.isEmpty(datte) ){
            date.setError("Veuillez saisir votre date de naissance");
            return;
        }else  if (TextUtils.isEmpty(gennre) ){
            genre.setError("Veuillez saisir votre genre");
            return;
        }else  if (TextUtils.isEmpty(payss) ){
            pays.setError("Veuillez saisir votre pays");
            return;
        }

        Intent i = new Intent(getApplicationContext(),Confirmation_activity.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(), "Acces reussie", Toast.LENGTH_SHORT).show();

    }
}