package com.example.an.pnaandroid.presenter.base;

import android.support.annotation.NonNull;

import com.androidnetworking.error.ANError;
import com.example.an.pnaandroid.model.APIResponse;

public interface BasePresenterImplement<V extends BaseViewPresenterImplement> {
    void onAttach(V baseView);

    void onDetach();

    void onViewInitialized();

    void handleApiError(@NonNull ANError error);

    void handleThrowable(Throwable throwable);

    void handleApiResponseError(@NonNull APIResponse apiResponse);

    void setUserAsLoggedOut();
}
