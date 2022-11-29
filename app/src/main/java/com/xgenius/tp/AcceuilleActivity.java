package com.xgenius.tp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class AcceuilleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuille);

        new Handler().postDelayed(() -> {
            Intent intent= new Intent(AcceuilleActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 5000);
    }
}