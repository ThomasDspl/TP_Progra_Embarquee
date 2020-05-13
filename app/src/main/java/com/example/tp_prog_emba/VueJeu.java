package com.example.tp_prog_emba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class VueJeu extends AppCompatActivity {

    private GameDatasource datasource;
    private String nom;
    private String desc;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_jeu);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_vuejeu);
        setSupportActionBar(myToolbar);
        datasource = new GameDatasource(this);
        datasource.open();

        Intent intent = getIntent();
        String urlimage = intent.getStringExtra("Url");
        String plateformes = intent.getStringExtra("Plateformes");
        String developpeurs = intent.getStringExtra("Developpeurs");
        String date = intent.getStringExtra("Date");
        desc = intent.getStringExtra("Description");

        if (desc.equals("")){
            desc = "Non renseigné";
        }

        nom = intent.getStringExtra("Nom");


        ImageView imageView = (ImageView) findViewById(R.id.imageViewCoverJeu);
        TextView textViewPlateformes = findViewById(R.id.textViewPlateformes);
        TextView textViewDeveloppeurs = findViewById(R.id.textViewDeveloppeurs);
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewNom = findViewById(R.id.textViewNom);
        TextView textViewDesc = findViewById(R.id.textViewDescription);


        Picasso.with(getBaseContext()).load("https://images.igdb.com/igdb/image/upload/t_cover_big/" +  urlimage).resize(400,564).into(imageView);
        textViewNom.setText(Html.fromHtml("<b> " + nom +  " </b>"));

        textViewPlateformes.setText(Html.fromHtml("<b> Plateformes : </b>" + plateformes));
        textViewDeveloppeurs.setText(Html.fromHtml("<b> Développeurs : </b>" + developpeurs));
        textViewDate.setText(Html.fromHtml("<b> Sorti le : </b>" + date));
        textViewDesc.setText(Html.fromHtml("<b> Description : </b>" + desc));




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vuejeu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.suppjeu:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Suppression jeu")
                        .setMessage("Etes-vous sûr de vouloir supprimer ce jeu ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                datasource.deleteGame(nom);
                                Intent i = new Intent(context, Bibliotheque.class);

                                startActivity(i);
                                Toast.makeText(getBaseContext(), "Le jeu " + nom +" a bien été supprimé", Toast.LENGTH_SHORT).show();

                            }

                        })
                        .setNegativeButton("Non", null)
                        .show();
                return true;
            case R.id.modifjeu:
                Intent i = new Intent(context, ModifJeu.class);
                i.putExtra("Nom", nom);
                i.putExtra("Description", desc);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
