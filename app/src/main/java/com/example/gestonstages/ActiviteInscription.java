package com.example.gestonstages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class ActiviteInscription extends AppCompatActivity {
    TextInputEditText tiet_courriel, tiet_mdp, tiet_mdpConfirmation;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_inscription);
        tiet_courriel = findViewById(R.id.tiet_courriel_inscription);
        tiet_mdp = findViewById(R.id.tiet_mdpInscription);
        tiet_mdpConfirmation = findViewById(R.id.tiet_confirmationMdp);


        firebaseAuth = FirebaseAuth.getInstance();



    }


    public void onClickConnexion(View view) {
        Intent intentActiviteConnexion = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentActiviteConnexion);


    }

    public void onclickSeConnecter(View view) {
        String courriel  = tiet_courriel.getText().toString();
        String mdp = tiet_mdp.getText().toString();
        String mdpConfirmation = tiet_mdpConfirmation.getText().toString();

        if(Patterns.EMAIL_ADDRESS.matcher(courriel).matches()) {
            if(mdp.matches(mdpConfirmation) && mdp.length() >= 10) {
                InputMethodManager imn = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imn.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                firebaseAuth.createUserWithEmailAndPassword(courriel,mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Utilisateur créee", Toast.LENGTH_LONG).show();
                            FirebaseUser utilisateur = firebaseAuth.getCurrentUser();
                            if(utilisateur != null) {
                                Intent intentActiviteConnexion = new Intent(getApplicationContext(), MainActivity.class);

                                startActivity(intentActiviteConnexion);
                                finish();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Erreur d'authentification", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else if(mdp.length() < 10){
                tiet_mdp.setError("ERREUR! Le mot de passe doit contenir au moins 10 caractère");
                tiet_mdp.requestFocus();
            }
            else {
                tiet_mdpConfirmation.setError("Le mot de passe n'est pas le même !");
                tiet_mdpConfirmation.requestFocus();
            }
        }else {
            tiet_courriel.setError("Format invalide pour le courriel !");
            tiet_courriel.requestFocus();

        }

    }

    public void onClickChk_affichageMdp(View v){
        boolean checked = ((CheckBox) v).isChecked();
        if (checked) {

            tiet_mdp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            tiet_mdpConfirmation.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } else {
            tiet_mdp.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            tiet_mdpConfirmation.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);


        }
    }
}