package com.example.shoeson.Activities.ToUpWallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.shoeson.Activities.VerifyAccount.RegisterActivity;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityRechargeBinding;

public class RechargeActivity extends AppCompatActivity {
    User user;
    ActivityRechargeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRechargeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent=getIntent();
        if (intent!=null){
            user= (User) intent.getSerializableExtra("user");
            Log.d(">>>>>", "onCreate: m"+user.getMoney());
        }else{
            Log.d(">>>>>", "onCreate: null");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int money=Integer.parseInt(binding.edtInputMoney.getText().toString());
                user.setMoney(user.getMoney()+money);
                Intent intent=new Intent(RechargeActivity.this,EnterPINActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",user);
                bundle.putInt("type",0);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        binding.tv10k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtInputMoney.setText("10000");
            }
        });
        binding.tv20k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtInputMoney.setText("20000");
            }
        });
        binding.tv50k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtInputMoney.setText("50000");
            }
        });

        binding.tv100k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtInputMoney.setText("100000");
            }
        });
        binding.tv200k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtInputMoney.setText("200000");
            }
        });
        binding.tv500k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtInputMoney.setText("500000");
            }
        });

        binding.tv1000k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtInputMoney.setText("1000000");
            }
        });
        binding.tv2000k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtInputMoney.setText("2000000");
            }
        });
        binding.tv5000k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtInputMoney.setText("5000000");
            }
        });


    }
}