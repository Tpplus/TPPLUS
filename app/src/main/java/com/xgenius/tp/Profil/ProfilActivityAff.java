package com.xgenius.tp.Profil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xgenius.tp.R;
import com.xgenius.tp.Constant.ModifProfil;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivityAff extends AppCompatActivity {

    private TextView nomPrenom, pays, numero, section,
            ville, niveauEtu, domaineEtu, dateNai, email, genre;

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private int gallery_pick= 1;
    private StorageReference ref;
    private String userId, imgUrl;
    private String nom_prenom, ppays, eemail, nnumero, vville, nnivEtud, ddateNaiss,
            ddomainEtud, ssection, ggenre, pImg;
    private CircleImageView profImg;

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
        profImg= findViewById(R.id.profile_image_affi);

        mAuth= FirebaseAuth.getInstance();
        userId= mAuth.getCurrentUser().getUid();

        userRef= FirebaseDatabase.getInstance().getReference().child("Utilisateurs").child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                     nom_prenom= snapshot.child("Nom Prénom").getValue().toString();
                     ppays= snapshot.child("Pays").getValue().toString();
                     eemail= snapshot.child("Email").getValue().toString();
                     nnumero= snapshot.child("Numero").getValue().toString();
                     vville= snapshot.child("Ville").getValue().toString();
                     ggenre= snapshot.child("Genre").getValue().toString();
                     ddateNaiss= snapshot.child("Date de naissance").getValue().toString();
                     ssection= snapshot.child("Profession").getValue().toString();
                     nnivEtud= snapshot.child("Niveau d'étude").getValue().toString();
                     ddomainEtud= snapshot.child("Domaine d'étude").getValue().toString();

                     if (snapshot.child("PhotoProfil").exists()){
                         pImg= snapshot.child("PhotoProfil").getValue().toString();
                         try {
                             Picasso.get().load(pImg).placeholder(R.drawable.profile).into(profImg);
                         }catch (Exception e){}
                     }

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

    public void EditProfil(View view) {
        String[] options= {"La photo de profil","Nom et Prenom","Pays","Email","Numero","Ville",
                "Genre","Date de naissance", "Profession", "Niveau d'étude", "Domaine d'étude"};

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Modification profil");
        builder.setItems(options, (dialogInterface, i) -> {
            switch (i) {
                case 0:
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, gallery_pick);

                    break;
                case 1:
                    new ModifProfil(this, nom_prenom, userRef, options[1], "Nom Prénom");
                    break;
                case 2:
                    new ModifProfil(this, ppays, userRef, options[2], "Pays");
                    break;
                case 3:
                   // new ModifProfil(this, eemail, userRef, options[3], "Email");
                    AlertDialog.Builder dialog= new AlertDialog.Builder(this);
                    dialog.setTitle("Modifier l'email");
                    dialog.setMessage("Veuillez contacter l'administration pour pouvoir modifier votre email");
                    dialog.setNegativeButton("Annuler", (dialogInterface1, i1) -> {

                    }).setPositiveButton("Contacter", (dialogInterface12, i12) -> {
                        Toast.makeText(this, "Désolé, nous ne sommes pas disponible pour le moment!",
                                Toast.LENGTH_SHORT).show();
                    });

                    break;
                case 4:
                    new ModifProfil(this, nnumero, userRef, options[4], "Numero");
                    break;
                case 5:
                    new ModifProfil(this, vville, userRef, options[5], "Ville");
                    break;
                case 6:
                    new ModifProfil(this, ggenre, userRef, options[6], "Genre");
                    break;
                case 7:
                    new ModifProfil(this, ddateNaiss, userRef, options[7], "Date de naissance");
                    break;
                case 8:
                    new ModifProfil(this, ssection, userRef, options[8], "Profession");
                    break;
                case 9:
                    new ModifProfil(this, nnivEtud, userRef, options[9], "Niveau d'étude");
                    break;
                case 10:
                    new ModifProfil(this, ddomainEtud, userRef, options[10], "Domaine d'étude");
                    break;

            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== gallery_pick && resultCode== RESULT_OK && data!= null){

            ProgressDialog dialog= new ProgressDialog(this);
            dialog.setTitle("Changement de photo du profil en cours");
            dialog.setMessage("Veuilllez patienter...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Uri imageUri= data.getData();

            ref= FirebaseStorage.getInstance().getReference()
                    .child("PhotoProfil").child(userId)
                    .child(imageUri.getLastPathSegment()+".JPG");

            ref.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();

                    Toast.makeText(this, "Sauvegarde terminé !", Toast.LENGTH_SHORT).show();
                    imgUrl = downloadUrl.toString();

                    HashMap userMap= new HashMap();
                    userMap.put("PhotoProfil", imgUrl);
                    userRef.updateChildren(userMap).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()){
                            dialog.dismiss();
                        }else {
                            String msg= task1.getException().getMessage();
                            Toast.makeText(this, "Erreur: "+msg,
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });


                }else {
                    String message= task.getException().getMessage();
                    Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }
    }
}