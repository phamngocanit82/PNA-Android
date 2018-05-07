package com.example.an.pnaandroid.presenter.base;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.an.pnaandroid.library.EnumAlertType;
import com.example.an.pnaandroid.library.EnumMessageType;
import com.example.an.pnaandroid.library.UtilsString;
import com.google.zxing.common.StringUtils;

public abstract class BaseFragment extends Fragment implements BaseViewPresenterImplement {
    protected BaseActivity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity){
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }
    @Override
    public void showLoading() {
        if (mActivity != null){
            mActivity.showLoading();
        }
    }
    @Override
    public void hideLoading() {
        if (mActivity != null){
            mActivity.hideLoading();
        }
    }
    @Override
    public void onError(String message) {
        if (mActivity != null){
            mActivity.onError(message);
        }
    }
    @Override
    public void showMessage(String message, EnumMessageType messageType, EnumAlertType alertType) {
        if (mActivity != null){
            mActivity.showMessage(message, messageType, alertType);
        }
    }
    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null){
            return mActivity.isNetworkConnected();
        }
        return false;
    }
    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }
    @Override
    public void hideKeyboard() {
        if (mActivity != null){
            mActivity.hideKeyboard();
        }
    }
    @Override
    public boolean isLocationEnabled(LocationManager locationManager) {
        return mActivity.isLocationEnabled(locationManager);
    }
    @Override
    public void onTokenExpired() {
        mActivity.onTokenExpired();
    }
    @Override
    public void onError(int resId) {
        mActivity.onError(UtilsString.getStringBaseOnLanguage(mActivity, resId));
    }
    @Override
    public void showMessage(int res, EnumMessageType messageType, EnumAlertType alertType) {
        mActivity.showMessage(UtilsString.getStringBaseOnLanguage(mActivity, res),
                messageType, alertType);
    }
    @Override
    public void onConnectToServerError() {

    }
    @Override
    public void onNoInternetConnection() {

    }
    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    protected abstract void initView(View rootView);

    protected abstract void initData();

    public interface Callback {
        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
