package com.example.storeb.ui.recent;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.storeb.R;
import com.example.storeb.databinding.FragmentRecentActivityBinding;

public class RecentActivityFragment extends Fragment {

    private RecentActivityViewModel mViewModel;
    private FragmentRecentActivityBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(RecentActivityViewModel.class);

        binding = FragmentRecentActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRecentActivity;
        mViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}