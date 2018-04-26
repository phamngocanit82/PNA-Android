package com.example.an.pnaandroid.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class APIResponse<T> {
    @SerializedName("status")
    private int status = 0;

    @SerializedName("data")
    private JsonElement data = null;

    @SerializedName("message")
    private String message = "";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData(Class<T> myClass) {
        if (data != null) {
            return (new Gson()).fromJson(data, myClass);
        }
        return null;
    }

    public List<T> getDataList(Class<T> myClass) {
        JsonArray arrJson = data.getAsJsonArray();
        List<T> listData = new ArrayList<>();
        for (JsonElement jsonE : arrJson) {
            T objectTemp = (new Gson()).fromJson(jsonE, myClass);
            listData.add(objectTemp);
        }
        return listData;
    }

    public ArrayList<T> getDataArrayList(Class<T> myClass) {
        JsonArray arrJson = data.getAsJsonArray();
        ArrayList<T> listData = new ArrayList<>();
        for (JsonElement jsonE : arrJson) {
            T objectTemp = (new Gson()).fromJson(jsonE, myClass);
            listData.add(objectTemp);
        }
        return listData;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }
}
