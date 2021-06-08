package com.example.trabalho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.R;
import com.example.trabalho.models.Forecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private List<Forecast> forecastList;

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        public View viewForecast;
        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            this.viewForecast = itemView;
        }
    }

    public ForecastAdapter(List<Forecast> forecasts) {
        this.forecastList = forecasts;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_forecast, parent, false);
        return new ForecastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast obj = (Forecast) this.forecastList.get(position);
        SimpleDateFormat brazilianFormat = new SimpleDateFormat("dd/MM/yyyy");

        TextView date = holder.viewForecast.findViewById(R.id.labelForecastDate);
        TextView pressure = holder.viewForecast.findViewById(R.id.labelForecastPressure);
        TextView humidity = holder.viewForecast.findViewById(R.id.labelForecastHumidity);
        TextView max = holder.viewForecast.findViewById(R.id.labelForecastMax);
        TextView min = holder.viewForecast.findViewById(R.id.labelForecastMin);

        date.setText("Data: " + brazilianFormat.format(obj.getDate()));
        pressure.setText("Pressão: " + obj.getPressure() + " hPa");
        humidity.setText("Umidade: " + obj.getHumidity() + "%");
        max.setText("Máxima de: " + obj.getMax());
        min.setText("Mínima de: " + obj.getMin());
    }

    @Override
    public int getItemCount() {
        return this.forecastList.size();
    }
}