package com.example.storez.ui.list;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.storez.models.ListPreferenceHelper;
import com.example.storez.models.MutableTuple;
import com.example.storez.models.ProductModel;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ListViewModel extends ViewModel {
    Currency currency = Currency.getInstance(Locale.getDefault());
    String symbol = currency.getSymbol();
    static final String TAG = "ListViewModel";
    static MutableLiveData<Double> mBudget = new MutableLiveData<>();
    static MutableLiveData<List<ProductModel>> mListItems = new MutableLiveData<>(new ArrayList<>());
    static MutableLiveData<Double> mTotal = new MutableLiveData<>(0.0);
    static MutableLiveData<MutableTuple<Integer, String>> mShowSnackbar = new MutableLiveData<>(new MutableTuple(-1, "null"));
    static ListPreferenceHelper listPreferenceHelper;


    public ListViewModel() {
        Double placeholder = 0.0;
        mBudget.setValue(placeholder);
        calculateTotal();
    }


    public static Double setBudget(Double budget) {
        mBudget.setValue(budget);
        listPreferenceHelper.saveListState(mListItems.getValue(), budget);
        listPreferenceHelper.saveBudgetState(budget);
        return budget;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: called");
    }

    public static void addToListItems(ProductModel product) {
        if (mListItems.getValue() == null) {
            mListItems.setValue(new ArrayList<>());
        }

        List<ProductModel> allProducts = mListItems.getValue();

        if (allProducts.contains(product)) {
            mShowSnackbar.setValue(new MutableTuple<>(1, product.getName()));
        } else {
            allProducts.add(product);
            mShowSnackbar.setValue(new MutableTuple<>(0, product.getName()));
        }

        setListItems(allProducts);
        calculateTotal();

        listPreferenceHelper.saveListState(mListItems.getValue(), mBudget.getValue());
        mShowSnackbar.setValue(new MutableTuple<>(-1, "null"));
    }

    public static void setListItems(List<ProductModel> listItems) {
        mListItems.setValue(listItems);
        calculateTotal();
    }

    public static void removeFromListItems(ProductModel productModel) {
        if (mListItems.getValue().contains(productModel)) {
            mListItems.getValue().get(mListItems.getValue().indexOf(productModel)).setQuantity(productModel.getQuantity() - 1);
        }
    }


    public static void calculateTotal() {
        Double total = 0.0;
        for (ProductModel product : mListItems.getValue()) {
            total += product.getPrice() * product.getQuantity();
        }
        mTotal.setValue(total);
        Log.d(TAG, "calculateTotal: " + total);
    }

    public LiveData<Double> getBudget() {
        return mBudget;
    }

    public Double getBudgetValue() {
        return mBudget.getValue();
    }

    public void loadListState() {

        mListItems.setValue(listPreferenceHelper.getListState());
        mBudget.setValue(listPreferenceHelper.getBudgetState());
        setListItems(mListItems.getValue());
        Log.d(TAG, "loadListState: " + mListItems.getValue());
        Log.d(TAG, "loadListState: " + mBudget.getValue());

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