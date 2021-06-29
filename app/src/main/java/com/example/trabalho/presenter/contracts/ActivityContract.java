package com.example.trabalho.presenter.contracts;

import android.content.Context;
import android.content.Intent;

import com.example.trabalho.models.Trip;

public class ActivityContract {
    public interface ActivityView {
        public Context getContext();
        public void showToast(String message);
        public void navigate(Intent intent);
    }

    public interface ActivityPresenter {
        public void start();
    }

    public interface ActivityFormPresenter {
        public void submit(ModelContract.Model model);
        public void validate() throws Exception;
    }
}
