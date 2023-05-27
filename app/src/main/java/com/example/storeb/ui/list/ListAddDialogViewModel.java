package com.example.storeb.ui.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.storeb.models.ProductModel;
import com.example.storeb.services.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAddDialogViewModel extends ViewModel {
    static final String TAG = "ListAddDialogViewModel";
    static MutableLiveData<List<ProductModel>> mAllProductsList = new MutableLiveData<>(new ArrayList<>());
    static MutableLiveData<List<ProductModel>> mFilteredProductsList = new MutableLiveData<>(new ArrayList<>());
    static MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();


    public ListAddDialogViewModel() {
        getProducts();
    }

    public static void setLoading(boolean loading) {
        mIsLoading.setValue(loading);
    }

    public static void getProducts() {
        setLoading(true);

        RetrofitInstance.getRetrofitClient().getProducts().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                    setAllProducts(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        setLoading(false);
    }

    public static void setAllProducts(List<ProductModel> allProducts) {
        mAllProductsList.setValue(allProducts);
    }

    public LiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public void searchProducts(String query) {
        List<ProductModel> allProducts = mAllProductsList.getValue();
        if (allProducts != null) {
            List<ProductModel> filteredProducts = new ArrayList<>();
            for (ProductModel product : allProducts) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }
            mFilteredProductsList.setValue(filteredProducts);
        }
    }


}