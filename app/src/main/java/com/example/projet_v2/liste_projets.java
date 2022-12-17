package com.example.projet_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class liste_projets extends AppCompatActivity implements RecycleViewInterface{
    private ArrayList<Projet> plist = new ArrayList<>();
    private FloatingActionButton btnAjouterProjet;
    private int idUtilisateur;
    private Bundle bundle = new Bundle();

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_projets);
        btnAjouterProjet = findViewById(R.id.fab);
        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        DBOpenHelper db = new DBOpenHelper(this);
        idUtilisateur = bundle.getInt("idUtilisateur");

        Utilisateur utilisateur = db.chercherUtilisateur(idUtilisateur);
        plist = db.chargerListeProjets(utilisateur.getLogin());
        /////
        RecyclerView recyclerView = findViewById(R.id.listeProjets);
        listeProjetsRecycleViewAdapter adapter = new listeProjetsRecycleViewAdapter(this, plist, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnAjouterProjet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(liste_projets.this, ajouter_projet.class);
                bundle.putInt("idUtilisateur", idUtilisateur);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
        ////
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.project:
                    break;

                case R.id.account:
                    Intent intent = new Intent(liste_projets.this, profil.class);
                    bundle.putInt("idUtilisateur",idUtilisateur);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;

            }
            return true;
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent it = new Intent(this,pageProjet2.class);
        bundle.putInt("idUtilisateur",idUtilisateur);
        bundle.putString("titreProjet",plist.get(position).getTitre());
        it.putExtras(bundle);
        startActivity(it);
    }

}