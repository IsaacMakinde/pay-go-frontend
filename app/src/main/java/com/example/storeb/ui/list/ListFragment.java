package com.example.storeb.ui.list;

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

import com.example.storeb.databinding.FragmentListBinding;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private final String TAG = "ListFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListViewModel listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textList;
        listViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBudgetDialogFragment listBudgetDialogFragment = new ListBudgetDialogFragment();
                listBudgetDialogFragment.show(getChildFragmentManager(), "ListBudgetDialogFragment");
                Log.d(TAG, "onClick: Budget button clicked");
            }
        });

        final Button addButton = binding.buttonListAdd;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListAddDialogFragment listAddDialogFragment = new ListAddDialogFragment();
                listAddDialogFragment.show(getChildFragmentManager(), "ListAddDialogFragment");
                Log.d(TAG, "onClick: Add button clicked");
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