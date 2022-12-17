package com.example.projet_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class profil extends AppCompatActivity {
    private int idUtilisateur;
    Utilisateur utilisateur = new Utilisateur();
    private TextView nomUtilisateurTextView;
    private TextView idUtilisateurTextView;
    private TextView emailTextView;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Intent it = getIntent();
        Bundle bundle= it.getExtras();
        idUtilisateur = bundle.getInt("idUtilisateur");
        DBOpenHelper db  = new DBOpenHelper(this);
        utilisateur = db.chercherUtilisateur(idUtilisateur);
        nomUtilisateurTextView = findViewById(R.id.nomUtilisateurTextView);
        nomUtilisateurTextView.setText(utilisateur.getLogin());
        idUtilisateurTextView = findViewById(R.id.IdUtilisateurProfil);
        idUtilisateurTextView.setText("ID: "+utilisateur.getId());
        emailTextView = findViewById(R.id.emailTextView);
        emailTextView.setText(utilisateur.getEmail());
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(profil.this,MainActivity.class);
                startActivity(it);
            }
        });
    }
}