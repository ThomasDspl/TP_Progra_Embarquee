package com.example.tp_prog_emba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class Bibliotheque extends AppCompatActivity {
    Context context = this;
    private GameDatasource datasource;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliotheque);
        ImageButton addbutton = findViewById(R.id.imageButton);
        datasource = new GameDatasource(this);
        datasource.open();

        mListView = (ListView) findViewById(R.id.ListView);
        TextView textViewEmptyBiblio = (TextView) findViewById(R.id.textViewEmptyBiblio);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AjoutJeu.class);
                startActivity(intent);
            }
        });

        final ArrayList<String> jeux;
        ArrayList<String> listeAffichage = new ArrayList<>();
        jeux = datasource.getGames("tous");
        int i = 0;
        if (jeux.size() == 0){

            textViewEmptyBiblio.setText("Vous n'avez pas de jeu dans votre bibliothèque.\nAjoutez-en avec le bouton \"+\" ");

        }
        else{
            textViewEmptyBiblio.setText("");

            for (String e : jeux){
                String[] cont;
                cont = e.split("\\|");//cont[1] = la date en texte jcp si on peut resplit apres un split
                String[] datesplit = cont[1].split(" ");
                listeAffichage.add(" Nom : " + cont[0] + " (" + datesplit[2] + ")\n Développeur(s) : " + cont[4]);
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Bibliotheque.this, android.R.layout.simple_list_item_1, listeAffichage){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    View view = super.getView(position,convertView,parent);
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    tv.setPadding(20,40,20,40);
                    if (position%2 == 1){
                        view.setBackgroundColor(Color.parseColor("#e0dedf"));
                        TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);

                        ListItemShow.setTextColor(Color.parseColor("#000000"));

                    }
                    else{
                        view.setBackgroundColor(Color.parseColor("#878386"));
                        TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);

                        ListItemShow.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    return view;
                }
            };

            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String[] cont2;
                    cont2 = jeux.get(position).split("\\|");
                    Intent i = new Intent(context, VueJeu.class);
                    i.putExtra("Nom", cont2[0]);
                    i.putExtra("Date", cont2[1]);
                    i.putExtra("TypeJeu", cont2[2]);
                    i.putExtra("Plateformes", cont2[3]);
                    i.putExtra("Developpeurs", cont2[4]);
                    i.putExtra("Description", cont2[5]);
                    i.putExtra("Url", cont2[6]);

                    startActivity(i);

                }
            });
        }

    }
}
