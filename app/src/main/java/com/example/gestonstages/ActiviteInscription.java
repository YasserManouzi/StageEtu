package com.example.gestonstages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.gestonstages.ui.gallery.GalleryFragment;
import com.example.gestonstages.ui.home.HomeFragment;
import com.example.gestonstages.ui.slideshow.SlideshowFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class ActiviteInscription extends AppCompatActivity {
    TextInputEditText tiet_courriel, tiet_mdp, tiet_mdpConfirmation;
    FirebaseAuth firebaseAuth;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_inscription);
        tiet_courriel = findViewById(R.id.tiet_courriel_inscription);
        tiet_mdp = findViewById(R.id.tiet_mdpInscription);
        tiet_mdpConfirmation = findViewById(R.id.tiet_confirmationMdp);


        firebaseAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();

                if (itemId == R.id.nav_home) {

                    startActivity(new Intent(ActiviteInscription.this, HomeFragment.class));
                } else if (itemId == R.id.nav_gallery) {

                    startActivity(new Intent(ActiviteInscription.this, GalleryFragment.class));
                } else if (itemId == R.id.nav_slideshow) {

                    startActivity(new Intent(ActiviteInscription.this, SlideshowFragment.class));
                }


                return true;
            }


        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gérer les actions de l'icône de la barre d'action (le bouton de menu)
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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