package com.example.storeb.ui.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.storeb.models.MutableTuple;
import com.example.storeb.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {
    static MutableLiveData<Double> mBudget = new MutableLiveData<>();
    static final String TAG = "ListViewModel";
    static MutableLiveData<List<ProductModel>> mListItems = new MutableLiveData<>(new ArrayList<>());
    static MutableLiveData<MutableTuple<Integer, String>> mShowSnackbar = new MutableLiveData<>(new MutableTuple(-1, "null"));

    public ListViewModel() {
        Double budget = 0.0;
        mBudget.setValue(budget);
    }


    public static Double setBudget(Double budget) {
        mBudget.setValue(budget);
        return budget;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: called");
    }

    public static void addToListItems(ProductModel product) {
        List<ProductModel> allProducts = mListItems.getValue();

        if (allProducts.contains(product)) {
            mShowSnackbar.setValue(new MutableTuple<>(1, product.getName()));
        } else {

            allProducts.add(product);
            mShowSnackbar.setValue(new MutableTuple<>(0, product.getName()));
        }

        mShowSnackbar.setValue(new MutableTuple<>(-1, "null"));
    }

    public void setListItems(List<ProductModel> listItems) {
        mListItems.setValue(listItems);
    }

    public static void removeFromListItems(ProductModel productModel) {
        if (mListItems.getValue().contains(productModel)) {
            mListItems.getValue().get(mListItems.getValue().indexOf(productModel)).setQuantity(productModel.getQuantity() - 1);
        }
    }

    public LiveData<Double> getBudget() {
        return mBudget;
    }


}