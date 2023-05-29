package com.example.storez.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.storez.databinding.FragmentJoinBasketDialogBinding;

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
        final Button dismissButton = binding.joinDismissButton;
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return binding.getRoot();
    }


}