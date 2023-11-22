package com.example.shoeson.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    User user;
    //firebase
    FirebaseAuth mAuth;
    FragmentProfileBinding binding;
    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("user",user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user= (User) getArguments().getSerializable("user");
        }
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater);

        Glide.with(getContext()).load(user.getLinkImg()).into(binding.imgAvatar);
        binding.tvName.setText(user.getName());
        binding.tvPhoneNumber.setText(user.getPhoneNumber());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                ((HomePageActivity) getContext()).toLoginOrRegisterActivity();
            }
        });

        binding.chatWithShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomePageActivity) getContext()).toChatWithShopActivity();
            }
        });
    }
}