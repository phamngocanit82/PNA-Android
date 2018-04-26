package com.example.an.pnaandroid.view.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.an.pnaandroid.presenter.base.BasePresenterImplement;

public abstract class BaseAdapter<PresenterType extends BasePresenterImplement,
        ViewHolderType extends BaseViewHolder<PresenterType>>
        extends RecyclerView.Adapter<ViewHolderType> {

    private final PresenterType presenter;
    private final Context context;

    public BaseAdapter(PresenterType presenterType, Context context) {
        this.presenter = presenterType;
        this.context = context;
    }

    protected PresenterType getPresenter() {
        return presenter;
    }

    protected Context getContext() {
        return context;
    }

    protected View inflate(ViewGroup parent, @LayoutRes int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

}

