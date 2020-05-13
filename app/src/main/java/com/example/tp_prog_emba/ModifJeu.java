package com.example.tp_prog_emba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class ModifJeu extends AppCompatActivity {

    Context context = this;
    private GameDatasource datasource;
    private String nom;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_jeu);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_modifJeu);
        setSupportActionBar(myToolbar);
        datasource = new GameDatasource(this);
        datasource.open();
        EditText editTextDesc = findViewById(R.id.editTextDescModif);
        TextView textViewNom = findViewById(R.id.nomModif);
        Intent intent = getIntent();
        nom = intent.getStringExtra("Nom");
        desc= intent.getStringExtra("Description");
        editTextDesc.setText(desc);
        textViewNom.setText(nom);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modifjeu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.valider:
                EditText editTextDesc = findViewById(R.id.editTextDescModif);
                desc = editTextDesc.getText().toString();

                datasource.modifGameDesc(nom,desc);
                Intent i = new Intent(context, Bibliotheque.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
