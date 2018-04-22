package com.example.an.pnaandroid.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UtilsIO {
    /**
     * encode a bitmap to base64 string
     * @param image
     * @return base64 string
     */
    public static String encodeBitmapToBase64(Bitmap image) {
        if (image == null) return  null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded.replace("\n", "");
    }

    /**
     * decode base64 string to bitmap
     * @param input
     * @return bitmap
     */
    public static Bitmap decodeBase64ToBitmap(String input) {
        if (input == null) return null;
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0,decodedByte.length);
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    //encode bitmap to byte array
    public static byte[] encodeBitmapToByte(Bitmap bitmap){
        if (bitmap == null) return  null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return  byteArrayOutputStream.toByteArray();
    }
    //decode byte array to bitmap
    public static Bitmap decodeByteToBitmap(byte[] byteArray){
        return  BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    public byte[] convertFileToByte(String path){
        //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/filename.3gpp";
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    public  void writeFileFromByte(byte[]byteArray, Context context) throws IOException {
        File file = File.createTempFile("filename", "mp3", context.getCacheDir());
        file.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(byteArray);
        fos.close();
    }
}
