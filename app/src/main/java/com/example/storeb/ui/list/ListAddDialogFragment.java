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
import androidx.lifecycle.ViewModelProvider;

import com.example.storeb.databinding.FragmentListAddDialogBinding;

public class ListAddDialogFragment extends DialogFragment {

    private FragmentListAddDialogBinding binding;
    private final String TAG = "ListAddDialogFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ListAddDialogViewModel mViewModel = new ViewModelProvider(this).get(ListAddDialogViewModel.class);
        binding = FragmentListAddDialogBinding.inflate(inflater, container, false);
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