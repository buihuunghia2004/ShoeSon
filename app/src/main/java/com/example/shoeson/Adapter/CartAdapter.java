package com.example.shoeson.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.Fragments.CartFragment;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.databinding.FragmentCartBinding;
import com.example.shoeson.databinding.ItemShoeCartBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private static final int REDUCE=0;
    private static final int ADD=1;
    Context context;
    ArrayList<ShoesCart> list;
    public CartAdapter(ArrayList<ShoesCart> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        ItemShoeCartBinding binding=ItemShoeCartBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        ShoesCart shoesCart=list.get(position);

        Glide.with(context).load(shoesCart.getLinkImg()).into(holder.binding.imgShoes);
        holder.binding.tvName.setText(shoesCart.getName());
        holder.binding.tvPrice.setText(shoesCart.getPriceSell()+" VNĐ");
        holder.binding.tvSize.setText(String.valueOf(shoesCart.getSize()));

        holder.binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomePageActivity) context).removeShoesCart(holder.getAdapterPosition());

                list.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        holder.binding.btnReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shoesCart.getQuantity() > 1){
                    shoesCart.setQuantity(shoesCart.getQuantity()-1);
                    ((HomePageActivity) context).setQuantityShoesCart(holder.getAdapterPosition(),shoesCart.getQuantity());
                    holder.binding.tvQuantity.setText(String.valueOf(shoesCart.getQuantity()));
                }
            }
        });
        holder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoesCart.setQuantity(shoesCart.getQuantity()+1);
                ((HomePageActivity) context).setQuantityShoesCart(holder.getAdapterPosition(),shoesCart.getQuantity());
                holder.binding.tvQuantity.setText(String.valueOf(shoesCart.getQuantity()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemShoeCartBinding binding;
        public ViewHolder(ItemShoeCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
