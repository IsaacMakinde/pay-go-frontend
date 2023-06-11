package com.example.storez.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.storez.databinding.FragmentListBudgetDialogBinding;

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
        ListViewModel listViewModel =
                new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        binding = FragmentListBudgetDialogBinding.inflate(inflater, container, false);
        final Button dismissButton = binding.budgetDismissButton;
        EditText budgetEditText = binding.editTextNumberDecimal;


        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueString = budgetEditText.getText().toString();
                if (!valueString.isEmpty()) {
                    // Validate the input string
                    if (isValidDecimalNumber(valueString)) {
                        double budget = Double.parseDouble(valueString);
                        listViewModel.setBudget(budget);
                    } else {
                        // Display an error message or handle the invalid input
                        Toast.makeText(getContext(), "Invalid input. Please enter a valid decimal number.", Toast.LENGTH_SHORT).show();
                    }
                }

                dismiss();
            }
        });

        return binding.getRoot();
    }

    private boolean isValidDecimalNumber(String input) {
        try {
            double number = Double.parseDouble(input);
            // Additional checks can be added here if needed
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}