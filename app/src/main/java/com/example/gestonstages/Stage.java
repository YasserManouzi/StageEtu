package com.example.gestonstages;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Stage implements Parcelable {

    String titre;

    String nomCompagnie;

    String adresse;

    String exigences;

    String description;

    public Stage() {
    }

    public Stage(String titre, String nomCompagnie, String adresse, String exigences, String description) {
        this.titre = titre;
        this.nomCompagnie = nomCompagnie;
        this.adresse = adresse;
        this.exigences = exigences;
        this.description = description;
    }


    protected Stage(Parcel in) {
        titre = in.readString();
        nomCompagnie = in.readString();
        adresse = in.readString();
        exigences = in.readString();
        description = in.readString();
    }

    public static final Creator<Stage> CREATOR = new Creator<Stage>() {
        @Override
        public Stage createFromParcel(Parcel in) {
            return new Stage(in);
        }

        @Override
        public Stage[] newArray(int size) {
            return new Stage[size];
        }
    };

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNomCompagnie() {
        return nomCompagnie;
    }

    public void setNomCompagnie(String nomCompagnie) {
        this.nomCompagnie = nomCompagnie;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getExigences() {
        return exigences;
    }

    public void setExigences(String exigences) {
        this.exigences = exigences;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(titre);
        dest.writeString(nomCompagnie);
        dest.writeString(adresse);
        dest.writeString(exigences);
        dest.writeString(description);
    }
}
