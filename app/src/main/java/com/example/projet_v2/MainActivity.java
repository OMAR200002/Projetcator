package com.example.projet_v2;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText loginEditText, motPassEditText;
    private TextView goToSignUp;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBOpenHelper db = new DBOpenHelper(this);
        loginButton = findViewById(R.id.loginButton);
        goToSignUp  = findViewById(R.id.goToSignUp);
        loginEditText = findViewById(R.id.loginEditText);
        motPassEditText = findViewById(R.id.motPassEditText);
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it  = new Intent(MainActivity.this,signUp.class);
                startActivity(it);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginEditText.getText().toString();
                String motPass = motPassEditText.getText().toString();
                Utilisateur utilisateur = new Utilisateur(login,motPass);
                utilisateur = db.chercherUtilisateur(utilisateur);
                if( utilisateur != null){
                    Intent it = new Intent(MainActivity.this,liste_projets.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idUtilisateur",utilisateur.getId());
                    it.putExtras(bundle);
                    startActivity(it);
                }
                else {
                    Toast.makeText(MainActivity.this, "Cree un compte", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}