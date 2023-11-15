package com.example.shoeson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.Model.Test;
import com.example.shoeson.Model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShoesCart shoesCart=new ShoesCart("1","s1","link1",1,1,"b1",1,35);
        ShoesCart shoesCart2=new ShoesCart("2","s2","link2",2,2,"b2",2,36);
        ShoesCart shoesCart3=new ShoesCart("3","s3","link3",3,3,"b3",3,37);
        ArrayList<ShoesCart> list=new ArrayList<>();
        list.add(shoesCart);
        list.add(shoesCart2);
        list.add(shoesCart3);

        User user=new User("id",true,"name","linkImg","09999999","address",100,"1111",list);
        MyFDB.UsersFDB.addCartUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), list, new MyFDB.IAddCallBack() {
            @Override
            public void onSuccess(boolean b) {
                Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });
    }
}