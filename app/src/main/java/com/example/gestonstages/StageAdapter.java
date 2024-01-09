package com.example.gestonstages;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder> {
    private List<Stage> listeStages;
    private OnStageClickListener onStageClickListener; // Ajout de l'interface pour le clic


    public StageAdapter(List<Stage> listeStages, OnStageClickListener  onStageClickListener) {
        this.listeStages = listeStages;
        this.onStageClickListener = onStageClickListener;

    }



    public interface OnStageClickListener {
        void onStageClick(Stage stage);
    }

    @NonNull
    @Override
    public StageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stage, parent, false);
        return new StageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StageViewHolder holder, int position) {
        Stage stage = listeStages.get(position);
        holder.bind(stage);

        Log.d("StageAdapter", "onBindViewHolder: " + position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStageClickListener != null && stage != null) {
                    onStageClickListener.onStageClick(stage);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listeStages.size();
    }

    static class StageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitre;
        private TextView textViewCompagnie;




        public StageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitre = itemView.findViewById(R.id.textViewTitre);
            textViewCompagnie = itemView.findViewById(R.id.textViewCompagnie);


        }

        public void bind(Stage stage) {
            if (textViewTitre != null) {
                textViewTitre.setText(stage.getTitre());
            }
            if (textViewCompagnie != null) {
                textViewCompagnie.setText(stage.getNomCompagnie());

        }
    }
}
    }
