package com.example.gestonstages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gestonstages.ui.slideshow.SlideshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class StageDetailsActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_details);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_accueil) {
                    item.setChecked(true);

                    bottomNavigationView.getMenu().findItem(R.id.menu_recherche).setChecked(false);

                    bottomNavigationView.getMenu().findItem(R.id.menu_recherche).setActionView(new View(getApplicationContext()));



                    return true;
                } else if (item.getItemId() == R.id.menu_recherche) {
                    item.setChecked(true);

                    bottomNavigationView.getMenu().findItem(R.id.menu_accueil).setChecked(false);

                    bottomNavigationView.getMenu().findItem(R.id.menu_accueil).setActionView(new View(getApplicationContext()));

                    startActivity(new Intent(StageDetailsActivity.this, RechercheStage.class));
                    return true;
                }
                return false;
            }
        });




        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();

                if (itemId == R.id.nav_home) {

                    startActivity(new Intent(StageDetailsActivity.this, ActiviteAccueil.class));
                } else if (itemId == R.id.nav_modifierprofile) {

                    startActivity(new Intent(StageDetailsActivity.this, ModificationProfile.class));
                } else if (itemId == R.id.nav_slideshow) {

                    startActivity(new Intent(StageDetailsActivity.this, SlideshowFragment.class));
                }

                drawerLayout.closeDrawers();

                return true;
            }


        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_settings) {
            return true;
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}