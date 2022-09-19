package com.example.tp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tp.InscrireActivity;
import com.example.tp.R;

public class AcceuilFragment extends Fragment {

    private Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.acceuil_fragment, container, false);

       // btn= v.findViewById(R.id.id_btn_sinsc);
       // btn.setOnClickListener(view -> startActivity(new Intent(getContext(), InscrireActivity.class)));

        return v;
    }
}
