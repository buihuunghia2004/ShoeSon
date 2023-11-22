package com.example.shoeson.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoeson.Model.Chat;
import com.example.shoeson.databinding.ItemChattextReceiverBinding;
import com.example.shoeson.databinding.ItemChattextSenderBinding;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int ITEM_SENDER=0;
    private static final int ITEM_RECEIVER=1;

    ArrayList<Chat> list;
    public ChatAdapter(ArrayList<Chat> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_SENDER){
            ItemChattextSenderBinding binding=ItemChattextSenderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new ViewHolderSender(binding);
        }else {
            ItemChattextReceiverBinding binding=ItemChattextReceiverBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new ViewHolderReceiver(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderSender) {
            ((ViewHolderSender) holder).binding.tvContent.setText(list.get(position).getContent());
        } else if (holder instanceof ViewHolderReceiver) {
            ((ViewHolderReceiver) holder).binding.tvContent.setText(list.get(position).getContent());
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    // ViewHolder cho dạng view thứ nhất
    private static class ViewHolderSender extends RecyclerView.ViewHolder {
        ItemChattextSenderBinding binding;
        public ViewHolderSender(ItemChattextSenderBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    // ViewHolder cho dạng view thứ hai
    private static class ViewHolderReceiver extends RecyclerView.ViewHolder {
        ItemChattextReceiverBinding binding;
        public ViewHolderReceiver(ItemChattextReceiverBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
