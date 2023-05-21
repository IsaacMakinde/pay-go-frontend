package com.example.storeb.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.storeb.databinding.FragmentListBudgetDialogBinding;

public class ListBudgetDialogFragment extends DialogFragment {

    private FragmentListBudgetDialogBinding binding;
    private final String TAG = "ListBudgetFragment";

    public static ListBudgetDialogFragment newInstance() {
        return new ListBudgetDialogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ListBudgetDialogViewModel mViewModel = new ViewModelProvider(this).get(ListBudgetDialogViewModel.class);
        ListViewModel mListViewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        binding = FragmentListBudgetDialogBinding.inflate(inflater, container, false);
        final Button dismissButton = binding.budgetDismissButton;
        EditText budgetEditText = binding.editTextNumberDecimal;


        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budget = budgetEditText.getText().toString();
                Log.d(TAG, "onClick: Dismiss button was pressed");
                Log.d(TAG, "onClick: Budget is: " + budget);
                mListViewModel.setBudget(Double.valueOf(budget));
                dismiss();
            }
        });
        return binding.getRoot();
    }


}