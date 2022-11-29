package com.xgenius.tp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xgenius.tp.R;

public class MesDipActivity extends AppCompatActivity {

    private ListView lv;
    /*private String[] dip= {"Diplome de reussite au programme de l'informatique",
            "Diplome de reussite au programme de l'informatique"};
    private String[] univ= {"Universite Norbert ZONGO de Koudougou", "Universite Norbert ZONGO de Koudougou"}; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_dip);

       // lv= findViewById(R.id.lv1);
        //MyAdapter adapter= new MyAdapter(this, dip, univ);
       // lv.setAdapter(adapter);
    }

    private class MyAdapter extends ArrayAdapter{
        String[] dipl;
        String[] unive;

        MyAdapter(Context context, String[] dip1, String[] uni1){
            super(context, R.layout.row_dip_attes, R.id.id_dip, dip1);

            this.dipl= dip1;
            this.unive= uni1;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater= (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view= inflater.inflate(R.layout.row_dip_attes, parent, false);

            TextView tv= view.findViewById(R.id.id_dip);
            TextView tv1= view.findViewById(R.id.id_univ);

            tv.setText(dipl[position]);
            tv1.setText(unive[position]);

            return view;
        }
    }
}