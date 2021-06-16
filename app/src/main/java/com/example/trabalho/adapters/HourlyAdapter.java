package com.example.trabalho.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.databinding.LayoutHourlyBinding;
import com.example.trabalho.models.Hourly;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {

    private List<Hourly> hourlyList;

    public class HourlyViewHolder extends RecyclerView.ViewHolder {
        public LayoutHourlyBinding viewHourly;

        public HourlyViewHolder(@NonNull LayoutHourlyBinding itemView) {
            super(itemView.getRoot());
            this.viewHourly = itemView;
        }
    }

    public HourlyAdapter(List<Hourly> hourlies) {
        this.hourlyList = hourlies;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutHourlyBinding v = null;
        v = LayoutHourlyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HourlyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapter.HourlyViewHolder holder, int position) {
        Hourly obj = (Hourly) this.hourlyList.get(position);
        holder.viewHourly.setHourlyModel(obj);
        Picasso.get().load("http://openweathermap.org/img/w/"+obj.getWeather().getIcon() + ".png").into(holder.viewHourly.imageView3);
    }

    @Override
    public int getItemCount() {
        return this.hourlyList.size();
    }
}