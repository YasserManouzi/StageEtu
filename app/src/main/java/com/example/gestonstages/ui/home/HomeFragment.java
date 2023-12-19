package com.example.gestonstages.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestonstages.ActiviteInscription;
import com.example.gestonstages.R;
import com.example.gestonstages.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {

    Button btnConnection;
    TextInputEditText tiet_courriel, tiet_mdp;
    FirebaseAuth firebaseAuth;


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);




        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView tv_inscription = binding.tvInscription;

        btnConnection = root.findViewById(R.id.btn_connexion);
        tiet_courriel = root.findViewById(R.id.tiet_courrielConnexion);
        tiet_mdp = root.findViewById(R.id.tiet_mdpConnexion);

        firebaseAuth = FirebaseAuth.getInstance();
        homeViewModel.getText().observe(getViewLifecycleOwner(), tv_inscription::setText);


        tv_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActiviteInscription.class);
                startActivity(intent);
            }
        });


        btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courriel = tiet_courriel.getText().toString();
                String mdp = tiet_mdp.getText().toString();

                if (Patterns.EMAIL_ADDRESS.matcher(courriel).matches() && mdp.length() >= 10) {
                    firebaseAuth.signInWithEmailAndPassword(courriel, mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser utilisateur = firebaseAuth.getCurrentUser();

                           //     Intent intentActiviteDeconnexion = new Intent(getActivity(), ActiviteDeconnexion.class);
                           //     startActivity(intentActiviteDeconnexion);
                                Toast.makeText(getActivity(), "Connexion réussie", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "Erreur de connexion", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else if (!Patterns.EMAIL_ADDRESS.matcher(courriel).matches()) {
                    tiet_courriel.setError("Format invalide pour le courriel !");
                    tiet_courriel.requestFocus();
                } else {
                    tiet_mdp.setError("ERREUR! Le mot de passe doit contenir au moins 10 caractères");
                    tiet_mdp.requestFocus();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
