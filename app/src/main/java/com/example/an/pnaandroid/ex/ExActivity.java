package com.example.an.pnaandroid.ex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.an.pnaandroid.R;
import com.facebook.FacebookActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_qrcode)
    public void actionQRCode(){
        Intent intent = new Intent(this, ExQRCodeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_youtube)
    public void actionYoutube(){
        Intent intent = new Intent(this, ExYoutubeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_facebook)
    public void actionFaceBook(){
        Intent intent = new Intent(this, ExFacebookActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_socket)
    public void actionSocket(){
        Intent intent = new Intent(this, ExSocketActivity.class);
        startActivity(intent);
    }
}
