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
import com.example.storeb.databinding.FragmentListBudgetDialogBinding;

public class ListBudgetDialogFragment extends DialogFragment {

    private FragmentListBudgetDialogBinding binding;
    private final String TAG = "ListBudgetDialogFragment";

    public static ListBudgetDialogFragment newInstance() {
        return new ListBudgetDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ListBudgetDialogViewModel mViewModel = new ViewModelProvider(this).get(ListBudgetDialogViewModel.class);
        binding = FragmentListBudgetDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}