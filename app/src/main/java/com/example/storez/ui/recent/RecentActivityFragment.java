package com.example.storez.ui.recent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.storez.databinding.FragmentRecentActivityBinding;


public class RecentActivityFragment extends Fragment {

    private RecentActivityViewModel mViewModel;
    private FragmentRecentActivityBinding binding;
    final String TAG = "RecentActivityFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(RecentActivityViewModel.class);

        binding = FragmentRecentActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textRecentActivity;
        ImageView imageView = binding.imageView;
        Glide.with(this)
                .load("https://i.imgur.com/DvpvklR.png")
                .apply(new RequestOptions().disallowHardwareConfig())
                .centerCrop()
                .into(imageView);

        mViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}