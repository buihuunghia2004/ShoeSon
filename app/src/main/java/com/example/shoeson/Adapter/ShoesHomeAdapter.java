package com.example.shoeson.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.Activities.ShoesDetailsActivity;
import com.example.shoeson.Fragments.HomePageFragment;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.databinding.ItemShoeHomeBinding;

import java.util.ArrayList;

public class ShoesHomeAdapter extends RecyclerView.Adapter<ShoesHomeAdapter.ViewHolder> {
    Context context;
    ArrayList<Shoes> list;
    public ShoesHomeAdapter(ArrayList<Shoes> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ShoesHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        ItemShoeHomeBinding binding=ItemShoeHomeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesHomeAdapter.ViewHolder holder, int position) {
        Shoes shoes=list.get(position);

        Glide.with(context).load(shoes.getLinkImg()).into(holder.binding.imgShoes);
        holder.binding.tvName.setText(shoes.getName());
        holder.binding.tvPrice.setText(String.valueOf(shoes.getPriceSell()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomePageActivity) context).toShoesDetailsActivity(shoes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemShoeHomeBinding binding;
        public ViewHolder(ItemShoeHomeBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
