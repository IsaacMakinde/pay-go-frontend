package com.example.storez.ui.list;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.storez.R;
import com.example.storez.models.ProductModel;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    final String TAG = "ListAdapter";
    private List<ProductModel> productList;
    ListViewModel listViewModel;

    public ListAdapter(List<ProductModel> products) {
        this.productList = products;
        this.listViewModel = new ListViewModel();
    }


    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        productList.get(position).setQuantity(1);
        String priceDisplay = String.format("%s%.2f", holder.symbol, productList.get(position).getPrice());
        holder.cardviewTitle.setText(productList.get(position).getName());
        holder.cardviewPrice.setText(priceDisplay);
        holder.cardviewDescription.setText(productList.get(position).getDescription());
        holder.cardviewQuantity.setText(String.valueOf(productList.get(position).getQuantity()));
        Glide.with(holder.cardviewImage.getContext())
                .load(productList.get(position).getImage()).apply(new RequestOptions().disallowHardwareConfig())
                .placeholder(R.drawable.ic_baseline_shopping_basket_24)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e(TAG, "onLoadFailed: ", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "onResourceReady: ");
                        return false;
                    }
                })
                .into(holder.cardviewImage);
        holder.cardviewSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewModel.decreaseQuantity(productList.get(position));
                notifyDataSetChanged();
            }
        });

        holder.cardviewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewModel.increaseQuantity(productList.get(position));
                notifyDataSetChanged();
            }
        });

        if (productList.get(position).isHasScanned()) {
            holder.cardviewCheckbox.setChecked(true);
        } else {
            holder.cardviewCheckbox.setChecked(false);
        }

        holder.cardviewCheckbox.setClickable(false);
        holder.cardviewCheckbox.setFocusable(false);


    }

    public void setProductList(List<ProductModel> productList) {
        if (productList != null) {
            this.productList = productList;
            notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardviewTitle;
        TextView cardviewPrice;
        TextView cardviewDescription;
        TextView cardviewQuantity;
        ImageView cardviewImage;
        Button cardviewAdd;
        Button cardviewSubtract;
        CheckBox cardviewCheckbox;
        Currency currency = Currency.getInstance(Locale.getDefault());
        String symbol;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardviewTitle = itemView.findViewById(R.id.cardview_product_title);
            cardviewPrice = itemView.findViewById(R.id.cardview_product_price);
            cardviewDescription = itemView.findViewById(R.id.cardview_product_description);
            cardviewQuantity = itemView.findViewById(R.id.cardview_product_quantity);
            cardviewImage = itemView.findViewById(R.id.cardview_product_image);
            cardviewCheckbox = itemView.findViewById(R.id.cardview_product_checkbox);
            cardviewAdd = itemView.findViewById(R.id.cardview_product_plus_button);
            cardviewSubtract = itemView.findViewById(R.id.cardview_product_minus_button);
            symbol = currency.getSymbol();
        }

        }
    }

