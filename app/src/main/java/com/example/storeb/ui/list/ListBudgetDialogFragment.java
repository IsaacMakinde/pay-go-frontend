package com.example.storeb.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

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
        final Button dismissButton = binding.budgetDismissButton;
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return binding.getRoot();
    }

}