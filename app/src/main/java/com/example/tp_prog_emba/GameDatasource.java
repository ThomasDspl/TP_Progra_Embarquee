package com.example.tp_prog_emba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GameDatasource {

    public static final String GAME_KEY = "id";
    public static final String GAME_NOM= "nom";
    public static final String GAME_ANNEE = "annee";
    public static final String GAME_TYPE = "type";
    public static final String GAME_PLATEFORME = "plateforme";
    public static final String GAME_EDITEUR = "editeur";
    public static final String GAME_TABLE_NAME = "jeux";
    public static final String GAME_DESCRIPTION = "description";
    public static final String GAME_URL = "url";
    public static final String GAME_TABLE_DROP = "DROP TABLE IF EXISTS " + GAME_TABLE_NAME + ";";

    protected SQLiteDatabase mDB = null;
    protected DatabaseHelper mHandler = null;
    protected final static int VERSION = 1;
    protected final static String NOM = "database.db";

    public GameDatasource(Context pContext){
        this.mHandler = new DatabaseHelper(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open(){
        mDB = mHandler.getWritableDatabase();
        return mDB;
    }

    public void close(){mDB.close();}

    public SQLiteDatabase getDb() {return mDB;}

    public long addGame(Game game){
        ContentValues values = new ContentValues();
        values.put(GAME_NOM, game.getNom());
        values.put(GAME_ANNEE, game.getAnnee());
        values.put(GAME_TYPE, game.getType());
        values.put(GAME_PLATEFORME, game.getPlateforme());
        values.put(GAME_EDITEUR, game.getEditeur());
        values.put(GAME_DESCRIPTION, game.getDescription());
        values.put(GAME_URL, game.getUrl());

        return mDB.insert(GAME_TABLE_NAME, null, values);
    }

    public void deleteGames(){
        mDB.execSQL("DROP TABLE IF EXISTS '" + GAME_TABLE_NAME + "'");
        mHandler.onCreate(mDB);
    }

    public void modifGame(String nom, int annee, String type, String plateforme, String editeur, String description, String url){
        mDB.execSQL("UPDATE '" + GAME_TABLE_NAME + "' SET " +
                GAME_NOM + " = '" + nom + "', " +
                GAME_ANNEE + " = '" + annee + "', " +
                GAME_TYPE + " = '" + type + "', " +
                GAME_PLATEFORME + " = '" + plateforme + "', " +
                GAME_EDITEUR + " = '" + editeur + "', " +
                GAME_DESCRIPTION + "= '" + description + "', " +
                GAME_URL + "= '" + url + "'"

        );
    }

    public void modifGameDesc(String nom, String description){
        mDB.execSQL("UPDATE '" + GAME_TABLE_NAME + "' SET " +
                GAME_DESCRIPTION + " = '" + description + "' WHERE nom = '" + nom + "'"
        );
    }

    public void suppGame(String nom){
        mDB.execSQL("DELETE FROM '" + GAME_TABLE_NAME + "' WHERE " + GAME_NOM + " = '" + nom + "'");
    }

    public ArrayList<String> getGames(String typeGAME){
        ArrayList<String> output= new ArrayList<String>();
        String[] colonnesARecup = new String[] {"nom", "annee", "type", "plateforme", "editeur","description", "url"};

        Cursor cursorResults = mDB.query(GAME_TABLE_NAME, colonnesARecup, null, null, null, null, "nom asc", null);
        if (null != cursorResults){
            if(cursorResults.moveToFirst()){
                do{
                    int colIdxNom = cursorResults.getColumnIndex("nom");
                    int colIdxAnnee = cursorResults.getColumnIndex("annee");
                    int colIdxType = cursorResults.getColumnIndex("type");
                    int colIdxPlateforme = cursorResults.getColumnIndex("plateforme");
                    int colIdxEditeur = cursorResults.getColumnIndex("editeur");
                    int colIdxDescription = cursorResults.getColumnIndex("description");
                    int colIdxUrl = cursorResults.getColumnIndex("url");


                    String AllThing = cursorResults.getString(colIdxNom)
                            + "|" + cursorResults.getString(colIdxAnnee)
                            + "|" + cursorResults.getString(colIdxType)
                            + "|" + cursorResults.getString(colIdxPlateforme)
                            + "|" + cursorResults.getString(colIdxEditeur)
                            + "|" + cursorResults.getString(colIdxDescription)
                            + "|" + cursorResults.getString(colIdxUrl);


                    if (typeGAME.equals("tous")){
                        output.add(AllThing);
                    }
                    else{
                        if (cursorResults.getString(colIdxNom).contains(typeGAME)) {
                            output.add(AllThing);
                        }
                    }
                } while(cursorResults.moveToNext());
            }
        }

        cursorResults.close();
        return output;

    }



    public ArrayList<String> searchGame(String nom){
        ArrayList<String> output= new ArrayList<String>();
        String[] colonnesARecup = new String[] {"nom"};

        Cursor cursorResults = mDB.query(GAME_TABLE_NAME, colonnesARecup, "nom = '" + nom + "'", null, null, null, "nom asc", null);
        if (null != cursorResults){
            if(cursorResults.moveToFirst()){
                do{
                    int colIdxNom = cursorResults.getColumnIndex("nom");

                    String AllThing = cursorResults.getString(colIdxNom);
                    output.add(AllThing);

                } while(cursorResults.moveToNext());
            }
        }

        cursorResults.close();
        return output;

    }

    public void deleteGame(String nom){
        mDB.execSQL("DELETE FROM '" + GAME_TABLE_NAME + "' WHERE nom = '" + nom + "'");
    }




    public void suppDatabase(){
        mDB.execSQL(GAME_TABLE_DROP);
    }
}
