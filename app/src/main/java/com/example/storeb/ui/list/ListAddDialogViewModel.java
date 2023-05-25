package com.example.storeb.ui.list;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.storeb.models.ProductModel;
import com.example.storeb.services.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAddDialogViewModel extends ViewModel {
    static final String TAG = "ListAddDialogViewModel";
    // TODO: Implement the ViewModel
    static MutableLiveData<List<ProductModel>> mAllProductsList = new MutableLiveData<>();

    public ListAddDialogViewModel() {
        getProducts();


    }

    public static void setAllProducts(List<ProductModel> allProducts) {
        mAllProductsList.setValue(allProducts);
    }

    public static void addToListItems(ProductModel productModel) {
        if (mAllProductsList.getValue().contains(productModel)) {
            mAllProductsList.getValue().get(mAllProductsList.getValue().indexOf(productModel)).setQuantity(productModel.getQuantity() + 1);
        } else {
            mAllProductsList.getValue().add(productModel);
        }
    }

    public static void getProducts() {
        RetrofitInstance.getRetrofitClient().getProducts().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                    setAllProducts(response.body());
//                  Log.d(TAG, "ListAddDialogViewModel: called" + mAllProductsList.getValue().get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}