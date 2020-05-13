package com.example.tp_prog_emba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AjoutJeu extends AppCompatActivity {
    private JSONArray reponsejeu;
    private JSONObject coverInfos = null;
    private JSONArray platformsInfos = null;
    private JSONArray involvedCompaniesInfos = null;
    private JSONArray releaseDates = null;



    private String urlObject;
    final String API = "a35fcfb9e4816dff598a83045d116f65";
    private RequestQueue queue;
    Context context = this;
    private GameDatasource datasource;

    private String nom;
    int annee;
    private String type;
    private String editeur;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_jeu);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_ajoutjeu);
        setSupportActionBar(myToolbar);
        datasource = new GameDatasource(this);
        datasource.open();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ajoutjeu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ajoutjeu:
                String nomInput = ((EditText)findViewById(R.id.editTextNom)).getText().toString();
                nom = nomInput.substring(0, 1).toUpperCase() + nomInput.substring(1);

                type = ((Spinner)findViewById(R.id.spinnerType)).getSelectedItem().toString();
                description = ((EditText)findViewById(R.id.editTextDescription)).getText().toString();

                if (nom.equals("") || type.equals("")){
                    Toast.makeText(getBaseContext(), "Un ou plusieurs champs sont vides. Réessayez.", Toast.LENGTH_SHORT).show();
                }
                else{
                    ArrayList<String > verif;
                    verif = datasource.searchGame(nom);
                    if (verif.size()==0){


                        /**
                         * APPEL API
                         */
                        queue = Volley.newRequestQueue(this);
                        String url = "https://api-v3.igdb.com/games?search=" + nom + "&fields=cover.url,platforms.name,release_dates.date,involved_companies.developer,involved_companies.company.name&limit=1";

                        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONArray>()
                                {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        // display response
                                        Log.d("Response", response.toString());
                                        reponsejeu = response;

                                        try {
                                            StringBuilder plateformes = new StringBuilder();
                                            StringBuilder developpeurs = new StringBuilder();
                                            String dateText;

                                            JSONObject jeu = reponsejeu.getJSONObject(0);
                                            JSONObject temp = null;
                                            JSONObject temp2 = null;
                                            Long minDate = (Long) new Date().getTime();


                                            coverInfos = jeu.getJSONObject("cover");
                                            platformsInfos = jeu.getJSONArray("platforms");
                                            involvedCompaniesInfos = jeu.getJSONArray("involved_companies");
                                            releaseDates = jeu.getJSONArray("release_dates");

                                            for (int i = 0; i<involvedCompaniesInfos.length(); i++){
                                                temp = involvedCompaniesInfos.getJSONObject(i);

                                                if (temp.getString("developer").equals("true")){
                                                    if (!developpeurs.toString().equals("")){
                                                        developpeurs.append(", ");
                                                    }
                                                    temp2 = temp.getJSONObject("company");
                                                    developpeurs.append(temp2.getString("name"));
                                                }
                                            }

                                            Long dateMinUnix = System.currentTimeMillis();

                                            for (int i = 0; i<releaseDates.length(); i++){
                                                temp = releaseDates.getJSONObject(i);
                                                Long dateunix = Long.MAX_VALUE;

                                                if (temp.has("date")){
                                                    dateunix = temp.getLong("date");
                                                }

                                                if (dateunix < dateMinUnix){
                                                    dateMinUnix = dateunix;
                                                }

                                            }

                                            Date date = new Date(dateMinUnix*1000);
                                            SimpleDateFormat df2 = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE);
                                            dateText = df2.format(date);
                                            Log.d("datetext", "datetext " + dateText);


                                            for (int i = 0; i<platformsInfos.length(); i++){
                                                temp = platformsInfos.getJSONObject(i);
                                                if (!plateformes.toString().equals("")){
                                                    plateformes.append(", ");
                                                }
                                                plateformes.append(temp.getString("name"));

                                            }


                                            String urlcover = coverInfos.getString("url");
                                            String[] splittedUrl = urlcover.split("/");
                                            urlObject = splittedUrl[splittedUrl.length-1];

                                            Game g = new Game(nom, dateText, type, plateformes.toString(), developpeurs.toString(), description, urlObject);

                                            datasource.addGame(g);

                                            Intent intent = new Intent(context, Bibliotheque.class);
                                            startActivity(intent);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Error.Response", error.toString());
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("user-key", API);
                                return params;
                            }

                        };

                        MySingleton.getInstance(this).addToRequestQueue(getRequest);

                        /**
                         * FIN APPEL API
                         */
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Le jeu est déjà dans votre bibliothèque.", Toast.LENGTH_SHORT).show();
                    }
                }


                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
