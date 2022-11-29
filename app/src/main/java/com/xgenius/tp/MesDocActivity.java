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

public class MesDocActivity extends AppCompatActivity {

    private ListView lv;
    private String[] doc ={"Acte de naissance", "Pièce d'identité"};
    private String[] type= {"Pdf", "Pdf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_doc);

       // lv= findViewById(R.id.id_lv);

        AdapterList adapterList= new AdapterList(this, doc, type);
       // lv.setAdapter(adapterList);

    }

    private class AdapterList extends ArrayAdapter{
        Context context;
        String myDoc[];
        String myType[];

          AdapterList(Context context, String[] doc, String[] type){
              super(context, R.layout.row_doc, R.id.id_doc, doc);
              this.myDoc= doc;
              this.myType= type;
          }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater= (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row= inflater.inflate(R.layout.row_doc, parent, false);

            TextView mst= row.findViewById(R.id.id_doc);
            TextView pai= row.findViewById(R.id.id_type);

            mst.setText(myDoc[position]);
            pai.setText(myType[position]);

            return row;
        }
    }
}