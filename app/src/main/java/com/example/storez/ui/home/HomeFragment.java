package com.example.storez.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.storez.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final String TAG = "HomeFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        final Button joinButton = binding.buttonGroupJoin;
        final Button createButton = binding.buttonCreateGroup;
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Creating Basket...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinBasketDialogFragment joinBasketDialogFragment = new JoinBasketDialogFragment();
                joinBasketDialogFragment.show(getChildFragmentManager(), "JoinBasketDialogFragment");
                Log.d(TAG, "onClick: Join button clicked");

            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}