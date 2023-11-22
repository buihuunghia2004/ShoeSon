package com.example.shoeson.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.databinding.ItemShoeOrderBinding;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private static final int TYPE_1=1;
    private static final int TYPE_2=2;
    private static final int TYPE_3=3;
    private static final int TYPE_4=4;
    Context context;
    ArrayList<ShoesCart> list;
    int type;

    public OrderAdapter(ArrayList<ShoesCart> list,int type) {
        this.list = list;
        this.type=type;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShoeOrderBinding binding=ItemShoeOrderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context= parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        ShoesCart shoesCart=list.get(position);

        Glide.with(context).load(shoesCart.getLinkImg()).into(holder.binding.imgShoes);
        holder.binding.tvName.setText(shoesCart.getName());
        holder.binding.tvPrice.setText(shoesCart.getPriceSell()+" VNĐ");
        holder.binding.tvSize.setText(String.valueOf(shoesCart.getSize()));

        switch (type){
            case TYPE_1:
                holder.binding.btnOptions.setText("Chi tiết");
                break;
            case TYPE_2:
                holder.binding.btnOptions.setText("Mua lại");
                break;
            case TYPE_3:break;
            case TYPE_4:break;
        }
        holder.binding.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case TYPE_1:

                        break;
                    case TYPE_2:break;
                    case TYPE_3:break;
                    case TYPE_4:break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemShoeOrderBinding binding;
        public ViewHolder(ItemShoeOrderBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
