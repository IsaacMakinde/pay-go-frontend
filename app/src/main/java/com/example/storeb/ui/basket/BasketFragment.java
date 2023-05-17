package com.example.storeb.ui.basket;

import android.app.Dialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.storeb.databinding.FragmentBasketBinding;
import com.google.android.material.snackbar.Snackbar;

public class BasketFragment extends Fragment {

    private FragmentBasketBinding binding;
    private final String TAG = "Basket";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BasketViewModel basketViewModel =
                new ViewModelProvider(this).get(BasketViewModel.class);

        binding = FragmentBasketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textBasket;
        final Button scanButton = binding.buttonBasketScan;
        final Button checkoutButton = binding.buttonBasketCheckout;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BasketListDialogFragment basketListDialogFragment = new BasketListDialogFragment();
                basketListDialogFragment.show(getChildFragmentManager(), "BasketListDialogFragment");
                Log.d(TAG, "onClick: Add Button was pressed");
            }
        });

        basketViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thank you for your purchase", Snackbar.LENGTH_LONG)
                        .setAction("OK", null).show();
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}