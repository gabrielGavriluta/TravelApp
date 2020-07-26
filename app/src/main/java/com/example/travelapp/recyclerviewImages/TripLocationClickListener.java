package com.example.travelapp.recyclerviewImages;

import android.view.View;

public interface TripLocationClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}

