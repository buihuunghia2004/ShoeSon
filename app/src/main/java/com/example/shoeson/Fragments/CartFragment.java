package com.example.shoeson.Fragments;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoeson.Activities.EpochTime;
import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.Adapter.CartAdapter;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.Orders;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.FragmentCartBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartFragment extends Fragment {
    private ArrayList<ShoesCart> list;
    CartAdapter adapter;
    int totalMoney;
    AlertDialog.Builder builder;
    AlertDialog.Builder builderAddAddress;
    User user;
    Orders orders;
    FragmentCartBinding binding;
    public CartFragment() {
        // Required empty public constructor
    }
    private static CartFragment instance;

    public static CartFragment getInstance(User user) {
        if (instance == null){
            instance=new CartFragment();
        }
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        instance.setArguments(args);
        return instance;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();
        getData();

        adapter=new CartAdapter(list);
        builder=new AlertDialog.Builder(getContext());
        builderAddAddress=new AlertDialog.Builder(getContext());
        Log.d("LLLLL", "getInstance: 1");
        orders=new Orders();
        orders.setIdUser(user.getId());
        orders.setNameUser(user.getName());
        orders.setAddress(user.getAddress());
        orders.setPhoneNumber(user.getPhoneNumber());
        orders.setListShoesCarts(list);
        orders.setStatus(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCartBinding.inflate(inflater);

        binding.rcv.setAdapter(adapter);
        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        setTotalMoney();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        binding.btnTopUp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                orders.setOrderTime(EpochTime.getEpochTime());
                ((HomePageActivity) getContext()).toCheckOutActivity(orders,user);
            }
        });
    }
    private void setTotalMoney(){
        totalMoney=0;
        if (list!=null){
            for (ShoesCart shoesCart:list){
                totalMoney+=shoesCart.getPriceSell()*shoesCart.getQuantity();
            }
        }
        adapter.notifyDataSetChanged();
        binding.tvTotalMoney.setText(totalMoney+" VNĐ");
    }
    private void showDialog(Orders orders){
        builder.setTitle("Xác nhận mua hàng");
        builder.setMessage("message");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý sự kiện khi người dùng ấn OK
                ((HomePageActivity) getContext()).addOrder(orders);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý sự kiện khi người dùng ấn Cancel
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void getData(){
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
            if (user != null){
                list.clear();
                list.addAll(user.getListShoesCarts());
                if (adapter == null){
                    adapter=new CartAdapter(list);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}