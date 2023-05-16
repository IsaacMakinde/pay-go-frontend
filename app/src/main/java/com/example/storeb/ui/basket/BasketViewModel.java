package com.example.storeb.ui.basket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BasketViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BasketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("$20");
    }

    public LiveData<String> getText() {
        return mText;
    }
}