package com.example.trabalho.presenter.contracts;

import com.example.trabalho.models.Forecast;

import java.util.Date;
import java.util.List;

public class RequestFirestoreContract {

    public interface RequestFirestoreView {
        public void bindListFirestore(List<ModelContract.Model> modelArrayList);
    }

    public interface RequestFirestorePresenter {
    }
}
