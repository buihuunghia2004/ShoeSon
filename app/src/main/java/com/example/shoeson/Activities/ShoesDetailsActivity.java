package com.example.shoeson.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoeson.Adapter.SizeAdapter;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.SAQ;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityShoesDetailsBinding;

import java.util.ArrayList;
import java.util.Map;

public class ShoesDetailsActivity extends AppCompatActivity {
    //recycler size
    SizeAdapter adapter;
    ArrayList<String> listSize;
    //data
    int quantity=1;
    int size;
    ArrayList<ShoesCart> list;
    Shoes shoes;
    User user;
    ActivityShoesDetailsBinding biding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biding=ActivityShoesDetailsBinding.inflate(getLayoutInflater());
        setContentView(biding.getRoot());

        Intent intent=getIntent();
        if (intent!=null){
            Bundle bundle=intent.getExtras();
            if (bundle!=null){
                shoes= (Shoes) bundle.getSerializable("shoes");
                user= (User) bundle.getSerializable("user");
                list=user.getListShoesCarts();
                listSize=new ArrayList<>();
                for (Map.Entry<String, Integer> entry : shoes.getSaq().getHashMapSize().entrySet()) {
                    String key=entry.getKey();
                    listSize.add(key);
                }
            }
        }
        //thông tin cơ bản
        Glide.with(this).load(shoes.getLinkImg()).into(biding.imgShoes);
        biding.tvName.setText(shoes.getName());
        biding.tvPrice.setText(shoes.getPriceSell()+" VNĐ");

        //quantity
        biding.tvQuantity.setText(String.valueOf(quantity));

        //size
        adapter=new SizeAdapter(listSize);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        biding.rcvSize.setLayoutManager(linearLayoutManager);
        biding.rcvSize.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        biding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        biding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity+=1;
                biding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        biding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity!=1){
                    quantity-=1;
                    biding.tvQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        biding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoesCart shoesCart=new ShoesCart(shoes);
                shoesCart.setQuantity(quantity);
                shoesCart.setSize(size);

                if (checkContain(list,shoesCart.getId(),size)){
                    list.add(shoesCart);
                    MyFDB.UsersFDB.addCartUser(user.getId(), list, new MyFDB.IAddCallBack() {
                        @Override
                        public void onSuccess(boolean b) {
                            Toast.makeText(ShoesDetailsActivity.this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(ShoesDetailsActivity.this, "Sản phẩm đã có sẵn trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void chooseSize(int size){
        this.size=size;
    }
    private boolean checkContain(ArrayList<ShoesCart> list,String id,int size){
        for (ShoesCart shoesCart:list){
            if (shoesCart.getId().equals(id)){
                if (shoesCart.getSize() == size){
                    return false;
                }
            }
        }
        return true;
    }
}