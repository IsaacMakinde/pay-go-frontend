package com.example.storez.models;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListPreferenceHelper {
    private static final String TAG = "ListPreferenceHelper";
    private static final String PREF_NAME = "StorePreferences";
    private static final String KEY_LIST_STATE = "listState";
    private static final String KEY_BUDGET_STATE = "budgetState";


    private SharedPreferences preferences;
    private Gson gson;

    public ListPreferenceHelper(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveListState(List<ProductModel> list, Double budget) {
        String jsonList = gson.toJson(list);
        preferences.edit().putString(KEY_LIST_STATE, jsonList).apply();
        preferences.edit().putString(KEY_BUDGET_STATE, budget.toString()).apply();
    }

    public List<ProductModel> getListState() {
        String json = preferences.getString(KEY_LIST_STATE, "");
        Type type = new TypeToken<ArrayList<ProductModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveBudgetState(Double budget) {
        preferences.edit().putString(KEY_BUDGET_STATE, budget.toString()).apply();
    }

    public Double getBudgetState() {
        String json = preferences.getString(KEY_BUDGET_STATE, "0.00");
        return gson.fromJson(json, Double.class);
    }
}
