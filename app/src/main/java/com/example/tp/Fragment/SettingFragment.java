package com.example.tp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.tp.ConnexionActivity;
import com.example.tp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {

    private CardView Deconnexion;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.setting_fragment, container, false);

        Deconnexion= v.findViewById(R.id.deconnexion);

        mAuth= FirebaseAuth.getInstance();

        Deconnexion.setOnClickListener(v1 -> {
            mAuth.signOut();
            Intent intent= new Intent(getActivity(), ConnexionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return v;
    }
}
