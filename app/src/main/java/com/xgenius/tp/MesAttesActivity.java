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

public class MesAttesActivity extends AppCompatActivity {

    private ListView lv;
    private String[] att= {"Attestation de reussite au programme de", "Attestation de reussite au programme de",
            "Attestation de reussite au programme de","Attestation de reussite au programme de"};
    private String[] un= {"Universite Norbert ZONGO de Koudougou","Universite Norbert ZONGO de Koudougou",
            "Universite Norbert ZONGO de Koudougou", "Universite Norbert ZONGO de Koudougou"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_attes);

        //lv= findViewById(R.id.lv2);
        MyAdapter adapter= new MyAdapter(this, att, un);
       // lv.setAdapter(adapter);
    }

    private class MyAdapter extends ArrayAdapter {
        String[] atte, uni;

        MyAdapter(Context context, String[] at, String[] unive){
            super(context, R.layout.row_dip_attes, R.id.id_dip, at);
            this.atte= at;
            this.uni= unive;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater= (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view= inflater.inflate(R.layout.row_dip_attes, parent, false);

            TextView tv= view.findViewById(R.id.id_dip);
            TextView tv1= view.findViewById(R.id.id_univ);

            tv.setText(atte[position]);
            tv1.setText(uni[position]);
            return view;
        }
    }
}