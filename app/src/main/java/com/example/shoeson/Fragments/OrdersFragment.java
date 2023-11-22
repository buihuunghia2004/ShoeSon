package com.example.shoeson.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoeson.Adapter.CartAdapter;
import com.example.shoeson.Adapter.OrderAdapter;
import com.example.shoeson.Model.Orders;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.FragmentOrdersBinding;
import com.google.android.material.tabs.TabLayout;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {
    ArrayList<Orders> listOrder;
    ArrayList<ShoesCart> listShoesCart;
    OrderAdapter adapter;
    private static OrdersFragment instance;
    FragmentOrdersBinding binding;
    public OrdersFragment() {
        // Required empty public constructor
    }

    public static OrdersFragment newInstance(ArrayList<Orders> list) {
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
        listOrder=new ArrayList<>();
        listShoesCart=new ArrayList<>();
        if (getArguments() != null) {
            listOrder= (ArrayList<Orders>) getArguments().getSerializable("list");
        }

        listShoesCart=getListShoesCartByType(1);
        adapter=new OrderAdapter(listShoesCart,1);
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
        binding.tly.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        listShoesCart.clear();
                        listShoesCart.addAll(getListShoesCartByType(1));
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        listShoesCart.clear();
                        listShoesCart.addAll(getListShoesCartByType(2));
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        listShoesCart.clear();
                        listShoesCart.addAll(getListShoesCartByType(3));
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private ArrayList<ShoesCart> getListShoesCartByType(int type){
        ArrayList<ShoesCart> list=new ArrayList<>();

        for (Orders orders:listOrder){
            if (type == orders.getStatus()){
                list.addAll(orders.getListShoesCarts());
            }
        }
        return list;
    }
    public void updateData(){
        if (getArguments() != null) {
            listOrder= (ArrayList<Orders>) getArguments().getSerializable("list");

            if (listShoesCart != null){
                listShoesCart.clear();
                listShoesCart.addAll(getListShoesCartByType(binding.tly.getSelectedTabPosition()+1));
                adapter.notifyDataSetChanged();
            }
        }
    }
}