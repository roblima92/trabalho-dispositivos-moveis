package com.example.trabalho.presenter.contracts;

import com.example.trabalho.models.LocationGeo;

public class RequestLocationContract {
    public interface RequestLocationView {
    }

    public interface RequestLocationPresenter {
        public void getLocation(LocationGeo locationGeo);
    }
}
