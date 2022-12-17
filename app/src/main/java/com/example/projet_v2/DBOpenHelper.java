package com.example.projet_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.metrics.LogSessionId;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "projetDB";
    //TABLE UTILISATEUR
    private static final String TABLE_UTILISATEUR = "t_utilisateur";
    private static final String KEY_ID_UTILISATEUR = "id";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOT_PASS = "motPass";
    private static final String KEY_STATUS = "status";
    private static final String KEY_IMAGEPROFILPATH = "imageProfilPath";
    //TABLE PROJET
    private static final String TABLE_PROJET = "t_projet";
    private static final String KEY_ID_PROJET = "id";
    private static final String KEY_TITRE_PROJET = "titre";
    private static final String KEY_DESCRIPTION_PROJET = "description";
    private static final String KEY_NOTE_PROJET = "note";
    //TABLE RESSOURCE
    private static final String TABLE_RESSOURCE = "t_ressource";
    private static final String KEY_ID_RESSOURCE = "id";
    private static final String KEY_NOM_RESSOURCE = "nom";
    //TABLE CONTRIBUTION
    private static final String TABLE_CONTRIBUTION= "t_contribution";
    private static final String KEY_ID_CONTRIBUTION = "id";
    private static final String KEY_TYPE_CONTRIBUTION = "type";
    //TABLE COMMENTAIRE
    private static final String TABLE_COMMENTAIRE = "t_commentaire";
    private static final String KEY_ID_COMMENTAIRE = "id";
    private static final String KEY_CONTENUE_COMMENTAIRE = "contenue";

    /*
     *   Requetes sql
     */
    private static final String CREATE_TABLE_UTILISATEUR = "CREATE TABLE "+TABLE_UTILISATEUR
            +"("+KEY_ID_UTILISATEUR+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            KEY_LOGIN+" TEXT NOT NULL UNIQUE,"+
            KEY_EMAIL+" TEXT NOT NULL UNIQUE,"+
            KEY_MOT_PASS+" TEXT NOT NULL,"+
            KEY_STATUS+" TEXT NOT NULL,"+
            KEY_IMAGEPROFILPATH+"TEXT)";
    private static final String CREATE_TABLE_PROJET = "CREATE TABLE "+TABLE_PROJET+
            "("+KEY_ID_PROJET+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            KEY_TITRE_PROJET+" TEXT NOT NULL UNIQUE," +
            KEY_DESCRIPTION_PROJET+" TEXT ," +
            KEY_NOTE_PROJET+" REAL)";
    private static final String CREATE_TABLE_RESSOURCE = "CREATE TABLE "+TABLE_RESSOURCE+
            "("+KEY_ID_RESSOURCE+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            KEY_NOM_RESSOURCE+" TEXT NOT NULL UNIQUE," +
            "idProjet INTEGER," +
            "FOREIGN KEY(idProjet) REFERENCES "+TABLE_PROJET+"("+KEY_ID_PROJET+"))";
    private static final String CREATE_TABLE_CONTRIBUTION = "CREATE TABLE "+TABLE_CONTRIBUTION+
            "("+KEY_ID_CONTRIBUTION+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "titreProjet TEXT," +
            "login TEXT," +
            "FOREIGN KEY(titreProjet) REFERENCES "+TABLE_PROJET+"("+KEY_TITRE_PROJET+")," +
            "FOREIGN KEY(login) REFERENCES "+TABLE_UTILISATEUR+"("+KEY_LOGIN+"))";
    private static final String CREATE_TABLE_COMMENTAIRE = "CREATE TABLE "+TABLE_COMMENTAIRE+" " +
            "("+KEY_ID_COMMENTAIRE+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "idProjet INTEGER," +
            KEY_CONTENUE_COMMENTAIRE+" TEXT," +
            "FOREIGN KEY(idProjet) REFERENCES "+TABLE_PROJET+"("+KEY_ID_PROJET+"))";

    public DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_PROJET);
        db.execSQL(CREATE_TABLE_RESSOURCE);
        db.execSQL(CREATE_TABLE_CONTRIBUTION);
        db.execSQL(CREATE_TABLE_COMMENTAIRE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMMENTAIRE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTRIBUTION);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_RESSOURCE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROJET);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_UTILISATEUR);
        onCreate(db);
    }

    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOGIN,utilisateur.getLogin());
        values.put(KEY_MOT_PASS,utilisateur.getMotPass());
        values.put(KEY_EMAIL,utilisateur.getEmail());
        values.put(KEY_STATUS,utilisateur.getStatus());
        db.insert(TABLE_UTILISATEUR,null,values);
        db.close();
        return utilisateur;
    }
    public Utilisateur chercherUtilisateur(Utilisateur utilisateur){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+KEY_ID_UTILISATEUR+","+KEY_LOGIN+","+KEY_MOT_PASS+","+KEY_EMAIL+" FROM "+TABLE_UTILISATEUR+" WHERE "+KEY_LOGIN+"=? AND "+KEY_MOT_PASS+"=?",new String[]{utilisateur.getLogin(),utilisateur.getMotPass()});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return new Utilisateur(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID_UTILISATEUR)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOGIN)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOT_PASS)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)));
        }
        return null;
    }
    public Utilisateur chercherUtilisateur(int idUtilisateur){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+KEY_ID_UTILISATEUR+","+KEY_LOGIN+","+KEY_MOT_PASS+","+KEY_EMAIL+","+KEY_STATUS+" FROM "+TABLE_UTILISATEUR+" WHERE "+KEY_ID_UTILISATEUR+"=?",new String[]{String.valueOf(idUtilisateur)});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return new Utilisateur(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID_UTILISATEUR)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOGIN)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOT_PASS)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS)));
        }
        return null;
    }

    public Utilisateur chercherUtilisateur(String emailUtilisateur){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+KEY_ID_UTILISATEUR+","+KEY_LOGIN+","+KEY_MOT_PASS+","+KEY_EMAIL+","+KEY_STATUS+" FROM "+TABLE_UTILISATEUR+" WHERE "+KEY_EMAIL+"=?",new String[]{emailUtilisateur});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return new Utilisateur(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID_UTILISATEUR)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOGIN)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOT_PASS)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS)));
        }
        return null;
    }

    public boolean loginExiste(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_UTILISATEUR+" WHERE "+KEY_LOGIN+"=?",new String[]{login});
        if (cursor.getCount() != 0){
            return  true;
        }
        return  false;
    }
    public Projet chercherProjet(String titreProjet){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PROJET+" WHERE "+KEY_TITRE_PROJET+"=?",new String[]{titreProjet});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return new Projet(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID_PROJET)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITRE_PROJET)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION_PROJET)),cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_NOTE_PROJET)));
        }
        return null;
    }
    public void ajouterNoteProjet(Double note){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_PROJET,note);
        db.insert(TABLE_UTILISATEUR,null,values);
        db.close();
    }
    public boolean projtExiste(String nomProjet) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PROJET+" WHERE "+KEY_TITRE_PROJET+"=?",new String[]{nomProjet});
        if (cursor.getCount() != 0){
            return  true;
        }
        return  false;
    }
    public ArrayList<Projet> chargerListeProjets(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT titreProjet FROM "+TABLE_CONTRIBUTION+" WHERE login=?",new String[]{login});
        ArrayList<String> titreProjets = new ArrayList<String>();

        while(cursor.moveToNext()){
            titreProjets.add(cursor.getString(cursor.getColumnIndexOrThrow("titreProjet")));
        }
        ArrayList<Projet> projets = new ArrayList<>();
        for (String titre : titreProjets){
            projets.add(chercherProjet(titre));
        }

        return projets;
    }
    public ArrayList<Utilisateur> chargerListeContributeurs(String titreProjet){
        Log.i("test", "chargerListeContributeurs: "+titreProjet);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT login FROM "+TABLE_CONTRIBUTION+" WHERE titreProjet=?",new String[]{titreProjet});
        ArrayList<String> logins = new ArrayList<String>();

        while(cursor.moveToNext()){
            logins.add(cursor.getString(cursor.getColumnIndexOrThrow("login")));
        }

        ArrayList<Utilisateur> contributeurs = new ArrayList<>();
        Utilisateur utilisateur ;
        for (String login : logins){
            cursor = db.rawQuery("SELECT "+KEY_ID_UTILISATEUR+","+KEY_LOGIN+","+KEY_MOT_PASS+","+KEY_EMAIL+","+KEY_STATUS+" FROM "+TABLE_UTILISATEUR+" WHERE "+KEY_LOGIN+"=?",new String[]{login});
            while (cursor.moveToNext()){
                utilisateur = new Utilisateur(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID_UTILISATEUR)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOGIN)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOT_PASS)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS)));
                contributeurs.add(utilisateur);
                Log.i("test", "chargerListeContributeurs: "+utilisateur.getLogin());
            }

        }
        Log.i("test", "from chargerListeContributeurs: ");
        for (Utilisateur u : contributeurs){
            Log.i("test", "chargerListeContributeurs: "+u.getLogin());
        }
        db.close();
        return contributeurs;
    }
    public Projet ajouterProjet(Projet projet) {

        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITRE_PROJET,projet.getTitre());
        values.put(KEY_DESCRIPTION_PROJET,projet.getDescription());
        db.insert(TABLE_PROJET,null,values);

        db.close();
        return projet;
    }
    public void ajouterContributtion(String login,String titreProjet){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("login",login);
        values.put("titreProjet",titreProjet);
        db.insert(TABLE_CONTRIBUTION,null,values);
        db.close();
    }
}

