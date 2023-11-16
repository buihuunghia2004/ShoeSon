package com.example.shoeson.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.databinding.ItemShoeResultBinding;

import java.util.ArrayList;

public class ResultShoeAdapter extends RecyclerView.Adapter<ResultShoeAdapter.ViewHolder> {
    Context context;
    ArrayList<Shoes> list;

    public ResultShoeAdapter(ArrayList<Shoes> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public ResultShoeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShoeResultBinding binding=ItemShoeResultBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultShoeAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getLinkImg()).into(holder.binding.imgShoes);
        holder.binding.tvName.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomePageActivity) context).toShoesDetailsActivity(list.get(holder.getAdapterPosition()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemShoeResultBinding binding;
        public ViewHolder(ItemShoeResultBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
