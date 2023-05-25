package com.example.storeb.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeb.databinding.FragmentListAddDialogBinding;
import com.example.storeb.models.ProductModel;

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
        binding = FragmentListAddDialogBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.listAddDialogRecycleview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ListAddDialogAdapter adapter = new ListAddDialogAdapter(null); // Pass null initially
        recyclerView.setAdapter(adapter);

        mViewModel.mAllProductsList.observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productList) {
                if (mViewModel.mAllProductsList.getValue() != null) {
                    Log.d(TAG, "onChanged: mAllProductsList is null");

                    adapter.setProductList(mViewModel.mAllProductsList.getValue()); // Set the updated product list
                    Log.d(TAG, "onChanged: " + mViewModel.mAllProductsList.getValue()); // Log the updated value
                    adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                }
            }
        });

        final Button dismissButton = binding.dismissButton;
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onResponse: " + mViewModel.mAllProductsList.getValue().get(0).getName());

                dismiss();
                Log.d(TAG, "onClick: Dismiss button was pressed");
            }
        });

        return binding.getRoot();
    }



}