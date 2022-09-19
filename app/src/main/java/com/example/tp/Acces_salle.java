package com.example.tp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Acces_salle extends AppCompatActivity {

    private TextView direct, biblioteque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acces_salle);

        direct = findViewById(R.id.direct);
        biblioteque = findViewById(R.id.biblioteque);

        direct.setOnClickListener(v -> {
            Intent o = new Intent(getApplicationContext(),Direct_salle.class);
            startActivity(o);
        });

        biblioteque.setOnClickListener(v -> {
            Intent o = new Intent(getApplicationContext(),Biblioteque_salle.class);
            startActivity(o);
        });
    }
}