package com.example.projet_v2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projet_v2.databinding.ActivityPageProjet2Binding;

import java.util.ArrayList;

public class pageProjet2 extends AppCompatActivity {
    private ArrayList<Utilisateur> contributeurs = new ArrayList<Utilisateur>();
    private ActivityPageProjet2Binding binding;
    private LinearLayout linearLayout;
    private EditText githubRepositoryEditText,noteProjetEditText;
    private TextView noteProjetContentTextView, descriptionProjetTextView, titreProjetContentTextView;

    private String titreProjet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPageProjet2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        noteProjetContentTextView = findViewById(R.id.noteProjetContentTextView);
        descriptionProjetTextView = findViewById(R.id.descriptionProjetTextView);
        titreProjetContentTextView = findViewById(R.id.titreProjetContentTextView);

        DBOpenHelper db = new DBOpenHelper(this);
        Intent it = getIntent();
        Bundle bundle= it.getExtras();
        titreProjet = bundle.getString("titreProjet");
        contributeurs = db.chargerListeContributeurs(titreProjet);
        Projet projet = db.chercherProjet(titreProjet);
        titreProjetContentTextView.setText(projet.getTitre());
        descriptionProjetTextView.setText(projet.getDescription());
        noteProjetContentTextView.setText(String.valueOf(projet.getNote())+"/ 100");
        RecyclerView rv=findViewById(R.id.recycle);
        listeContributorRecyclerViewAdapter adapter = new listeContributorRecyclerViewAdapter(this, contributeurs);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        linearLayout = findViewById(R.id.linearLayout);
        githubRepositoryEditText = findViewById(R.id.githubRepositoryEditText);
        noteProjetEditText = findViewById(R.id.noteProjetEditText);

        int idUtilisateur = bundle.getInt("idUtilisateur");
        Utilisateur utilisateur = db.chercherUtilisateur(idUtilisateur);
        if (utilisateur.getStatus().equals("proof")){
            linearLayout.removeView(githubRepositoryEditText);
        }
        else {
            linearLayout.removeView(noteProjetEditText);
        }

    }

    public void ajouterGithubRepository(View view) {
    }

    public void ajouterNoteProjet(View view) {
        DBOpenHelper db =  new DBOpenHelper(this);
        noteProjetEditText = findViewById(R.id.noteProjetEditText);
        db.ajouterNoteProjet(Double.parseDouble(noteProjetEditText.getText().toString()));
    }
}