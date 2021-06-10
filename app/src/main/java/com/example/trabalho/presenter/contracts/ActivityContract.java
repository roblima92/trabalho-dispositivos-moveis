package com.example.trabalho.presenter.contracts;

import android.content.Context;

public class ActivityContract {
    public interface ActivityView {
        public Context getContext();
        public void showToast(String message);
    }

    public interface ActivityPresenter {
        public void start();
    }
}
