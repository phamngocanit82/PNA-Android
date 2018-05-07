package com.example.an.pnaandroid.ex;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.an.pnaandroid.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ExFacebookActivity extends AppCompatActivity {
    ProfilePictureView profilePictureView;
    LoginButton loginButton;
    Button btnLogout, btnFunctions;
    TextView txtName;
    CallbackManager callbackManager;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_facebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo("com.example.ngocanpham.pnaandroid", PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        for (Signature signature: packageInfo.signatures){
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("SHA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            messageDigest.update(signature.toByteArray());
            Log.w("keyhash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
        }

        callbackManager = CallbackManager.Factory.create();

        profilePictureView = findViewById(R.id.profilePictureView);
        loginButton = findViewById(R.id.loginButton);
        btnLogout = findViewById(R.id.btnLogout);
        btnFunctions = findViewById(R.id.btnFunctions);
        txtName = findViewById(R.id.txtName);

        loginButton.setVisibility(View.VISIBLE);
        btnLogout.setVisibility(View.INVISIBLE);
        btnFunctions.setVisibility(View.INVISIBLE);
        txtName.setVisibility(View.INVISIBLE);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                loginButton.setVisibility(View.VISIBLE);
                btnLogout.setVisibility(View.INVISIBLE);
                btnFunctions.setVisibility(View.INVISIBLE);
                txtName.setVisibility(View.INVISIBLE);
                profilePictureView.setProfileId(null);
            }
        });

        btnFunctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExFacebookActivity.this, ExFacebookActivity2.class);
                startActivity(intent);
            }
        });


        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                btnLogout.setVisibility(View.VISIBLE);
                btnFunctions.setVisibility(View.VISIBLE);
                txtName.setVisibility(View.VISIBLE);

                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            txtName.setText(object.getString("name"));
                            profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "name,email,first_name,last_name");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}