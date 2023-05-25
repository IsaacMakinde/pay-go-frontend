package com.example.storeb.ui.recent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.storeb.R;
import com.example.storeb.databinding.FragmentRecentActivityBinding;


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
                .load("https://digitalcontent.api.tesco.com/v2/media/ghs/9bd0452c-e6b2-4345-bf26-4a840f1fa1bf/snapshotimagehandler_570571010.jpeg?h=225&w=225")
                .placeholder(R.drawable.ic_baseline_shopping_basket_24)
                .error(R.drawable.ic_home_black_24dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Handle the image loading failure
                        Log.d(TAG, "onLoadFailed: " + e.getMessage());
                        e.logRootCauses("onLoadFailed: ");
                        return false; // Return false if you want Glide to handle the failure as usual
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // Image successfully loaded
                        return false; // Return false if you want Glide to handle the successful loading as usual
                    }
                })
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