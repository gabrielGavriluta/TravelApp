package com.example.travelapp.recyclerviewImages;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.travelapp.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {
    private Context context;
    private List<Trip> trips;

    public TripAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.trips = trips;
    }


    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trip_layout, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip currentTrip = trips.get(trips.size() - position - 1);
        holder.getDescriptionTextView().setText(currentTrip.getDescription());
        holder.getLocationTextView().setText(currentTrip.getLocation());
        holder.getUserAtLocationTextView().setText(currentTrip.getUser());
        holder.getUserAtDescriptionTextView().setText(currentTrip.getUser());
        Picasso.get().load(currentTrip.getImageUrl()).into(holder.getTripImage());
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }
}
