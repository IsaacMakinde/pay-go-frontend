package com.example.storeb.ui.list;

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

import com.example.storeb.databinding.FragmentListBinding;
import com.example.storeb.models.ProductModel;
import com.example.storeb.ui.CaptureAct;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private final String TAG = "ListFragment";

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
        ListViewModel listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textList;
        final Button addButton = binding.buttonListAdd;
        final Button scanButton = binding.buttonScan;

        listViewModel.mBudget.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                String budgetDisplay = String.format("20/%.2f", aDouble);
                binding.textList.setText("Budget: $" + budgetDisplay);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBudgetDialogFragment listBudgetDialogFragment = new ListBudgetDialogFragment();
                listBudgetDialogFragment.show(getChildFragmentManager(), "ListBudgetDialogFragment");
                Log.d(TAG, "onClick: Budget button clicked");
            }
        });

        ListViewModel.mListItems.observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                Log.d(TAG, "onChanged: " + ListViewModel.mListItems.getValue());

            }

        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListAddDialogFragment listAddDialogFragment = new ListAddDialogFragment();
                listAddDialogFragment.show(getChildFragmentManager(), "ListAddDialogFragment");
                Log.d(TAG, "onClick: Add button clicked");
            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
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