package com.example.storeb.ui.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {
    static MutableLiveData<Double> mBudget = new MutableLiveData<>();
    final String TAG = "ListViewModel";

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

    public LiveData<Double> getBudget() {
        return mBudget;
    }


}