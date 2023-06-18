package com.example.storez.ui.list;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storez.databinding.FragmentListBinding;
import com.example.storez.models.ListPreferenceHelper;
import com.example.storez.models.ProductModel;
import com.example.storez.ui.CaptureAct;
import com.example.storez.ui.basket.BasketViewModel;
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
        ListViewModel listViewModel =
                new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        BasketViewModel basketViewModel =
                new ViewModelProvider(requireActivity()).get(BasketViewModel.class);
        super.onCreate(savedInstanceState);
        barcodeScanner = registerForActivityResult(new ScanContract(), result -> {
            // Handle result
            if (result.getContents() != null) {
                Log.d(TAG, "onCreateView: " + result.getContents());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Scan Result");
                String eanBarcode = String.format("0%s", result.getContents());
                if (listViewModel.checkList(eanBarcode)) {
                    listViewModel.setToScanned(eanBarcode);
                    listViewModel.addToScannedBasket(listViewModel.getItemByBarcode(eanBarcode));
                    basketViewModel.addToBasket(listViewModel.getItemByBarcode(eanBarcode));
                }

                Log.d(TAG, "onCreate: " + eanBarcode);
                builder.setMessage(eanBarcode);
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
                new ViewModelProvider(requireActivity()).get(ListViewModel.class);


        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listViewModel.listPreferenceHelper = new ListPreferenceHelper(getContext());
        listViewModel.loadListState();

        TextView textView = binding.textList;
        final Button addButton = binding.buttonListAdd;
        final Button scanButton = binding.buttonScan;

        RecyclerView recyclerView = binding.recyclerViewList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ListAdapter listAdapter = new ListAdapter(null);
        recyclerView.setAdapter(listAdapter);

        listViewModel.mListItems.observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productList) {
                if (ListViewModel.mListItems.getValue() != null) {
                    Log.d(TAG, "onChanged: mAllProductsList is null");
                    listAdapter.setProductList(listViewModel.mListItems.getValue());
                    Log.d(TAG, "onChanged: " + listViewModel.mListItems.getValue());
                    listAdapter.notifyDataSetChanged();

                }
            }
        });




        listViewModel.mBudget.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                String budgetDisplay = String.format("%.2f / %.2f", listViewModel.getTotalValue(),  aDouble);
                binding.textList.setText("Budget: " + listViewModel.getSymbol() + budgetDisplay);
            }
        });

        listViewModel.mTotal.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                String budgetDisplay = String.format("%.2f / %.2f",aDouble, listViewModel.getBudgetValue());
                binding.textList.setText("Budget: " + listViewModel.getSymbol() + budgetDisplay);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBudgetDialogFragment listBudgetDialogFragment = new ListBudgetDialogFragment();
                listBudgetDialogFragment.show(getChildFragmentManager(), "ListBudgetDialogFragment");
//                Log.d(TAG, "onClick: Budget button clicked");
            }
        });

        listViewModel.mListItems.observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                Log.d(TAG, "onChanged: " + listViewModel.mListItems.getValue());

            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListAddDialogFragment listAddDialogFragment = new ListAddDialogFragment();
                listAddDialogFragment.show(getChildFragmentManager(), "ListAddDialogFragment");
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