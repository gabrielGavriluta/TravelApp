package com.example.travelapp.recyclerviewImages;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.travelapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TripViewHolder extends RecyclerView.ViewHolder {
    private TextView locationTextView;
    private TextView descriptionTextView;
    private TextView userAtDescriptionTextView;
    private TextView userAtLocationTextView;
    private ImageView tripImage;

    public TripViewHolder(@NonNull View itemView) {
        super(itemView);
        locationTextView = itemView.findViewById(R.id.locationTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        tripImage = itemView.findViewById(R.id.tripImage);
        userAtLocationTextView = itemView.findViewById(R.id.userAtLocationTextView);
        userAtDescriptionTextView = itemView.findViewById(R.id.userAtDescriptionTextView);
    }
    public TextView getUserAtDescriptionTextView() {
        return userAtDescriptionTextView;
    }

    public void setUserAtDescriptionTextView(TextView userAtDescriptionTextView) {
        this.userAtDescriptionTextView = userAtDescriptionTextView;
    }

    public TextView getUserAtLocationTextView() {
        return userAtLocationTextView;
    }

    public void setUserAtLocationTextView(TextView userAtLocationTextView) {
        this.userAtLocationTextView = userAtLocationTextView;
    }
    public TextView getLocationTextView() {
        return locationTextView;
    }

    public void setLocationTextView(TextView locationTextView) {
        this.locationTextView = locationTextView;
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public void setDescriptionTextView(TextView descriptionTextView) {
        this.descriptionTextView = descriptionTextView;
    }

    public ImageView getTripImage() {
        return tripImage;
    }

    public void setTripImage(ImageView tripImage) {
        this.tripImage = tripImage;
    }
}
