package com.example.gestonstages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gestonstages.ui.slideshow.SlideshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class RechercheStage extends AppCompatActivity implements StageAdapter.OnStageClickListener{


    private ArrayList<Stage> listeStages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_stage);
        Log.d("RechercheStage", "onCreate");


        listeStages = new ArrayList<>();
        listeStages.add(new Stage("Stage Hiver Develpper Full Stack", "Ex-Programming", "Montréal", "Technique informatique", "Description 1"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));
        listeStages.add(new Stage("Titre 2", "Compagnie 2", "Adresse 2", "Exigences 2", "Description 2"));


        RecyclerView recyclerView  = findViewById(R.id.recyclerViewStages);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StageAdapter adapter = new StageAdapter(listeStages, this);



        recyclerView.setAdapter(adapter);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_accueil) {
                    item.setChecked(false);

                    bottomNavigationView.getMenu().findItem(R.id.menu_recherche).setActionView(new View(getApplicationContext()));

                 startActivity(new Intent(RechercheStage.this, ActiviteAccueil.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_recherche) {
                    item.setChecked(false);

                    bottomNavigationView.getMenu().findItem(R.id.menu_accueil).setActionView(new View(getApplicationContext()));


                    return true;
                }
                return false;
            }
        });

        bottomNavigationView.getMenu().findItem(R.id.menu_accueil).setActionView(new View(getApplicationContext()));
        bottomNavigationView.getMenu().findItem(R.id.menu_recherche).setActionView(new View(getApplicationContext()));

        bottomNavigationView.getMenu().findItem(R.id.menu_recherche).setChecked(true);



    }



    @Override
    public void onStageClick(Stage stage) {
        Log.d("StageAdapter", "Item clicked: " + stage.getTitre());


        Intent intent = new Intent(this, StageDetailsActivity.class);
        intent.putExtra("stage", stage);
        Log.d("StageAdapter", "Intent created: " + intent);
        startActivity(intent);

    }

}