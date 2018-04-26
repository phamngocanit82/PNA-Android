package com.example.an.pnaandroid.presenter.base;

import android.support.annotation.NonNull;

import com.example.an.pnaandroid.model.APIResponse;
import com.example.an.pnaandroid.view.base.BaseView;

public interface BasePresenterImplement<V extends BaseView> {
    void onAttach(V baseView);

    void onDetach();

    void onViewInitialized();

    void handleApiError(@NonNull ANError error);

    void handleThrowable(Throwable throwable);

    void handleApiResponseError(@NonNull APIResponse apiResponse);

    void setUserAsLoggedOut();
}
