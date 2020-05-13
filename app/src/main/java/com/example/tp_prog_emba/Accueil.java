package com.example.tp_prog_emba;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Accueil  extends AppCompatActivity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        Button BBJ = findViewById(R.id.BBJ);


        BBJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Bibliotheque.class);
                startActivity(intent);
            }
        });
    }
}
