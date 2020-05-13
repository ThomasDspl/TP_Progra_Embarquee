package com.example.tp_prog_emba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String GAME_KEY = "id";
    public static final String GAME_NOM= "nom";
    public static final String GAME_ANNEE = "annee";
    public static final String GAME_TYPE = "type";
    public static final String GAME_PLATEFORME = "plateforme";
    public static final String GAME_EDITEUR = "editeur";
    public static final String GAME_DESCRIPTION = "description";
    public static final String GAME_URL = "url";


    public static final String GAME_TABLE_NAME = "jeux";
    public static final String GAME_TABLE_CREATE =
            "CREATE TABLE " + GAME_TABLE_NAME + " (" +
                    GAME_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GAME_NOM + " TEXT, " +
                    GAME_ANNEE + " TEXT, " +
                    GAME_TYPE + " TEXT, " +
                    GAME_PLATEFORME + " TEXT, " +
                    GAME_EDITEUR + " TEXT, " +
                    GAME_DESCRIPTION +" TEXT, "+
                    GAME_URL +" TEXT"+
                    ");";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(GAME_TABLE_CREATE);}

    public static final String GAME_TABLE_DROP = "DROP TABLE IF EXISTS " + GAME_TABLE_NAME + ";";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(GAME_TABLE_DROP);
        onCreate(db);
    }





}
