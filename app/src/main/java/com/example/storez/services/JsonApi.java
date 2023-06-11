package com.example.storez.services;

import com.example.storez.models.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonApi {
    @GET("products")
    Call<List<ProductModel>> getProducts();

    @GET("products/<String :barcode>")
    Call<List<ProductModel>> getProductsByBarcode();
}
