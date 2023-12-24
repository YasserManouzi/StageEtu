package com.example.gestonstages;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ModificationProfile extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    ImageView img_profile;


    TextInputEditText tiet_nom, tiet_courriel, tiet_mdp, tiet_mdpConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_profile);

        tiet_nom = findViewById(R.id.tiet_nom);
        tiet_courriel = findViewById(R.id.tiet_courrielModifier);
        tiet_mdp = findViewById(R.id.tiet_mdpModfier);
        tiet_mdpConfirmation = findViewById(R.id.tiet_mdpConfirmationModfier);
        img_profile = findViewById(R.id.img_profile);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        FirebaseUser utilisateur = mAuth.getCurrentUser();


        tiet_courriel.setText(utilisateur.getEmail());

        if (utilisateur.getDisplayName() != null) {
            tiet_nom.setText(utilisateur.getDisplayName());
            loadProfileImage();




        }


    }

    private void loadProfileImage() {
        FirebaseUser utilisateurActuel = mAuth.getCurrentUser();
        if (utilisateurActuel != null) {
            StorageReference storageRef = storage.getReference();
            StorageReference imageRef = storageRef.child("images/" + utilisateurActuel.getUid() + "/profile_image.jpg");

            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Glide.with(ModificationProfile.this)
                            .load(uri)
                            .placeholder(R.drawable.img_profile)
                            .into(img_profile);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("ModificationProfile", "Erreur lors du chargement de l'image de profil", e);
                }
            });
        }
    }

    private void choisirImageProfil() {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Log.d("ModificationProfile", "Image Uri: " + imageUri.toString());

            ajouterImageProfilFirebase(imageUri);
            img_profile.setImageURI(imageUri);
        } else {
            Log.d("ModificationProfile", "La sélection d'image a échoué.");
        }
    }



    private void ajouterImageProfilFirebase(Uri imageUri) {
        FirebaseUser utilisateurActuel = mAuth.getCurrentUser();

        if (utilisateurActuel != null) {
            StorageReference storageRef = storage.getReference();
            StorageReference imageRef = storageRef.child("images/" + utilisateurActuel.getUid() + "/profile_image.jpg");

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(ModificationProfile.this, "Image de profil ajoutée avec succès.", Toast.LENGTH_SHORT).show();

                                    Glide.with(ModificationProfile.this)
                                            .load(uri)
                                            .placeholder(R.drawable.img_profile)
                                            .into(img_profile);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModificationProfile.this, "Erreur lors de l'ajout de l'image de profil.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void onClickFermer(View view) {
        Intent intentAccueil = new Intent(this, ActiviteAccueil.class);
        startActivity(intentAccueil);
    }

    public void onCliclSauvegarder(View view) {
        FirebaseUser utilisateurActuel = mAuth.getCurrentUser();

        if (utilisateurActuel != null) {
            String nouveauNom = tiet_nom.getText().toString().trim();
            String regexLettresSeulement = "^[a-zA-Z]+$";

            if (!nouveauNom.matches(regexLettresSeulement)) {
                tiet_nom.setError("Le nom ne doit contenir que des lettres et ne doit pas être vide");
                tiet_nom.requestFocus();
                return;
            }

            UserProfileChangeRequest profilAJour = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nouveauNom)
                    .build();

            utilisateurActuel.updateProfile(profilAJour)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "Profil mis à jour avec succès");

                                String nouveauCourriel = tiet_courriel.getText().toString().trim();

                                if (Patterns.EMAIL_ADDRESS.matcher(nouveauCourriel).matches()) {
                                    utilisateurActuel.verifyBeforeUpdateEmail(nouveauCourriel)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("TAG", "Adresse e-mail mise à jour avec succès");

                                                        String nouveauMotDePasse = tiet_mdp.getText().toString().trim();
                                                        String nouveauMotDePasseConfirmation = tiet_mdpConfirmation.getText().toString().trim();

                                                        if (!TextUtils.isEmpty(nouveauMotDePasse)) {
                                                            if (nouveauMotDePasse.length() >= 10) {
                                                                if (!TextUtils.isEmpty(nouveauMotDePasseConfirmation) && nouveauMotDePasseConfirmation.equals(nouveauMotDePasse)) {
                                                                    utilisateurActuel.updatePassword(nouveauMotDePasse)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        Log.d("TAG", "Mot de passe mis à jour avec succès");
                                                                                        Toast.makeText(ModificationProfile.this, "Profil mise à jour avec succès", Toast.LENGTH_SHORT).show();
                                                                                        Intent intent = new Intent(ModificationProfile.this, ActiviteAccueil.class);
                                                                                        startActivity(intent);
                                                                                    } else {
                                                                                        Log.d("TAG", "ERREUR ! Le mot de passe n'a pas pu être mis à jour");
                                                                                    }
                                                                                }
                                                                            });
                                                                } else {
                                                                    tiet_mdpConfirmation.setError("Les mots de passe ne correspondent pas ou le champ est vide");
                                                                    tiet_mdpConfirmation.requestFocus();
                                                                }
                                                            } else {
                                                                tiet_mdp.setError("Le mot de passe est invalide");
                                                            }
                                                        } else {
                                                            tiet_mdp.setError("Veuillez saisir le mot de passe");
                                                            tiet_mdp.requestFocus();
                                                        }
                                                    } else {
                                                        tiet_courriel.setError("Échec de la mise à jour de l'adresse e-mail");
                                                        tiet_courriel.requestFocus();
                                                    }
                                                }
                                            });
                                } else {
                                    tiet_courriel.setError("Format invalide pour le courriel !");
                                    tiet_courriel.requestFocus();
                                }
                            } else {
                                Log.w("TAG", "Échec de la mise à jour du profil", task.getException());
                            }
                        }
                    });
        }
    }





    public void onClickTeleverserImage(View view) {

        choisirImageProfil();
    }
}
