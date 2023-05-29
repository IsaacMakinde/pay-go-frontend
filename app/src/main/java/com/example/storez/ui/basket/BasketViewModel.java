package com.example.storez.ui.basket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class BasketViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BasketViewModel() {
        mText = new MutableLiveData<>();
        MutableLiveData<ArrayList<String>> basketItems = new MutableLiveData<>();
        mText.setValue("$20");
        basketItems.setValue(new ArrayList<>());
    }

    public LiveData<String> getText() {
        return mText;
    }
}