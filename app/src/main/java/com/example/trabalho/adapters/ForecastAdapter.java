package com.example.trabalho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.R;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.databinding.ShowForecastBinding;

import java.text.SimpleDateFormat;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private List<Forecast> forecastList;

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        public ShowForecastBinding viewForecast;

        public ForecastViewHolder(@NonNull ShowForecastBinding itemView) {
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
        ShowForecastBinding v = null;
        v = ShowForecastBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ForecastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast obj = (Forecast) this.forecastList.get(position);
        holder.viewForecast.setForecastModel(obj);
    }

    @Override
    public int getItemCount() {
        return this.forecastList.size();
    }
}