package com.xgenius.tp.Fragment;

import static android.R.layout.simple_spinner_dropdown_item;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.xgenius.tp.R;
import com.xgenius.tp.Acces_salle;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class AffectationFragment extends Fragment {
    private EditText code_salle,matricule;
    private TextView acceder;

    //private RecyclerView recycler_all;
    //private DatabaseReference mCartOneDataBase;
    private CardView cv;
    private LinearLayout rla, rla1, rla2, rla3, rla4;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.affectation_fragment, container, false);


       /* mCartOneDataBase = FirebaseDatabase.getInstance().getReference().child("CatOne DataBase");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recycler_all.setHasFixedSize(true);
        recycler_all.setLayoutManager(layoutManager); */

        code_salle = v.findViewById(R.id.code_salle);
        matricule = v.findViewById(R.id.matricule);
        acceder = v.findViewById(R.id.acceder);

        rla= v.findViewById(R.id.rl0);
        rla1= v.findViewById(R.id.rl1);
        rla2= v.findViewById(R.id.rl2);
        rla3= v.findViewById(R.id.rl3);
        rla4= v.findViewById(R.id.rl4);
        cv= v.findViewById(R.id.cvvv);
        spinner= v.findViewById(R.id.id_spinner);

        String[] affectations={"Aucune affectation"};

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(),
                simple_spinner_dropdown_item , affectations);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        cv.setVisibility(View.GONE);
                        rla.setVisibility(View.VISIBLE);
                        rla1.setVisibility(View.GONE);
                        rla2.setVisibility(View.GONE);
                        rla3.setVisibility(View.GONE);
                        rla4.setVisibility(View.GONE);
                        break;
                   /* case 1:
                        rla.setVisibility(View.GONE);
                        rla1.setVisibility(View.VISIBLE);
                        rla2.setVisibility(View.GONE);
                        rla3.setVisibility(View.GONE);
                        rla4.setVisibility(View.GONE);
                        cv.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        rla.setVisibility(View.GONE);
                        rla1.setVisibility(View.GONE);
                        rla2.setVisibility(View.VISIBLE);
                        rla3.setVisibility(View.GONE);
                        rla4.setVisibility(View.GONE);
                        cv.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        rla.setVisibility(View.GONE);
                        rla1.setVisibility(View.GONE);
                        rla2.setVisibility(View.GONE);
                        rla3.setVisibility(View.VISIBLE);
                        rla4.setVisibility(View.GONE);
                        cv.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        rla.setVisibility(View.GONE);
                        rla1.setVisibility(View.GONE);
                        rla2.setVisibility(View.GONE);
                        rla3.setVisibility(View.GONE);
                        rla4.setVisibility(View.VISIBLE);
                        cv.setVisibility(View.VISIBLE);
                        break;  */
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // appel a l action button
        acceder.setOnClickListener(v1 -> {

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

        });

        return v;
    }

    // fonction acces

    private void acces() {}



    @Override
    public void onStart() {
        super.onStart();

      /*  FirebaseRecyclerAdapter<Data,catOneViewHolder> adapterone = new FirebaseRecyclerAdapter<Data, catOneViewHolder>
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

        recycler_all.setAdapter(adapterone); */

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
