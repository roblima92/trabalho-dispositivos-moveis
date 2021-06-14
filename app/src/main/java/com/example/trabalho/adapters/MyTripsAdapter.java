package com.example.trabalho.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.databinding.ShowMyTripsBinding;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.contracts.ModelContract;

import java.util.List;

public class MyTripsAdapter extends RecyclerView.Adapter<MyTripsAdapter.MyTripsViewHolder> {
    private List<ModelContract.Model> tripList;

    public class MyTripsViewHolder extends RecyclerView.ViewHolder {
        public ShowMyTripsBinding viewMyTrips;

        public MyTripsViewHolder(@NonNull ShowMyTripsBinding itemView) {
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
        ShowMyTripsBinding v = null;
        v = ShowMyTripsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyTripsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTripsViewHolder holder, int position) {
        Trip obj = (Trip) this.tripList.get(position);
        holder.viewMyTrips.setTripModel(obj);
    }

    @Override
    public int getItemCount() {
        return this.tripList.size();
    }
}