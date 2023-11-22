package com.example.shoeson.Activities.ToUpWallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Fragments.OrdersFragment;
import com.example.shoeson.Model.Orders;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityEnterPinactivityBinding;

import java.util.ArrayList;

public class EnterPINActivity extends AppCompatActivity {
    private final int REACHARGE=0;
    private final int PAYMENT=1;
    int type=0;
    User user;
    Orders orders;
    String pin="";
    int stamp=0;
    EditText[] editTexts;
    ActivityEnterPinactivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEnterPinactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent=getIntent();
        if (intent!=null){
            user= (User) intent.getSerializableExtra("user");
            type=intent.getIntExtra("type",0);
            if (type==1){
                orders= (Orders) intent.getSerializableExtra("order");
            }
        }

        editTexts=new EditText[]{binding.edtPIN1,binding.edtPIN2,binding.edtPIN3,binding.edtPIN4};
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.tv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="0";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="1";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="2";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="3";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="4";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="5";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="6";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="7";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="8";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()<4){
                    pin+="9";
                    stamp++;
                    setPin();
                }
            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.length()>0){
                    pin=pin.substring(0,pin.length()-1);
                    stamp--;
                    setPin();
                }
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pin.isEmpty()){
                    if (user.getPin().equals(pin)){
                        if (type == REACHARGE){
                            MyFDB.UsersFDB.updateMoney(user.getId(), user.getMoney(), new MyFDB.IAddCallBack() {
                                @Override
                                public void onSuccess(boolean b) {
                                    if (b) {
                                        Toast.makeText(EnterPINActivity.this, "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(EnterPINActivity.this, "Thât bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            orders.setStatus(1);
                            MyFDB.OrderFDB.addOrder(orders, new MyFDB.OrderFDB.IAddOrder() {
                                @Override
                                public void onSuccess(String id) {
                                    //update các thông tin cho user
                                    //1.danh sách order
                                    ArrayList<String> listIdOrder=user.getListIdOrder();
                                    listIdOrder.add(id);
                                    user.setListIdOrder(listIdOrder);
                                    //2.danh sách giỏ hàng
                                    user.setListShoesCarts(new ArrayList<>());

                                    MyFDB.UsersFDB.addUser(user, new MyFDB.IAddCallBack() {
                                        @Override
                                        public void onSuccess(boolean b) {
                                            Intent intent=new Intent(EnterPINActivity.this, HomePageActivity.class);
                                            intent.putExtra("user",user);
                                            startActivity(intent);
                                            finishAffinity();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(String erorrMessage) {

                                }
                            });
                        }
                    }
                }
            }
        });
    }
    private void setPin(){
        for (int i=0;i<4;i++){
            editTexts[i].setText("");
        }
        for (int i=0;i<stamp;i++){
            editTexts[i].setText("1");
        }
    }
}