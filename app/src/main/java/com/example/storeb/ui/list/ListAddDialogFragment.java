package com.example.storeb.ui.list;

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
import com.example.storeb.databinding.FragmentListAddDialogBinding;

public class ListAddDialogFragment extends DialogFragment {

    private FragmentListAddDialogBinding binding;
    private final String TAG = "ListAddDialogFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ListAddDialogViewModel mViewModel = new ViewModelProvider(this).get(ListAddDialogViewModel.class);
        binding = FragmentListAddDialogBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }



}