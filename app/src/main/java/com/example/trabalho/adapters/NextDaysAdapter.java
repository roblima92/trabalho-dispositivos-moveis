package com.example.trabalho.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.databinding.LayoutForecastNextDaysBinding;
import com.example.trabalho.models.Forecast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NextDaysAdapter extends RecyclerView.Adapter<NextDaysAdapter.NextDaysAdapterViewHolder> {

    private List<Forecast> nextDaysList;

    public class NextDaysAdapterViewHolder extends RecyclerView.ViewHolder {
        public LayoutForecastNextDaysBinding viewNextDays;

        public NextDaysAdapterViewHolder(@NonNull LayoutForecastNextDaysBinding itemView) {
            super(itemView.getRoot());
            this.viewNextDays = itemView;
        }
    }

    public NextDaysAdapter(List<Forecast> nextDaysList) {
        this.nextDaysList = nextDaysList;
    }

    @NonNull
    @Override
    public NextDaysAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForecastNextDaysBinding v = null;
        v = LayoutForecastNextDaysBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NextDaysAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NextDaysAdapter.NextDaysAdapterViewHolder holder, int position) {
        Forecast obj = (Forecast) this.nextDaysList.get(position);
        holder.viewNextDays.setForecastModel(obj);
        Picasso.get().load("http://openweathermap.org/img/w/"+obj.getWeather().getIcon() + ".png").into(holder.viewNextDays.nextDaysIcon);
    }

    @Override
    public int getItemCount() {
        return this.nextDaysList.size();
    }
}