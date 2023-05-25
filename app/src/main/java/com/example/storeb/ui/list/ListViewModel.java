package com.example.storeb.ui.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.storeb.models.ProductModel;

import java.util.List;

public class ListViewModel extends ViewModel {
    static MutableLiveData<Double> mBudget = new MutableLiveData<>();
    static final String TAG = "ListViewModel";
    static MutableLiveData<List<ProductModel>> mListItems = new MutableLiveData<>();

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

    public static void setListItems(List<ProductModel> listItems) {
        mListItems.setValue(listItems);
    }

    public static void addToListItems(ProductModel productModel) {
        if (mListItems.getValue().contains(productModel)) {
            mListItems.getValue().get(mListItems.getValue().indexOf(productModel)).setQuantity(productModel.getQuantity() + 1);
        } else {
            mListItems.getValue().add(productModel);
        }
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