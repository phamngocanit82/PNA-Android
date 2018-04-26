package com.example.an.pnaandroid.view.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.an.pnaandroid.presenter.base.BasePresenterImplement;

public abstract class BaseViewHolder<PresenterType extends BasePresenterImplement> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setDataInViews(@NonNull Context context, PresenterType presenter, Object dataItem);

}