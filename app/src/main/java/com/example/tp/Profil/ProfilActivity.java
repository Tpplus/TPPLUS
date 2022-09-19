package com.example.tp.Profil;

import static android.R.layout.simple_spinner_dropdown_item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp.MainActivity;
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

    private EditText Ville, DateNaiss, Section, Domaine;
    private Spinner NiveauDetu, Sexe;
    private Button btn_Save;
    private ImageView JoindreDiplom;
    private String niveau= "Aucun", sexe= "Masculin";


    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        Ville= findViewById(R.id.ville);
        Sexe= findViewById(R.id.sexe);
        DateNaiss= findViewById(R.id.date_naiss);
        Section= findViewById(R.id.section);
        NiveauDetu= findViewById(R.id.niveau);
        Domaine= findViewById(R.id.domaine);

        String[] niv= {"Niveau d'etude","Aucun", "Niveau primaire", "Niveau secondaire", "Niveau superieur"};
        String[] sex= {"Masculin", "Feminin"};

        loadingBar= new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();

        userRef= FirebaseDatabase.getInstance().getReference().child("Utilisateurs");

        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),
                simple_spinner_dropdown_item, sex);
        Sexe.setAdapter(arrayAdapter);

        Sexe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        sexe= "Masculin";
                        break;
                    case 2:
                        sexe= "Feminin";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getApplicationContext(),
                simple_spinner_dropdown_item , niv);
        NiveauDetu.setAdapter(adapter);

        NiveauDetu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 1:
                        niveau="Aucun";
                        break;
                    case 2:
                        niveau="Niveau primaire";
                        break;
                    case 3:
                        niveau= "Niveau secondaire";
                        break;
                    case 4:
                        niveau= "Niveau superieur";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void Sauvegarder(View view) {

        String ville= Ville.getText().toString();
        String dateDeNaissance= DateNaiss.getText().toString();
        String section= Section.getText().toString();
         String domaineEtude= Domaine.getText().toString();

        if (TextUtils.isEmpty(ville)){
            Ville.setError("Veuillez entrer votre ville");
            DateNaiss.setError("");
            Section.setError("");
            Domaine.setError("");

        }else if (TextUtils.isEmpty(dateDeNaissance)) {
            DateNaiss.setError("Veuillez entrer votre date de naissance");
            Section.setError("");
            Domaine.setError("");
        }else if (TextUtils.isEmpty(section)) {
            Section.setError("Veuillez entrer votre section");
            Domaine.setError("");

        } else if (TextUtils.isEmpty(domaineEtude)) {
                Domaine.setError("Veuillez entrer votre d'étude");
        }else {
                loadingBar.setTitle("Opération en cours...");
                loadingBar.setMessage("Veuillez patienter");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                String userId= mAuth.getCurrentUser().getUid();

                HashMap userMap= new HashMap();
                userMap.put("Ville", ville);
                userMap.put("Genre", sexe);
                userMap.put("Date de naissance", dateDeNaissance);
                userMap.put("Profession", section);
                userMap.put("Niveau d'étude", niveau);
                userMap.put("Domaine d'étude", domaineEtude);

                userRef.child(userId).updateChildren(userMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ProfilActivity.this, "Votre profil a été completé",
                                Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(ProfilActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        loadingBar.dismiss();
                    }else {
                        String Message= task.getException().getMessage();
                        Toast.makeText(ProfilActivity.this, "Erreur: "+Message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                });

            }
        }
    }
