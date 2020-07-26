package com.example.travelapp.tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapp.LoginActivity;
import com.example.travelapp.R;
import com.example.travelapp.recyclerviewImages.RecyclerTouchListener;
import com.example.travelapp.recyclerviewImages.Trip;
import com.example.travelapp.recyclerviewImages.TripAdapter;
import com.example.travelapp.recyclerviewImages.TripLocationClickListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverTripsFragment extends Fragment {
    private RecyclerView recyclerViewDiscoverTrips;
    private TripAdapter tripAdapter;
    private DatabaseReference databaseReference;
    private List<Trip> discoverTrips;
    private StorageReference storageReference;
    public DiscoverTripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover_trips, container, false);
        recyclerViewDiscoverTrips = view.findViewById(R.id.recyclerViewDiscoverTrips);

        recyclerViewDiscoverTrips.setHasFixedSize(true);
        recyclerViewDiscoverTrips.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDiscoverTrips.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                recyclerViewDiscoverTrips, new TripLocationClickListener() {
            @Override
            public void onClick(View view, int position) {
                final TextView locationTextView = view.findViewById(R.id.locationTextView);
                locationTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(locationTextView.getText() != null && locationTextView.getText().toString().length() > 0){
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                            mapIntent.setData(Uri.parse("geo:0,0?q=" + locationTextView.getText().toString()));
                            mapIntent.setPackage("com.google.android.apps.maps");
                            Objects.requireNonNull(getActivity()).startActivity(mapIntent);
                        }
                    }
                });
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        discoverTrips = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Trips");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot postSnapshot : snapshot.getChildren()){
                    Trip trip = postSnapshot.getValue(Trip.class);
                    if(trip != null && !trip.getUser().equals(LoginActivity.USER)) {
                        discoverTrips.add(trip);
                    }
                }
                tripAdapter = new TripAdapter(getContext(), discoverTrips);
                recyclerViewDiscoverTrips.setAdapter(tripAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
