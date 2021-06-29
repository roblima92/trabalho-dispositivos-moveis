package com.example.trabalho.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.MyTripsActivity;
import com.example.trabalho.TripDetailsActivity;
import com.example.trabalho.databinding.LayoutMyTripsBinding;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.MyTripsPresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.ModelContract;

import java.util.List;

public class MyTripsAdapter extends RecyclerView.Adapter<MyTripsAdapter.MyTripsViewHolder> {
    private List<ModelContract.Model> tripList;

    public class MyTripsViewHolder extends RecyclerView.ViewHolder {
        public LayoutMyTripsBinding viewMyTrips;

        public MyTripsViewHolder(@NonNull LayoutMyTripsBinding itemView) {
            super(itemView.getRoot());
            this.viewMyTrips = itemView;
        }
    }

    public MyTripsAdapter(List<ModelContract.Model> trips) {
        this.tripList = trips;
    }

    @NonNull
    @Override
    public MyTripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutMyTripsBinding v = null;
        v = LayoutMyTripsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyTripsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTripsViewHolder holder, int position) {
        Trip obj = (Trip) this.tripList.get(position);
        holder.viewMyTrips.setTripModel(obj);
        holder.viewMyTrips.cardMyTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip tripSelected = (Trip) v.getTag();
                Intent intent = new Intent(v.getContext(), TripDetailsActivity.class);
                intent.putExtra("uId", tripSelected.getUid());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.tripList.size();
    }
}