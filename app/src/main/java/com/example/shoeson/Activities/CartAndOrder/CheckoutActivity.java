package com.example.shoeson.Activities.CartAndOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.shoeson.Adapter.CheckOutAdapter;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.Orders;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityCheckoutBinding;
import com.example.shoeson.databinding.DialogAddAddressBinding;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    Orders orders;
    User user;
    ArrayList<ShoesCart> list;
    CheckOutAdapter adapter;
    ActivityCheckoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent=getIntent();
        if (intent!=null){
            orders= (Orders) intent.getSerializableExtra("order");
            user= (User) intent.getSerializableExtra("user");
            list=user.getListShoesCarts();
        }else{
            orders=new Orders();
        }

        if (user.getAddress() != null){
            binding.tvDetailAddress.setText(user.getAddress());
            orders.setAddress(user.getAddress());
        }else{
            binding.tvDetailAddress.setText("Thêm địa chỉ");
        }

        adapter=new CheckOutAdapter(list);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this));
        binding.rcv.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddDress();
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void showDialogAddDress(){
        Dialog dialog=new Dialog(this);
        DialogAddAddressBinding dialogBiding=DialogAddAddressBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBiding.getRoot());

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Kích thước chiều rộng của Dialog
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // Kích thước chiều cao của Dialog
            window.setAttributes(layoutParams);
        }

        dialogBiding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address=dialogBiding.edtAddress.getText().toString();
                if (!address.isEmpty()){
                    user.setAddress(address);
                    MyFDB.UsersFDB.updateAddress(user.getId(), address, new MyFDB.IAddCallBack() {
                        @Override
                        public void onSuccess(boolean b) {
                            if (b){
                                orders.setAddress(address);
                                binding.tvDetailAddress.setText(address);
                                Toast.makeText(CheckoutActivity.this, "ok", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CheckoutActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        dialogBiding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}