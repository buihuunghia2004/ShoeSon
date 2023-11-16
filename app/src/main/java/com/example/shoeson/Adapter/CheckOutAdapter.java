package com.example.shoeson.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.databinding.ItemShoeCheckOutBinding;

import java.util.ArrayList;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.ViewHolder> {
    Context context;
    ArrayList<ShoesCart> list;
    public CheckOutAdapter(ArrayList<ShoesCart> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CheckOutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        ItemShoeCheckOutBinding binding=ItemShoeCheckOutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutAdapter.ViewHolder holder, int position) {
        ShoesCart shoesCart=list.get(holder.getAdapterPosition());

        Glide.with(context).load(shoesCart.getLinkImg()).into(holder.binding.imgShoes);
        holder.binding.tvName.setText(shoesCart.getName());
        holder.binding.tvPrice.setText(shoesCart.getPriceSell()+" VNĐ");
        holder.binding.tvSize.setText(String.valueOf(shoesCart.getSize()));
        holder.binding.tvQuantity.setText("x "+shoesCart.getQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemShoeCheckOutBinding binding;
        public ViewHolder(ItemShoeCheckOutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
