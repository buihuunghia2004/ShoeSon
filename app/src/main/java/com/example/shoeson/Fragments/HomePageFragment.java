package com.example.shoeson.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.shoeson.Activities.ShoesDetailsActivity;
import com.example.shoeson.Adapter.GridBrandAdapter;
import com.example.shoeson.Adapter.ShoesHomeAdapter;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.Brand;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.FragmentHomePageBinding;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {
    //firebase
    List<Brand> listBrand;
    ArrayList<Shoes> listShoe;
    GridBrandAdapter adapter;
    ShoesHomeAdapter shoesHomeAdapter;
    //proccess dialog
    ProgressDialog progressDialog;
    //
    User user;
    Context context;
    FragmentHomePageBinding binding;
    public static HomePageFragment newInstance(ArrayList<Shoes> listShoes, ArrayList<Brand> listBrand, User user) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putSerializable("listshoes",listShoes);
        args.putSerializable("listbrand",listBrand);
        args.putSerializable("user",user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();

        if (getArguments() != null) {
            listBrand= (List<Brand>) getArguments().getSerializable("listbrand");
            listShoe= (ArrayList<Shoes>) getArguments().getSerializable("listshoes");
            user= (User) getArguments().getSerializable("user");
            Log.d(">>>>>", "onCreate: "+user.getId());
            Log.d(">>>>>", "onCreate: "+user.getName());
        }else{
            listShoe=new ArrayList<>();
            listBrand=new ArrayList<>();
        }

        shoesHomeAdapter=new ShoesHomeAdapter(listShoe);
        adapter=new GridBrandAdapter(listBrand);
        progressDialog=new ProgressDialog(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomePageBinding.inflate(inflater);

        binding.tvName.setText(user.getName());
        Glide.with(getContext()).load(user.getLinkImg()).into(binding.imgAvatar);
        // Inflate the layout for this fragment
        binding.gridView.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        binding.rcvShoes.setLayoutManager(gridLayoutManager);
        binding.rcvShoes.setAdapter(shoesHomeAdapter);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public void toShoesDetailsActivity(){
        Intent intent=new Intent(context, ShoesDetailsActivity.class);
        startActivity(intent);
    }
}