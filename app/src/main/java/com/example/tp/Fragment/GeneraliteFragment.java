package com.example.tp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.tp.Autres.AideActivity;
import com.example.tp.Autres.NousContacterActivity;
import com.example.tp.ConnexionActivity;
import com.example.tp.MainActivity;
import com.example.tp.OperationActivity;
import com.example.tp.R;
import com.google.firebase.auth.FirebaseAuth;

public class GeneraliteFragment extends Fragment {

    private TextView BtnDec;
    private AlertDialog.Builder dialog;
    private FirebaseAuth mAuth;
    private ListView lv; // A remplacer par RecyclerView
    private TextView contacterBtn, partagerBtn, aideBtn;

    // Ce tableau est juste utiliser pour montrer l'appercu
   /* private String[] master ={"Master-classes 01", "Master-classes 02", "Master-classes 03",
            "Master-classes 04","Master-classes 05", "Master-classes 06", "Master-classes 07",
            "Master-classes 08"};
    private String[] paiement={"(Paiement disponible)", "(Paiement disponible)", "(Paiement disponible)",
            "(Paiement disponible)","(Paiement disponible)", "(Paiement disponible)", "(Paiement disponible)",
            "(Paiement disponible)"}; */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.generalite_fragment, container, false);

        BtnDec= v.findViewById(R.id.btn_dec);
        dialog= new AlertDialog.Builder(getContext());
        //lv= v.findViewById(R.id.id_list_view);
        contacterBtn= v.findViewById(R.id.nous_cont);
        partagerBtn= v.findViewById(R.id.id_part);
        aideBtn= v.findViewById(R.id.id_aid);

        mAuth= FirebaseAuth.getInstance();

       /* MyAdapter adapter= new MyAdapter(getContext(), master, paiement);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i){
                case 0:
                    startActivity(new Intent(getContext(), OperationActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(getContext(), OperationActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(getContext(), OperationActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(getContext(), OperationActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(getContext(), OperationActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(getContext(), OperationActivity.class));
                    break;
                case 6:
                    startActivity(new Intent(getContext(), OperationActivity.class));
                    break;
                case 7:
                    startActivity(new Intent(getContext(), OperationActivity.class));
                    break;
            }
        }); */

        BtnDec.setOnClickListener(view -> {

           dialog.setTitle("Déconnexion");
           dialog.setMessage("Êtes vous sûr de vouloir vous déconnecter ?");
           dialog.setCancelable(false);
           dialog.setNegativeButton("Annuler", (dialogInterface, i) -> {

           }).setPositiveButton("Oui", (dialogInterface, i) -> {
               mAuth.signOut();
               Intent intent= new Intent(getContext(), ConnexionActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);
           });

           dialog.create().show();

        });

        contacterBtn.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), NousContacterActivity.class));
        });

        partagerBtn.setOnClickListener(view -> {
            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Salut je vous conseille d'utiliser BeCome");
            intent.setType("text/plain");
            startActivity(intent);
        });

        aideBtn.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AideActivity.class));
        });

        return v;
    }

    // Cette classe est utiliser pour pouvoir montrer l'appercu sur le listview
    /*class MyAdapter extends ArrayAdapter{
        String[] titleMaster;
        String[] titlePaie;

        public MyAdapter(Context context, String[] master1, String[] paiem){
            super(context, R.layout.row_operation, R.id.id_master_class, master1);

            this.titleMaster= master1;
            this.titlePaie= paiem;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater= (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row= inflater.inflate(R.layout.row_operation, parent, false);

            TextView mst= row.findViewById(R.id.id_master_class);
            TextView pai= row.findViewById(R.id.id_paiement_disp);

            mst.setText(titleMaster[position]);
            pai.setText(titlePaie[position]);

            return row;
        }
    } */
}
