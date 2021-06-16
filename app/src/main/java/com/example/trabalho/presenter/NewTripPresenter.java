package com.example.trabalho.presenter;


import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.trabalho.NewTripActivity;
import com.example.trabalho.TripDetailsActivity;
import com.example.trabalho.databinding.ActivityNewTripBinding;
import com.example.trabalho.models.Address;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.LocationGeo;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.contracts.ActivityContract;

import com.example.trabalho.presenter.contracts.ModelContract;
import com.example.trabalho.presenter.contracts.RequestForecastContract;
import com.example.trabalho.presenter.contracts.RequestLocationContract;
import com.example.trabalho.services.Location;
import com.example.trabalho.services.OpenWeather;
import com.example.trabalho.utils.Validate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTripPresenter implements ActivityContract.ActivityFormPresenter, RequestLocationContract.RequestLocationPresenter, RequestForecastContract.RequestForecastPresenter {

    private Trip trip = new Trip();
    private List<Forecast> forecastDestination = new ArrayList<>();
    private List<Forecast> forecasthome = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LocationGeo locationGeo;
    private ActivityContract.ActivityView tripView;
    public ActivityNewTripBinding newTripBinding;
    private static int contRequests = 0;

    public NewTripPresenter(ActivityContract.ActivityView tripView) {
        this.tripView = tripView;
        Location location = new Location((NewTripActivity) tripView, this);
        location.getLastLocation();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void submit(ModelContract.Model tripModel) {
        this.trip = (Trip) tripModel;
        this.trip.setUserUid(mAuth.getCurrentUser().getUid());
        try {
            this.validate();
            OpenWeather openWeatherHome = new OpenWeather(this, tripView.getContext(), "home");
            openWeatherHome.startByCity(trip.getHomeCountry(), trip.getHomeCity());

            OpenWeather openWeatherDestination = new OpenWeather(this, tripView.getContext(), "destination");
            openWeatherDestination.startByCity(trip.getVisitedCountry(), trip.getVisitedCity());
        } catch (Exception e) {
            this.tripView.showToast(e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void validate() throws Exception {
        Validate.fieldIsRequired(newTripBinding.newTripDepartureDateInput, "Data de partida");
        Validate.fieldIsRequired(newTripBinding.newTripArrivalDateInput, "Data de chegada");
        Validate.fieldIsRequired(newTripBinding.newTripDestinationCityInput, "Cidade, País");
        if (trip.getDepartureDate() == null || !Validate.stringIsDateValid(newTripBinding.newTripDepartureDateInput.getText().toString())) {
            throw new Exception ("Data de partida inválida!");
        }
        if (trip.getArrivalDate() == null || !Validate.stringIsDateValid(newTripBinding.newTripArrivalDateInput.getText().toString())) {
            throw new Exception ("Data de chegada inválida!");
        }
        if (trip.getReturnDate() != null && !Validate.stringIsDateValid(newTripBinding.newTripReturnDateInput.getText().toString())) {
            throw new Exception ("Data de retorno inválida!");
        } else if (trip.getReturnDate() != null) {
            Validate.dateCantBeGreaterThanAnotherDate(trip.getArrivalDate(), trip.getReturnDate(), "Data de retorno não pode ser anterior a data de chegada!");
        }
        Validate.dateCantBeGreaterThanAnotherDate(trip.getDepartureDate(), trip.getArrivalDate(), "Data de chegada não pode ser anterior a data de partida!");
        Validate.dateCantBeGreaterThanAnotherDate(new Date(System.currentTimeMillis()-24*60*60*1000), trip.getDepartureDate(), "Data de partida não pode ser anterior a data de hoje!");
        Validate.dateOnLimit(trip.getDepartureDate(), "Data de partida");
        Validate.dateOnLimit(trip.getArrivalDate(), "Data de chegada");
    }

    public void changeVisibleReturnDate(Trip tripLayout, ConstraintLayout constraintLayout) {
        tripLayout.setHasReturnDate(!tripLayout.getHasReturnDate());
        constraintLayout.setVisibility(constraintLayout.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void getLocation(LocationGeo locationGeo) throws IOException {
        this.locationGeo = locationGeo;
        Address address = new Address();
        address.transformCoordenatesToAddress(this.locationGeo.getLatitude(), this.locationGeo.getLongitude(), this.tripView.getContext());
        this.trip.setHomeCity(address.getCity());
        this.trip.setHomeCountry(address.getCountry());
        ((NewTripActivity) this.tripView).bindTrip(trip);
    }

    @Override
    public void sendErrorForecast(String errorMessage) {
        this.tripView.showToast("Não foi buscar os dados da previsão do tempo.");
    }

    @Override
    public void getForecast(List<Forecast> forecastArrayList, String type) {

        // Format forecasts arrayList
        if (forecastArrayList == null || forecastArrayList.size() == 0) {
            this.sendErrorForecast("Nenhuma previsão de tempo foi retornada.");
            return;
        }

        // insert destination forecast at arraylist
        if (type == "destination") {
            for (Forecast forecast : forecastArrayList) {
                if (trip.getReturnDate() == null) {
                    if (forecast.getDate().compareTo(trip.getArrivalDate()) >= 0) {
                        forecastDestination.add(forecast);
                    }
                } else if (forecast.getDate().compareTo(trip.getArrivalDate()) >= 0 &&
                    forecast.getDate().compareTo(trip.getReturnDate()) < 0) {
                    forecastDestination.add(forecast);
                }
            }
        }

        // insert home forecast at arraylist
        if (type == "home") {
            for (Forecast forecast : forecastArrayList) {
                if (trip.getReturnDate() == null) {
                    if (forecast.getDate().compareTo(trip.getArrivalDate()) <= 0) {
                        this.forecasthome.add(forecast);
                    }
                } else if (forecast.getDate().compareTo(trip.getArrivalDate()) <= 0 ||
                        forecast.getDate().compareTo(trip.getReturnDate()) >= 0) {
                    this.forecasthome.add(forecast);
                }
            }
        }

        contRequests++;
        if (contRequests == 2) {
            this.saveData();
        }
    }

    public void saveData() {

        Map<String, Object> tripMap = this.trip.getInstanceinMap();
        tripMap.put("forecastHome", this.forecasthome);
        tripMap.put("forecastDestination", this.forecastDestination);

        db.collection("trips")
                .add(tripMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(tripView.getContext(), TripDetailsActivity.class);
                        intent.putExtra("uId", documentReference.getId());
                        tripView.navigate(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tripView.showToast("Ops! Ocorreu um erro ao tentar salvar esta viagem.");
                    }
                });
    }
}
