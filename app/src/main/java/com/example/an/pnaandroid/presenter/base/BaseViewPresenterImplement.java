package com.example.an.pnaandroid.presenter.base;

import android.location.LocationManager;
import android.support.annotation.StringRes;

import com.example.an.pnaandroid.library.EnumAlertType;
import com.example.an.pnaandroid.library.EnumMessageType;

public interface BaseViewPresenterImplement {
    void showLoading();

    void hideLoading();

    void onError(String message);

    void onError(@StringRes int resId);

    void onConnectToServerError();

    void onNoInternetConnection();

    void showMessage(String message, EnumMessageType messageType, EnumAlertType alertType);

    void showMessage(int res, EnumMessageType messageType, EnumAlertType alertType);

    boolean isNetworkConnected();

    boolean isLocationEnabled(LocationManager locationManager);

    void hideKeyboard();

    void onTokenExpired();
}
