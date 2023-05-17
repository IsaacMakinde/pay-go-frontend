package com.example.storeb.ui.home;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.storeb.R;
import com.example.storeb.databinding.FragmentJoinBasketDialogBinding;

public class JoinBasketDialogFragment extends DialogFragment {

    private FragmentJoinBasketDialogBinding binding;
    private final String TAG = "JoinBasketDialogFragment";

    public static JoinBasketDialogFragment newInstance() {
        return new JoinBasketDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        JoinBasketDialogViewModel mViewModel = new ViewModelProvider(this).get(JoinBasketDialogViewModel.class);
        binding = FragmentJoinBasketDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


}