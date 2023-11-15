package com.example.shoeson.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.FragmentWalletBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class WalletFragment extends Fragment {
    User mUser;
    //
    ProgressDialog progressDialog;
    FragmentWalletBinding binding;
    public WalletFragment() {
        // Required empty public constructor
    }
    public static WalletFragment newInstance(User user) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog=new ProgressDialog(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentWalletBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        MyFDB.UsersFDB.getUserByUid(FirebaseAuth.getInstance().getCurrentUser().getUid(), new MyFDB.IGetUserByUid() {
            @Override
            public void onSuccess(User user) {
                mUser=user;
                binding.tvMoney.setText(user.getMoney()+" VNĐ");
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

        binding.btnTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomePageActivity) getContext()).toRechargeActivity(mUser);
            }
        });

    }
}