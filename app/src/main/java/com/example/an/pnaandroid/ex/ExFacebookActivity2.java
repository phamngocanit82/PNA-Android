package com.example.an.pnaandroid.ex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.an.pnaandroid.R;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ExFacebookActivity2 extends AppCompatActivity {
    EditText editTextTitle, editTextDesc, editTextPath;
    ImageView imgShare;
    Button btnShareLink, btnShareImage, btnPickVideo, btnShareVideo;
    VideoView viewVideo;

    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;
    Bitmap bitmap;

    public static int select_image = 1;
    public static int select_video = 2;
    Uri selectVideo;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_facebook2);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextPath = findViewById(R.id.editTextPath);

        imgShare = findViewById(R.id.imgShare);

        btnShareLink = findViewById(R.id.btnShareLink);
        btnShareImage = findViewById(R.id.btnShareImage);
        btnPickVideo = findViewById(R.id.btnPickVideo);
        btnShareVideo = findViewById(R.id.btnShareVideo);

        viewVideo = findViewById(R.id.viewVideo);

        shareDialog = new ShareDialog(ExFacebookActivity2.this);

        btnShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shareDialog.canShow(ShareLinkContent.class)){
                    shareLinkContent = new ShareLinkContent.Builder()
                            .setContentTitle(editTextTitle.getText().toString())
                            .setContentDescription(editTextDesc.getText().toString())
                            .setContentUrl(Uri.parse(editTextPath.getText().toString()))
                            .build();
                    shareDialog.show(shareLinkContent);
                }
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, select_image);
            }
        });

        btnShareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shareDialog.canShow(SharePhotoContent.class)){
                    BitmapDrawable drawable = (BitmapDrawable) imgShare.getDrawable();
                    Bitmap bitmap2 = drawable.getBitmap();

                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(bitmap2)
                            .build();
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();

                    shareDialog.show(content);
                }
            }
        });

        btnPickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent, select_video);
            }
        });

        btnShareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shareDialog.canShow(ShareVideoContent.class)){
                    ShareVideo shareVideo = new ShareVideo.Builder()
                            .setLocalUrl(selectVideo)
                            .build();
                    ShareVideoContent content = new ShareVideoContent.Builder()
                            .setVideo(shareVideo)
                            .build();
                    shareDialog.show(content);
                    viewVideo.stopPlayback();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == select_image && resultCode == RESULT_OK){
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgShare.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == select_video && resultCode == RESULT_OK){
            selectVideo = data.getData();
            viewVideo.setVideoURI(selectVideo);
            viewVideo.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
