package com.example.tp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp.Fragment.AcceuilFragment;
import com.example.tp.Fragment.AffectationFragment;
import com.example.tp.Fragment.GeneraliteFragment;
import com.example.tp.Profil.ProfilActivity;
import com.example.tp.Profil.ProfilActivityAff;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView userName, userEmail,btnClicAcceuil, btnAcceuil,btnClicGeneralite, btnGeneralite;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private BottomNavigationView bottomNavigationView;
    private ImageView btnAffectation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("BeCome");

        DrawerLayout drawer= findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnAcceuil= findViewById(R.id.tAcceuil);
        btnClicAcceuil= findViewById(R.id.ctAcceuil);
        btnGeneralite= findViewById(R.id.tGene);
        btnClicGeneralite= findViewById(R.id.ctGene);
        btnAffectation= findViewById(R.id.id_affectation);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new AcceuilFragment()).commit();

        btnAcceuil.setOnClickListener(view -> {
            btnClicGeneralite.setVisibility(View.GONE);
            btnGeneralite.setVisibility(View.VISIBLE);

            btnAcceuil.setVisibility(View.GONE);
            btnClicAcceuil.setVisibility(View.VISIBLE);

            replaceFragment(new AcceuilFragment());
        });

       btnGeneralite.setOnClickListener(view -> {

           btnClicAcceuil.setVisibility(View.GONE);
           btnAcceuil.setVisibility(View.VISIBLE);

           btnGeneralite.setVisibility(View.GONE);
           btnClicGeneralite.setVisibility(View.VISIBLE);

           replaceFragment(new GeneraliteFragment());
       });

       btnAffectation.setOnClickListener(view -> {

           btnClicAcceuil.setVisibility(View.GONE);
           btnAcceuil.setVisibility(View.VISIBLE);

           btnClicGeneralite.setVisibility(View.GONE);
           btnGeneralite.setVisibility(View.VISIBLE);

           replaceFragment(new AffectationFragment());
       });

        mAuth= FirebaseAuth.getInstance();
        userName= findViewById(R.id.username);
        userEmail= findViewById(R.id.useremail);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
        case R.id.profil:
            startActivity(new Intent(MainActivity.this, ProfilActivityAff.class));
            break;
            case R.id.mon_compte:
                Toast.makeText(MainActivity.this, "Mon compte", Toast.LENGTH_SHORT).show();
            break;
            case R.id.inscrip:
                Toast.makeText(MainActivity.this, "Inscription", Toast.LENGTH_SHORT).show();
            break;
            case R.id.paiem:
                Toast.makeText(MainActivity.this, "paiement", Toast.LENGTH_SHORT).show();
            break;
            case R.id.archive:
                Toast.makeText(MainActivity.this, "chaines", Toast.LENGTH_SHORT).show();
            break;


    }
    return true; }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
    }

    //Menu des trois points
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.confident:
                Toast.makeText(MainActivity.this, "Confidentialité", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.langue:
                Toast.makeText(MainActivity.this, "Langue", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.parametre:
                Toast.makeText(MainActivity.this, "Paramètres", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.partage:
                Toast.makeText(MainActivity.this, "Partage", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nous_contacter:
                Toast.makeText(MainActivity.this, "Nous contacter", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.autre:
                Toast.makeText(MainActivity.this, "Autres", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.deconnexion:
                mAuth.signOut();
                Intent intent= new Intent(MainActivity.this, ConnexionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser= mAuth.getCurrentUser();

        if (currentUser== null){
            Intent intent= new Intent(MainActivity.this, ConnexionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            String userId= mAuth.getCurrentUser().getUid();
            userRef= FirebaseDatabase.getInstance().getReference().child("Utilisateurs");
            userRef.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.hasChild("Ville")){
                        Intent intent= new Intent(MainActivity.this, ProfilActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}