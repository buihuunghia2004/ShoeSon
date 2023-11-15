package com.example.shoeson.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoeson.Activities.ShoesDetailsActivity;
import com.example.shoeson.databinding.ItemSizeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {
    ArrayList<String> listKey;
    public SizeAdapter(ArrayList<String> listKey) {
        this.listKey=listKey;
    }
    ViewHolder viewHolder;
    Context context;
    int isSelected;
    @NonNull
    @Override
    public SizeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        ItemSizeBinding binding=ItemSizeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeAdapter.ViewHolder holder, int position) {
        String key=listKey.get(position);

        holder.binding.btnSize.setText(key);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder == null){
                    holder.binding.btnSize.setSelected(true);
                }else{
                    viewHolder.binding.btnSize.setSelected(false);
                    holder.binding.btnSize.setSelected(true);
                }
                viewHolder=holder;
                isSelected=holder.getAdapterPosition();

                ((ShoesDetailsActivity) context).chooseSize(Integer.parseInt(key));
            }
        });
    }
    @Override
    public int getItemCount() {
        return listKey.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSizeBinding binding;
        public ViewHolder(ItemSizeBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    private void setSelected(){

    }
}
