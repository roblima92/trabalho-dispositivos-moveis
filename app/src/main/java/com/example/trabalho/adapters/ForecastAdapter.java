package com.example.trabalho.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.databinding.LayoutTripBinding;
import com.example.trabalho.models.Forecast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private List<Forecast> forecastList;

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        public LayoutTripBinding viewForecast;

        public ForecastViewHolder(@NonNull LayoutTripBinding itemView) {
            super(itemView.getRoot());
            this.viewForecast = itemView;
        }
    }

    public ForecastAdapter(List<Forecast> forecasts) {
        this.forecastList = forecasts;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutTripBinding v = null;
        v = LayoutTripBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ForecastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast obj = (Forecast) this.forecastList.get(position);
        holder.viewForecast.setForecastModel(obj);
        Picasso.get().load("http://openweathermap.org/img/w/"+obj.getWeather().getIcon() + ".png").resize(200, 200).into(holder.viewForecast.tripDepartureIcon);
    }

    @Override
    public int getItemCount() {
        return this.forecastList.size();
    }
}