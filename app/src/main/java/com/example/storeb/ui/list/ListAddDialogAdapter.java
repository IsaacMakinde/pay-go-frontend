package com.example.storeb.ui.list;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.storeb.R;
import com.example.storeb.models.ProductModel;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ListAddDialogAdapter extends RecyclerView.Adapter<ListAddDialogAdapter.ViewHolder> {
    final String TAG = "ListAddDialogAdapter";
    private List<ProductModel> productList;
    ListViewModel listViewModel;
    ListAddDialogViewModel listAddDialogViewModel;

    public ListAddDialogAdapter(List<ProductModel> productList) {
        this.productList = productList;
        this.listViewModel = new ListViewModel();
        this.listAddDialogViewModel = new ListAddDialogViewModel();
    }

    @NonNull
    @Override
    public ListAddDialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_list_recycle_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAddDialogAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        productList.get(position).setQuantity(1);
        String priceDisplay = String.format(Locale.getDefault(), "%s%.2f", holder.symbol, productList.get(position).getPrice());
        holder.cardviewTitle.setText(productList.get(position).getName());
        holder.cardviewPrice.setText(priceDisplay);
        holder.cardviewDescription.setText(productList.get(position).getDescription());
        Glide.with(holder.cardviewImage.getContext())
                .load(productList.get(position).getImage()).apply(new RequestOptions().disallowHardwareConfig())
                .placeholder(R.drawable.ic_baseline_shopping_basket_24)
                .error(R.drawable.ic_home_black_24dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d(TAG, "onLoadFailed: " + e.getMessage());
                        if (e != null) {
                            e.logRootCauses("Glide Load Failed");
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.cardviewImage);
        holder.cardviewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + productList.get(position).getName());
                listViewModel.addToListItems(productList.get(position));

            }
        });

        Log.d(TAG, "onBindViewHolder: " + productList.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public void setProductList(List<ProductModel> productList) {
        if (productList != null) {
            this.productList = productList;

            notifyDataSetChanged();
            Log.d(TAG, "setProductList: " + getItemCount());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView cardviewTitle;
        TextView cardviewPrice;
        TextView cardviewDescription;
        ImageView cardviewImage;
        Button cardviewAdd;
        Currency currency = Currency.getInstance(Locale.getDefault());
        String symbol;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardviewTitle = itemView.findViewById(R.id.cardview_product_title);
            cardviewPrice = itemView.findViewById(R.id.cardview_product_price);
            cardviewDescription = itemView.findViewById(R.id.cardview_product_description);
            cardviewImage = itemView.findViewById(R.id.cardview_product_image);
            cardviewAdd = itemView.findViewById(R.id.cardview_product_add);
            symbol = currency.getSymbol();
        }

    }
}
