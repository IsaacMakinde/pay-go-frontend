package com.example.storez.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storez.databinding.FragmentListAddDialogBinding;
import com.example.storez.models.MutableTuple;
import com.example.storez.models.ProductModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ListAddDialogFragment extends DialogFragment {

    private FragmentListAddDialogBinding binding;
    private final String TAG = "ListAddDialogFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ListAddDialogViewModel mViewModel = new ViewModelProvider(this).get(ListAddDialogViewModel.class);
        ListViewModel mListViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        binding = FragmentListAddDialogBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.listAddDialogRecycleview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ListAddDialogAdapter adapter = new ListAddDialogAdapter(null); // Pass null initially
        recyclerView.setAdapter(adapter);
        ProgressBar progressBar = binding.listProgressBar;
        progressBar.setVisibility(View.VISIBLE);
        SearchView searchView = binding.listAddSearchview;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mViewModel.searchProducts(newText);
                return true;
            }
        });

        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


        mViewModel.mAllProductsList.observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productList) {
                if (mViewModel.mAllProductsList.getValue() != null) {
                    Log.d(TAG, "onChanged: mAllProductsList is null");
                    adapter.setProductList(mViewModel.mAllProductsList.getValue());
                    Log.d(TAG, "onChanged: " + mViewModel.mAllProductsList.getValue());
                    adapter.notifyDataSetChanged();

                }
            }
        });

        mViewModel.mFilteredProductsList.observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productList) {
                adapter.setProductList(productList);
                adapter.notifyDataSetChanged();
            }
        });


        mListViewModel.mShowSnackbar.observe(getViewLifecycleOwner(), new Observer<MutableTuple<Integer, String>>() {
            @Override
            public void onChanged(MutableTuple<Integer, String> tuple) {
                if (tuple.getItem1() == 0) {
                    Snackbar.make(getView(), tuple.getItem2() + " Added To List", Snackbar.LENGTH_SHORT)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            })
                            .show();
                } else if (tuple.getItem1() == 1) {
                    Snackbar.make(getView(), tuple.getItem2() + " Already In List", Snackbar.LENGTH_SHORT)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            })
                            .show();
                }
            }
        });


        final Button dismissButton = binding.dismissButton;
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dismiss();
                Log.d(TAG, "onClick: Dismiss button was pressed");
            }
        });

        return binding.getRoot();
    }


}