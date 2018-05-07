package com.example.an.pnaandroid.presenter.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.androidnetworking.utils.Utils;
import com.example.an.pnaandroid.R;
import com.example.an.pnaandroid.library.EnumAlertType;
import com.example.an.pnaandroid.library.EnumMessageType;
import com.example.an.pnaandroid.library.UtilsActivity;
import com.example.an.pnaandroid.library.UtilsString;
import com.example.an.pnaandroid.library.UtilsSystem;
import com.example.an.pnaandroid.library.UtilsUI;
import com.example.an.pnaandroid.view.dialog.NotifyConnectionDialog;
import com.example.an.pnaandroid.view.dialog.NotifyDialog;
import com.google.zxing.common.StringUtils;

public abstract class BaseActivity extends AppCompatActivity
        implements BaseViewPresenterImplement, BaseFragment.Callback, NotifyConnectionDialog.NotifyConnectionDialogListener {
    private ProgressDialog mProgressDialog;
    private boolean isLife = false;
    // this boolean is use for trigger function auto hide keyboard when tap out of focus edit text
    private boolean mTurnOnTouchHideKeyBoard = false;
    private NotifyConnectionDialog notifyConnectionDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLife = true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(BaseActivity.this);
            mProgressDialog.setTitle("Loading...");
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public boolean isActivityStillLive() {
        return isLife;
    }

    @Override
    public void finish() {
        isLife = false;
        super.finish();
    }

    @Override
    public void hideLoading() {
        if (isActivityStillLive()) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showMessage(message, EnumMessageType.ERROR, EnumAlertType.TOAST);
        }
    }

    @Override
    public void onError(int resId) {
        onError(UtilsString.getStringBaseOnLanguage(getApplicationContext(), resId));
    }

    @Override
    public void showMessage(int res, EnumMessageType messageType, EnumAlertType alertType) {
        showMessage(UtilsString.getStringBaseOnLanguage(getApplicationContext(), res),
                messageType, alertType);
    }

    @Override
    public void showMessage(String message, EnumMessageType messageType, EnumAlertType alertType) {
        if (message != null) {
            switch (alertType) {
                case INFO_DIALOG:
                    NotifyDialog notifyDialog;
                    switch (messageType) {
                        case ERROR:
                            notifyDialog = new NotifyDialog(BaseActivity.this,
                                    UtilsString.getStringBaseOnLanguage(getApplicationContext(), R.string.some_error), message);
                            break;
                        default:
                            notifyDialog = new NotifyDialog(BaseActivity.this,
                                    UtilsString.getStringBaseOnLanguage(getApplicationContext(), R.string.congrats), message);
                            break;
                    }
                    notifyDialog.show();
                    break;
                case TOAST:
                    UtilsUI.showToast(this, message);
                    break;
                case SNACKBAR:
                    break;
            }
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return UtilsSystem.isConnectingToInternet(BaseActivity.this);
    }

    @Override
    public boolean isLocationEnabled(LocationManager locationManager) {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(BaseActivity.this);
            alertDialog.setTitle(UtilsString.getStringBaseOnLanguage(BaseActivity.this, R.string.enable_location));
            alertDialog.setMessage(UtilsString.getStringBaseOnLanguage(BaseActivity.this, R.string.request_enable_location));
            alertDialog.setPositiveButton(UtilsString.getStringBaseOnLanguage(BaseActivity.this, R.string.location_settings), (dialogInterface, i) -> {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            });
            alertDialog.setNegativeButton(UtilsString.getStringBaseOnLanguage(BaseActivity.this, R.string.cancel), (dialogInterface, i) -> {
                dialogInterface.cancel();
            });
            alertDialog.create().show();
            return false;
        }
        return true;

    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        }
    }

    public void turnOnTouchHideKeyBoard() {
        mTurnOnTouchHideKeyBoard = true;
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (!mTurnOnTouchHideKeyBoard) {
            return super.dispatchTouchEvent(ev);
        }
        final View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            if (!(currentFocus instanceof EditText) || !isTouchInsideView(ev, currentFocus)) {
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null)
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            }
            return super.dispatchTouchEvent(ev);
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    /**
     * determine if the given motionevent is inside the given view.
     *
     * @param ev           the given view
     * @param currentFocus the motion event.
     * @return if the given motionevent is inside the given view
     */
    private boolean isTouchInsideView(final MotionEvent ev, final View currentFocus) {
        final int[] loc = new int[2];
        currentFocus.getLocationOnScreen(loc);
        return ev.getRawX() > loc[0] && ev.getRawY() > loc[1] && ev.getRawX() < (loc[0] + currentFocus.getWidth())
                && ev.getRawY() < (loc[1] + currentFocus.getHeight());
    }

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onTokenExpired() {
        //UtilsActivity.startActivity(BaseActivity.this, WelcomeActivity.class, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onConnectToServerError() {
        if (notifyConnectionDialog == null) {
            notifyConnectionDialog = new NotifyConnectionDialog(BaseActivity.this,
                    UtilsString.getStringBaseOnLanguage(getApplicationContext(), R.string.api_default_error), "");
            notifyConnectionDialog.setDialogListener(this);
        } else {
            notifyConnectionDialog.setTitle(UtilsString.getStringBaseOnLanguage(getApplicationContext(), R.string.api_default_error));
        }
        if (!notifyConnectionDialog.isShowing()) {
            notifyConnectionDialog.show();
        }
    }

    @Override
    public void onDismissNotifyConnectionDialog() {
        //do nothing
    }

    @Override
    public void onNoInternetConnection() {
        if (notifyConnectionDialog == null) {
            notifyConnectionDialog = new NotifyConnectionDialog(BaseActivity.this,
                    UtilsString.getStringBaseOnLanguage(getApplicationContext(), R.string.no_internet_connection), "");
            notifyConnectionDialog.setDialogListener(this);
        } else {
            notifyConnectionDialog.setTitle(UtilsString.getStringBaseOnLanguage(getApplicationContext(), R.string.no_internet_connection));
        }
        if (!notifyConnectionDialog.isShowing()) {
            notifyConnectionDialog.show();
        }
    }
}
