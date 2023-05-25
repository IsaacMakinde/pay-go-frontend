package com.example.storeb.services;

import com.example.storeb.models.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonApi {
    @GET("products")
    Call<List<ProductModel>> getProducts();
}
