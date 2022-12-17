package com.example.projet_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signUp extends AppCompatActivity {
    private EditText loginEditText, motPassEditText,confirmMotPassEditText, emailEditText;
    private Button signUpButton;
    private RadioGroup statusRadioGroup;
    private RadioButton statusRadioBtn;
    private TextView goToLogin;
    public void checkButton(View view) {
        int radioId = statusRadioGroup.getCheckedRadioButtonId();
        statusRadioBtn = findViewById(radioId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        DBOpenHelper db = new DBOpenHelper(this);
        loginEditText = findViewById(R.id.loginEditText);
        emailEditText = findViewById(R.id.emailEditText);
        motPassEditText = findViewById(R.id.motPassEditText);
        confirmMotPassEditText = findViewById(R.id.confirmMotPassEditText);
        statusRadioGroup = findViewById(R.id.statusRadioGroup);
        signUpButton = findViewById(R.id.signUpButton);
        goToLogin = findViewById(R.id.goToLogin);

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(signUp.this,MainActivity.class);
                startActivity(it);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String motPass = motPassEditText.getText().toString();
                String confirmMotPass = confirmMotPassEditText.getText().toString();
                //String status = statusRadioBtn.getText().toString();
                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                if (db.loginExiste(login)){
                    Toast.makeText(signUp.this, "login deja utilise", Toast.LENGTH_SHORT).show();
                }
                else if (!matcher.matches()){
                    Toast.makeText(signUp.this, "Entrer un email valid", Toast.LENGTH_SHORT).show();
                }
                else if (motPass.length() < 8) {
                    Toast.makeText(signUp.this, "minimum 8 characteres", Toast.LENGTH_SHORT).show();
                }
                else if (!motPass.equals(confirmMotPass)) {
                    Toast.makeText(signUp.this, "confirmer votre mot de passe", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.ajouterUtilisateur(new Utilisateur(login,motPass,email,statusRadioBtn.getText().toString()));
                    Intent it  = new Intent(signUp.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    Log.i("TAG", "onClick: "+login);
                    Log.i("TAG", "onClick: "+motPass);
                    bundle.putString("login",login);
                    bundle.putString("motPass",motPass);
                    it.putExtras(bundle);
                    startActivity(it);
                }

            }
        });
    }


}