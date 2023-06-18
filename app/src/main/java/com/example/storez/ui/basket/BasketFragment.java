package com.example.storez.ui.basket;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.storez.databinding.FragmentBasketBinding;
import com.example.storez.models.ListPreferenceHelper;
import com.example.storez.models.ProductModel;
import com.example.storez.ui.CaptureAct;
import com.example.storez.ui.list.ListViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

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
        basketViewModel.listPreferenceHelper = new ListPreferenceHelper(getContext());
        basketViewModel.loadListState();


        final TextView textView = binding.textBasket;
        final Button scanButton = binding.buttonBasketScan;
        final Button checkoutButton = binding.buttonBasketCheckout;


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

//        basketViewModel.mBasketItems.observe(getViewLifecycleOwner(),new Observer<List<ProductModel>>() {
//            @Override
//            public void onChanged(List<ProductModel> productModels) {
//                if (basketViewModel.mBasketItems.getValue() != null) {
//                    basketViewModel.mBasketItems.setValue(basketViewModel.mBasketItems.getValue());
//
//                }
//            }
//        });

        basketViewModel.mTotal.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                String totalDisplay = String.format("%.2f", aDouble);
                binding.textBasket.setText("Total: " + basketViewModel.getSymbol() + totalDisplay);
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