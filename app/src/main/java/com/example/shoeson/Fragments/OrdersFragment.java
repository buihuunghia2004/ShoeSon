package com.example.shoeson.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoeson.Adapter.OrderAdapter;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.R;
import com.example.shoeson.databinding.FragmentOrdersBinding;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {

    ArrayList<ShoesCart> list;
    OrderAdapter adapter;
    private static OrdersFragment instance;
    FragmentOrdersBinding binding;
    public OrdersFragment() {
        // Required empty public constructor
    }

    public static OrdersFragment newInstance(ArrayList<ShoesCart> list) {
        if (instance == null){
            instance=new OrdersFragment();
        }
        Bundle args = new Bundle();
        args.putSerializable("list",list);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();
        if (getArguments() != null) {
            list= (ArrayList<ShoesCart>) getArguments().getSerializable("list");
        }
        adapter=new OrderAdapter(list,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentOrdersBinding.inflate(inflater);

        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcv.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}