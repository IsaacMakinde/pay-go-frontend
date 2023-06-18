package com.example.storez.ui.basket;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.storez.models.ListPreferenceHelper;
import com.example.storez.models.ProductModel;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class BasketViewModel extends ViewModel {


    Currency currency = Currency.getInstance(Locale.getDefault());
    String symbol = currency.getSymbol();
    static final String TAG = "BasketViewModel";
    static MutableLiveData<List<ProductModel>> mBasketItems = new MutableLiveData<>(new ArrayList<>());
    static MutableLiveData<Double> mTotal = new MutableLiveData<>(0.0);
    static ListPreferenceHelper listPreferenceHelper;

    public BasketViewModel(ListPreferenceHelper preferenceHelper) {
        listPreferenceHelper = preferenceHelper;
        calculateTotal();
    }

    public BasketViewModel() {

    }

    public static void addToBasket(ProductModel product) {
        if (mBasketItems.getValue() == null) {
            mBasketItems.setValue(new ArrayList<>());
        }

        if (mBasketItems.getValue().contains(product)) {
            List<ProductModel> updatedList = new ArrayList<>(mBasketItems.getValue());
            int index = updatedList.indexOf(product);
            updatedList.set(index, product);
            mBasketItems.setValue(updatedList);
            Log.d(TAG, "addToBasket:  Thsi happened");
        }

        else  {
            mBasketItems.getValue().add(product);
        }
//        listPreferenceHelper.saveBasketState(mBasketItems.getValue(), mTotal.getValue());
        calculateTotal();
        Log.d(TAG, "addToListItems:  wahbadadawd");
    }


    public static void calculateTotal() {
        if (mBasketItems.getValue() == null) {
            mBasketItems.setValue(new ArrayList<>());
        }

        Double total = 0.0;
        for (ProductModel product : mBasketItems.getValue()) {
            total += product.getPrice();
        }

        mTotal.setValue(total);
    }

    public void loadListState() {
        mBasketItems.setValue(listPreferenceHelper.getBasketState());
//        mTotal.setValue(listPreferenceHelper.getTotalState());
//        setBasketItems(mBasketItems.getValue());
//        Log.d(TAG, "loadListState: "+ mBasketItems.getValue().size());
//        Log.d(TAG, "loadListState: " + mTotal.getValue());
        Log.d(TAG, "loadListState: " + listPreferenceHelper.getBasketState());
        calculateTotal();

    }

    public static void setBasketItems(List<ProductModel> list) {
        mBasketItems.setValue(list);
        calculateTotal();
    }

    public LiveData<Double> getTotal() {
        return mTotal;
    }

    public Double getTotalValue() {
        return mTotal.getValue();
    }

    public String getSymbol() {
        return symbol;
    }




}
