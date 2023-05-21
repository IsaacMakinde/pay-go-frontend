package com.example.storeb.ui.basket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.storeb.databinding.FragmentBasketBinding;
import com.example.storeb.ui.CaptureAct;
import com.google.android.material.snackbar.Snackbar;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class BasketFragment extends Fragment {

    private FragmentBasketBinding binding;
    private final String TAG = "Basket";

    // Barcode Result Handler
    private ActivityResultLauncher<ScanOptions> barcodeScanner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barcodeScanner = registerForActivityResult(new ScanContract(), result -> {
            // Handle result
            if (result.getContents() != null) {
                Log.d(TAG, "onCreateView: " + result.getContents());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Scan Result");
                builder.setMessage(result.getContents());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BasketViewModel basketViewModel =
                new ViewModelProvider(this).get(BasketViewModel.class);

        binding = FragmentBasketBinding.inflate(inflater, container, false);

        View root = binding.getRoot();


        final TextView textView = binding.textBasket;
        final Button scanButton = binding.buttonBasketScan;
        final Button checkoutButton = binding.buttonBasketCheckout;

        basketViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thank you for your purchase", Snackbar.LENGTH_LONG)
                        .setAction("OK", null).show();
            }
        });

        return root;
    }

    // Barcode Scanner
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to turn on flash");
        options.setOrientationLocked(true);
        options.setBeepEnabled(true);
        options.setDesiredBarcodeFormats(ScanOptions.EAN_13);
        options.setCaptureActivity(CaptureAct.class);
        barcodeScanner.launch(options);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}