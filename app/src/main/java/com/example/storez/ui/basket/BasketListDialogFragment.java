package com.example.storez.ui.basket;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.storez.R;
import com.example.storez.databinding.FragmentBasketListDialogBinding;

public class BasketListDialogFragment extends DialogFragment {

    private FragmentBasketListDialogBinding binding;
    private final String TAG = "BasketListDialogFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        BasketListDialogViewModel mViewModel = new ViewModelProvider(this).get(BasketListDialogViewModel.class);
        binding = FragmentBasketListDialogBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}