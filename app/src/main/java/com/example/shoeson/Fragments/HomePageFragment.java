package com.example.shoeson.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoeson.Activities.ShoesDetailsActivity;
import com.example.shoeson.Adapter.GridBrandAdapter;
import com.example.shoeson.Adapter.ResultShoeAdapter;
import com.example.shoeson.Adapter.ShoesHomeAdapter;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Interfaces.ISetBrandAdapter;
import com.example.shoeson.Model.Brand;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.FragmentHomePageBinding;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {
    //firebase
    List<Brand> listBrand;
    ArrayList<Shoes> listShoe;
    ArrayList<Shoes> listShoeStamp;
    ArrayList<Shoes> listReult;
    GridBrandAdapter adapter;
    ShoesHomeAdapter shoesHomeAdapter;
    ResultShoeAdapter resultShoeAdapter;
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

        listShoeStamp=new ArrayList<>();
        listShoeStamp.addAll(listShoe);
        listReult=new ArrayList<>();

        shoesHomeAdapter=new ShoesHomeAdapter(listShoeStamp);
        adapter=new GridBrandAdapter(listBrand);
        resultShoeAdapter=new ResultShoeAdapter(listReult,context);
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
        binding.rcvResult.setAdapter(resultShoeAdapter);
        binding.rcvResult.setLayoutManager(new LinearLayoutManager(context));
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(">>>>>", "setBrandApter40: "+listShoe.size());
        adapter.ISetBrandAdapterListener(new ISetBrandAdapter() {
            @Override
            public void setBrandApter(String id) {
                listShoeStamp.clear();
                Log.d(">>>>>", "setBrandApter40: "+listShoe.size());
                for (Shoes shoes:listShoe){
                    if (shoes.getIdBrand().equals(id)){
                        listShoeStamp.add(shoes);
                        Log.d(">>>>>", "setBrandApter3: "+listShoeStamp.size());
                    }
                }
                shoesHomeAdapter.notifyDataSetChanged();
            }
        });
        binding.sv.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listReult.clear();
                for (Shoes shoes:listShoe){
                    if (shoes.getName().toLowerCase().contains(s.toString().toLowerCase())){
                        listReult.add(shoes);
                    }
                }
                resultShoeAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listShoeStamp.clear();
                listShoeStamp.addAll(listShoe);
                shoesHomeAdapter.notifyDataSetChanged();
            }
        });
    }
}