package com.example.projet_v2;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ajouter_projet extends AppCompatActivity {
    ArrayList<Contributor> contributors = new ArrayList<>();
    Button addcontributor;
    LinearLayout ll1;
    LinearLayout ll2;
    Button addgithub;
    Button savebtn;
    EditText titreProjet;
    private int idUtilisateur;
    private Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_projet);
        DBOpenHelper db = new DBOpenHelper(this);
        savebtn = findViewById(R.id.savebtn);
        titreProjet = findViewById(R.id.title);
        EditText instructions =findViewById(R.id.instructions);
        ll1=findViewById(R.id.contributorcontainer);
        addcontributor=findViewById(R.id.contributors);
        Intent it = getIntent();
        bundle = it.getExtras();
        idUtilisateur = bundle.getInt("idUtilisateur");
        addcontributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouterContributeurview(ll1);
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkIfValidAndRead() && !db.projtExiste(titreProjet.getText().toString()) && valid()){
                    Projet projet = new Projet(titreProjet.getText().toString(),instructions.getText().toString());
                    projet = db.ajouterProjet(projet);
                    Log.i("TAG", "onClick: "+projet.toString());
                    //Ajouter les contributeurs
                    for (Contributor c : contributors){
                        Utilisateur utilisateur = db.chercherUtilisateur(c.getEmail());
                        db.ajouterContributtion(utilisateur.getLogin(),projet.getTitre());
                    }
                    Utilisateur utilisateur = db.chercherUtilisateur(idUtilisateur);
                    db.ajouterContributtion(utilisateur.getLogin(),projet.getTitre());
                    Intent it = new Intent(ajouter_projet.this,liste_projets.class);
                    bundle.putInt("idUtilisateur",idUtilisateur);
                    it.putExtras(bundle);
                    startActivity(it);
                }


            }
        });
    }
    void ajouterContributeurview(LinearLayout ll1){
        View view=getLayoutInflater().inflate(R.layout.champ_contributeurs,null);
        ll1.addView(view);
        Button btn2=view.findViewById(R.id.delete);
        btn2.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                removeView(view,ll1);
            }
        });}


    private void removeView(View v,LinearLayout ll1){
        ll1.removeView(v);
    }

    private boolean valid(){
        boolean result =true;
        if(titreProjet.getText().toString()=="") result =false;
        return result;
    }

    private boolean checkIfValidAndRead() {
        contributors.clear();
        boolean result = true;

        for(int i=0;i<ll1.getChildCount();i++){

            View contributorView = ll1.getChildAt(i);

            EditText editTextName = (EditText)contributorView.findViewById(R.id.editTextTextPersonName);

            Contributor contributor =new Contributor();
            if(!editTextName.getText().toString().equals("")){
                contributor.setEmail(editTextName.getText().toString());
            }else {
                result = false;
                break;
            }



            contributors.add(contributor);

        }

        if(contributors.size()==0){
            result = false;
            Toast.makeText(this, "add contributors", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }
}