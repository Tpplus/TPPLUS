package com.example.tp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp.Profil.ProfilActivity;
import com.example.tp.Profil.ProfilActivityAff;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView postList;
    private Toolbar mToolBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageView refresh_btn;
    private TextView decouvrir, offre, formation;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView= findViewById(R.id.navigation_view);
        View navView= navigationView.inflateHeaderView(R.layout.navigation_header);
        mToolBar= findViewById(R.id.main_app_toolbar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("TP+");

        drawerLayout = findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationItemSelectide(item);
                return false;
            }

        });

        mAuth= FirebaseAuth.getInstance();



    }

    private void navigationItemSelectide(MenuItem item) {

        switch (item.getItemId()){
            case R.id.profil:
                startActivity(new Intent(MainActivity.this, ProfilActivityAff.class));
                break;
            case R.id.programme:
                Toast.makeText(this, "programme cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sinscrire:
                Toast.makeText(this, "s'inscrire cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.paiement:
                Toast.makeText(this, "paiement cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_message:
                Toast.makeText(this, "message cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.notification:
                Toast.makeText(this, "notifications cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.historique:
                Toast.makeText(this, "historique cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.assistance:
                Toast.makeText(this, "assistance cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.evaluations:
                Toast.makeText(this, "evaluations cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.salles:
                Toast.makeText(this, "Salles cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.emploi_temps:
                Toast.makeText(this, "Emploi du temps cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.invitation:
                Toast.makeText(this, "invitation cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.documents:
                Toast.makeText(this, "documents cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reglement:
                Toast.makeText(this, "reglements cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contacter:

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        //Permettre la gestion des item du deuxième menu(trois points à droite)
        switch (item.getItemId()){
            case R.id.mon_compte:
                Toast.makeText(this, "mon compte cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.confidentialite:
                Toast.makeText(this, "confidentialité cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.langue:
                Toast.makeText(this, "Langue cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.parametre:
                Toast.makeText(this, "Paramètre cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.suivez_nous:
                Toast.makeText(this, "suivez nous cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.partager:
                Toast.makeText(this, "Partager cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signaler:
                Toast.makeText(this, "signaler problème cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mise_a_jour:
                Toast.makeText(this, "mise à jour cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.a_propos:
                Toast.makeText(this, "A propos cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deconnxion:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {

        FirebaseUser user= mAuth.getCurrentUser();
        super.onStart();
        if (user== null && !user.isEmailVerified()){
            Toast.makeText(this, "Veuillez vérifier votre email...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, EmailVerifyActivity.class));
            finish();
        }
    }

}