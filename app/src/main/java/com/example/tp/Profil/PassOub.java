package com.example.tp.Profil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tp.R;
import com.google.firebase.auth.FirebaseAuth;

public class PassOub extends AppCompatActivity {

    private EditText email;
    private Button btn;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_oub);

        email= findViewById(R.id.idpf_Email);
        btn= findViewById(R.id.idbtnpf);
        loadingBar= new ProgressDialog(this);
        tv= findViewById(R.id.idtv);
        mAuth= FirebaseAuth.getInstance();

        btn.setOnClickListener(view -> {
            String Email= email.getText().toString();
            if (TextUtils.isEmpty(Email)){
                email.setError("Veuillez entrer votre email");
            }else {
                loadingBar.setTitle("Envoie du email d'initialisation");
                loadingBar.setMessage("Veuillez patientez");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                mAuth.sendPasswordResetEmail(Email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(PassOub.this, "Email envoye", Toast.LENGTH_SHORT).show();
                        tv.setVisibility(View.VISIBLE);
                        loadingBar.dismiss();
                    }else {
                        String message= task.getException().getMessage();
                        Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                });
            }
        });
    }
}