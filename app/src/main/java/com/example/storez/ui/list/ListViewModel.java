package com.example.storez.ui.list;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.storez.models.ListPreferenceHelper;
import com.example.storez.models.MutableTuple;
import com.example.storez.models.ProductModel;
import com.example.storez.services.RetrofitInstance;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;

public class ListViewModel extends ViewModel {
    Currency currency = Currency.getInstance(Locale.getDefault());
    String symbol = currency.getSymbol();
    static final String TAG = "ListViewModel";
    static MutableLiveData<Double> mBudget = new MutableLiveData<>(0.0);
    static MutableLiveData<List<ProductModel>> mListItems = new MutableLiveData<>(new ArrayList<>());
    static MutableLiveData<Double> mTotal = new MutableLiveData<>(0.0);
    static MutableLiveData<MutableTuple<Integer, String>> mShowSnackbar = new MutableLiveData<>(new MutableTuple(-1, "null"));
    static ListPreferenceHelper listPreferenceHelper;
    static MutableLiveData<List<ProductModel>> mScannedProductsToBasket = new MutableLiveData<>(new ArrayList<>());
    static MutableLiveData<Boolean> mShowBudgetWarning = new MutableLiveData<>(false);


    public ListViewModel(ListPreferenceHelper preferenceHelper) {
        listPreferenceHelper = preferenceHelper;
        calculateTotal();
    }

    public ListViewModel() {

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
//        Log.d(TAG, "onCleared: called");
    }

    public static void addToListItems(ProductModel product) {
        if (mListItems.getValue() == null) {
            mListItems.setValue(new ArrayList<>());
        }

        List<ProductModel> allProducts = mListItems.getValue();

        if (allProducts.contains(product)) {
            mShowSnackbar.setValue(new MutableTuple<>(1, product.getName()));
        } else {
            product.setQuantity(1);
            allProducts.add(product);
            mShowSnackbar.setValue(new MutableTuple<>(0, product.getName()));
        }

        setListItems(allProducts);
        calculateTotal();

        listPreferenceHelper.saveListState(mListItems.getValue(), mBudget.getValue());
        mShowSnackbar.setValue(new MutableTuple<>(-1, "null"));
//        Log.d(TAG, "addToListItems: " + mListItems.getValue());
    }



    public static void setListItems(List<ProductModel> listItems) {
        mListItems.setValue(listItems);
        calculateTotal();
    }


    public static void increaseQuantity(ProductModel productModel) {
        if (mListItems.getValue().contains(productModel)) {
            List<ProductModel> updatedList = new ArrayList<>(mListItems.getValue());

            int index = updatedList.indexOf(productModel);
            ProductModel updatedProduct = updatedList.get(index);
            updatedProduct.setPrice(productModel.getPrice() / productModel.getQuantity());
            updatedProduct.setQuantity(updatedProduct.getQuantity() + 1);
            updatedProduct.setPrice(productModel.getPrice() * updatedProduct.getQuantity());
            updatedProduct.setHasScanned(false);

            updatedList.set(index, updatedProduct);
            mListItems.setValue(updatedList);
//            Log.d(TAG, "increaseQuantity: " + mListItems.getValue());

        }
        listPreferenceHelper.saveListState(mListItems.getValue(), mBudget.getValue());
        calculateTotal();
    }

    public static void decreaseQuantity(ProductModel productModel) {
        if (mListItems.getValue().contains(productModel)) {
            List<ProductModel> updatedList = new ArrayList<>(mListItems.getValue());
            int index = updatedList.indexOf(productModel);
            if (productModel.getQuantity() <= 1) {
                mListItems.getValue().remove(productModel);
            }
            else {
                ProductModel updatedProduct = updatedList.get(index);
                updatedProduct.setPrice(productModel.getPrice() / productModel.getQuantity());
                mListItems.getValue().get(mListItems.getValue().indexOf(productModel)).setQuantity(productModel.getQuantity() - 1);
                updatedProduct.setPrice(productModel.getPrice() * updatedProduct.getQuantity());
                updatedProduct.setHasScanned(false);

                updatedList.set(index, updatedProduct);
                mListItems.setValue(updatedList);
                Log.d(TAG, "decreasedQuantity: " + mListItems.getValue());

            }
        }
        listPreferenceHelper.saveListState(mListItems.getValue(), mBudget.getValue());
        calculateTotal();
    }

    public static void removeFromList(ProductModel productModel) {
        if (mListItems.getValue().contains(productModel)) {
            mListItems.getValue().remove(productModel);
        }
        calculateTotal();
    }

    public ProductModel getItemByBarcode(String barcode) {
        for (ProductModel product : mListItems.getValue()) {
            if (product.getBarcode().equals(barcode)) {
                return product;
            }
        }
        return null;
    }


    public static void calculateTotal() {
        if (mListItems.getValue() == null) {
            mListItems.setValue(new ArrayList<>());
        }
        Double total = 0.0;
        for (ProductModel product : mListItems.getValue()) {
            total += product.getPrice();
        }
        mTotal.setValue(total);
//        Log.d(TAG, "calculateTotal: " + total);

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
        mScannedProductsToBasket.setValue(listPreferenceHelper.getBasketState());
        setListItems(mListItems.getValue());
//        Log.d(TAG, "loadListState: " + mListItems.getValue());
//        Log.d(TAG, "loadListState: " + mBudget.getValue());

    }

    public boolean checkList(String barcode) {
        if (mListItems.getValue() == null) {
            mListItems.setValue(new ArrayList<>());
            return false;
        }
        else {
            List<ProductModel> updatedList = new ArrayList<>(mListItems.getValue());
            for (ProductModel product : updatedList) {
                if (product.getBarcode().equals(barcode) && !product.isHasScanned()) {
                    Log.d(TAG, "checkList: " + product.getName() + "dfasfae");
                    return true;

                }
            }
        }

        return false;
    }

    public void setToScanned(String barcode) {
        if (mListItems.getValue() == null) {
            mListItems.setValue(new ArrayList<>());
        }
        else {
            List<ProductModel> updatedList = new ArrayList<>(mListItems.getValue());
            for (ProductModel product : updatedList) {
                if (product.getBarcode().equals(barcode) && !product.isHasScanned()) {
                    product.setHasScanned(true);
                    updatedList.set(updatedList.indexOf(product), product);
                    mListItems.setValue(updatedList);
                    break;
                }
            }
            mListItems.setValue(updatedList);
            listPreferenceHelper.saveListState(mListItems.getValue(), mBudget.getValue());
        }


    }

    public void addToScannedBasket(ProductModel productModel) {
        if (mListItems.getValue() == null) {
            mListItems.setValue(new ArrayList<>());

        }
        if (mScannedProductsToBasket.getValue() == null) {
            mScannedProductsToBasket.setValue(new ArrayList<>());
        }

            List<ProductModel> updatedScannedBasket = new ArrayList<>(mScannedProductsToBasket.getValue());
            List<ProductModel> updatedList = new ArrayList<>(mListItems.getValue());
            if (productModel.isHasScanned() && !updatedScannedBasket.contains(productModel)) {
                updatedScannedBasket.add(productModel);
                mScannedProductsToBasket.setValue(updatedScannedBasket);
                listPreferenceHelper.saveBasketState(mScannedProductsToBasket.getValue());
            }

            else  if(productModel.isHasScanned() && updatedScannedBasket.contains(productModel)) {
                int index = updatedScannedBasket.indexOf(productModel);
                updatedScannedBasket.set(index, productModel);
                mScannedProductsToBasket.setValue(updatedScannedBasket);
                listPreferenceHelper.saveBasketState(mScannedProductsToBasket.getValue());
            }

        Log.d(TAG, "addToScannedBasket: " + mScannedProductsToBasket.getValue());
        Log.d(TAG, "addToScannedBasket: " + listPreferenceHelper.getBasketState());

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