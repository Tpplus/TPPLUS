package com.example.tp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Acces_salle extends AppCompatActivity {

    private Button direct;
    private Button biblioteque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acces_salle);

        direct = findViewById(R.id.direct);
        biblioteque = findViewById(R.id.biblioteque);

        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(getApplicationContext(),Direct_salle.class);
                startActivity(o);
            }
        });

        biblioteque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(getApplicationContext(),Biblioteque_salle.class);
                startActivity(o);
            }
        });
    }
}