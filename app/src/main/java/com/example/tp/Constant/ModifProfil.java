package com.example.tp.Constant;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class ModifProfil {
    Context context;
    String nom;
    DatabaseReference reference;
    String n, don;

    public ModifProfil() { }

    public ModifProfil(Context context, String nom, DatabaseReference reference, String n, String don ) {
        this.context = context;
        this.nom = nom;
        this.reference = reference;
        this.n= n;
        this.don= don;

        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Modifier "+n);

        LinearLayout layout= new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10,10,10,10);

        EditText editText= new EditText(context);
        editText.setText(nom);
        layout.addView(editText);

        builder.setView(layout);

        builder.setPositiveButton("Modifier", (dialogInterface, i) -> {
            ProgressDialog dialog= new ProgressDialog(context);
            dialog.setTitle("Modification en cours");
            dialog.setMessage("Veuillez patienter...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            String Nom= editText.getText().toString().trim();
            HashMap hashMap= new HashMap();
            hashMap.put(don, Nom);

            reference.updateChildren(hashMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Modification reussie", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    String message= task.getException().getMessage();
                    Toast.makeText(context, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });
        builder.setNegativeButton("Annuler", (dialogInterface, i) -> {

        });
        builder.create().show();
    }
}
