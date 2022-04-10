package com.example.tp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp.Acces_salle;
import com.example.tp.Model.Data;
import com.example.tp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class AffectationFragment extends Fragment {
    private EditText code_salle,matricule;
    private Button acceder;

    private RecyclerView recycler_all;
    private DatabaseReference mCartOneDataBase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.affectation_fragment, container, false);


        mCartOneDataBase = FirebaseDatabase.getInstance().getReference().child("CatOne DataBase");
        recycler_all = v.findViewById(R.id.recycler_all);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recycler_all.setHasFixedSize(true);
        recycler_all.setLayoutManager(layoutManager);

        code_salle = v.findViewById(R.id.code_salle);
        matricule = v.findViewById(R.id.matricule);
        acceder = v.findViewById(R.id.acceder);

        // appel a l action button

        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codee = code_salle.getText().toString().trim();
                String accede = acceder.getText().toString().trim();

                if (TextUtils.isEmpty(codee)){
                    code_salle.setError("Veuillez remplir le champs");
                    return;

                }else if (TextUtils.isEmpty(accede)) {
                    acceder.setError("Veuillez remplir le champs");
                    return;
                }

                Toast.makeText(getActivity(), "Acces a la salle reuissie", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getActivity(), Acces_salle.class);
                startActivity(i);



            }
        });

        return v;
    }

    // fonction acces

    private void acces() {



    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Data,catOneViewHolder> adapterone = new FirebaseRecyclerAdapter<Data, catOneViewHolder>
                (
                        Data.class,
                        R.layout.item_data,
                        catOneViewHolder.class,
                        mCartOneDataBase

                ) {
            @Override
            protected void populateViewHolder(catOneViewHolder ViewHolder, Data model, int i) {

                ViewHolder.setImage(model.getImage());
                ViewHolder.setDescription(model.getDescription());
                ViewHolder.setTitle(model.getTitle());

            }
        };

        recycler_all.setAdapter(adapterone);

    }


    public static class catOneViewHolder extends RecyclerView.ViewHolder{
            View v;

        public catOneViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
        }

        public void setTitle(String title){

            TextView mTitle = v.findViewById(R.id.title);
            mTitle.setText(title);

        }


        public void setDescription(String description){

            TextView mDescription = v.findViewById(R.id.description);
            mDescription.setText(description);

        }


        public void setImage(String image){
            ImageView mImage = v.findViewById(R.id.imageView);
            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(mImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                    Picasso.get().load(image).into(mImage);

                }
            });


        }
    }

}
