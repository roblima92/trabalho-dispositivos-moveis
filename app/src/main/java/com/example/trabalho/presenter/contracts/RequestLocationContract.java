package com.example.trabalho.presenter.contracts;

import com.example.trabalho.models.LocationGeo;

import java.io.IOException;

public class RequestLocationContract {
    public interface RequestLocationView {
    }

    public interface RequestLocationPresenter {
        public void getLocation(LocationGeo locationGeo) throws IOException;
    }
}
