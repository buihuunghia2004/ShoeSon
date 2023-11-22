package com.example.shoeson.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shoeson.Activities.CartAndOrder.CheckoutActivity;
import com.example.shoeson.Activities.ToUpWallet.RechargeActivity;
import com.example.shoeson.Activities.VerifyAccount.LoginOrRegisterActivity;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Fragments.CartFragment;
import com.example.shoeson.Fragments.HomePageFragment;
import com.example.shoeson.Fragments.OrdersFragment;
import com.example.shoeson.Fragments.ProfileFragment;
import com.example.shoeson.Fragments.WalletFragment;
import com.example.shoeson.Model.Brand;
import com.example.shoeson.Model.Orders;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityHomePageBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {
    //fragment home page
    ArrayList<Shoes> listShoes;
    ArrayList<Brand> listBrand;
    ArrayList<Orders> listOrders;
    //User
    User user;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment homeFragemt,walletFragment,profileFragment;
    OrdersFragment ordersFragment;
    CartFragment cartFragment;
    //process dialog
    ProgressDialog progressDialog;
    //listenner
    EventListener<DocumentSnapshot> userListener;
    EventListener<QuerySnapshot> orderListener;
    ActivityHomePageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent=getIntent();
        if (intent!=null){
            user= (User) intent.getSerializableExtra("user");
            if (user.getListShoesCarts() == null){
                user.setListShoesCarts(new ArrayList<>());
            }
        }

        listShoes=new ArrayList<>();
        listBrand=new ArrayList<>();
        listOrders=new ArrayList<>();

        ordersFragment=OrdersFragment.newInstance(listOrders);
        walletFragment=WalletFragment.newInstance(user);
        profileFragment=ProfileFragment.newInstance(user);

        progressDialog=new ProgressDialog(this);

        upLoadDataFirst();

        //listener
        userListener=new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                user=value.toObject(User.class);
            }
        };
        orderListener=new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        //upLoadShoesData();
        //upLoadBrandData();
        //upLoadUserData();
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                int id_item_home=R.id.item_home;
                int id_item_cart=R.id.item_cart;
                int id_item_order=R.id.item_order;
                int id_item_wallet=R.id.item_wallet;
                int id_item_profile=R.id.item_profile;

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (id==id_item_home){
                    fragmentTransaction.replace(binding.frameMain.getId(),homeFragemt);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else if (id==id_item_cart){
                    cartFragment=CartFragment.getInstance(user);
                    fragmentTransaction.replace(binding.frameMain.getId(),cartFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else if (id==id_item_order){
                    ordersFragment=OrdersFragment.newInstance(listOrders);
                    fragmentTransaction.replace(binding.frameMain.getId(),ordersFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else if (id==id_item_wallet){
                    fragmentTransaction.replace(binding.frameMain.getId(),walletFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    fragmentTransaction.replace(binding.frameMain.getId(),profileFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });

        //lắng nghe thay đổi
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                MyFDB.UsersFDB.crUser.document(user.getId()).addSnapshotListener(userListener);
                for (int i=0;i<listOrders.size();i++){
                    int finalI = i;
                    Log.d(">>>>>", "onResume: "+listOrders.get(i).getId());
                    MyFDB.OrderFDB.crOrder.document(listOrders.get(i).getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            Orders orders=value.toObject(Orders.class);
                            listOrders.set(finalI,orders);
                            Log.d("LLLL", "onEvent: "+listOrders.size());
                            ordersFragment=OrdersFragment.newInstance(listOrders);
                            ordersFragment.updateData();
                        }
                    });
                }
            }
        });
    }
    private void upLoadDataFirst(){
        showProcessDialog();
        MyFDB.ShoeFDB.getAllShoes(new MyFDB.ShoeFDB.IGetShoesCallBack() {
            @Override
            public void onSuccess(ArrayList<Shoes> list) {
                listShoes.clear();
                listShoes.addAll(list);

                homeFragemt=HomePageFragment.newInstance(listShoes,listBrand,user);

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(binding.frameMain.getId(),homeFragemt);
                fragmentTransaction.commit();
            }
            @Override
            public void onFailure(String errorMesage) {

            }
        });
        MyFDB.BrandFDB.getAllBrand(new MyFDB.IGetAllObjectCallBack() {
            @Override
            public void onSuccess(ArrayList<Object> list) {
                listBrand.clear();
                for (Object object:list){
                    listBrand.add((Brand) object);
                }
            }
            @Override
            public void onFailure(String errorMesage) {

            }
        });
        MyFDB.UsersFDB.getUserByUid(user.getId(), new MyFDB.IGetUserByUid(){
            @Override
            public void onSuccess(User users) {
                user=users;
                MyFDB.OrderFDB.getAllOrdersById(user.getId(), new MyFDB.OrderFDB.IGetAllOrder() {
                    @Override
                    public void onSuccess(ArrayList<Orders> list) {
                        listOrders.clear();
                        listOrders.addAll(list);
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(String erorrMessage) {

                    }
                });
            }
            @Override
            public void onFailure(String errorMessage) {

            }
        });

    }
    private void showProcessDialog()
    {
        progressDialog.setTitle("HowKteam");
        progressDialog.setMessage("Dang xu ly...");
        progressDialog.show();
    }

    //chuyển activity
    public void toShoesDetailsActivity(Shoes shoes){
        Intent intent=new Intent(this, ShoesDetailsActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("shoes",shoes);
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void toRechargeActivity(User user){
        Log.d(">>>>>", "toRechargeActivity: "+user.getMoney());
        Intent intent=new Intent(this, RechargeActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void toCheckOutActivity(Orders orders,User user){
        Intent intent=new Intent(this, CheckoutActivity.class);
        intent.putExtra("order",orders);
        intent.putExtra("user",user);
        startActivity(intent);
    }
    //fragment Cart
    public void setQuantityShoesCart(int position, int quantity){
        ShoesCart newShoesCart=user.getListShoesCarts().get(position);
        newShoesCart.setQuantity(quantity);
        user.setSize(position,newShoesCart);
    }
    public void removeShoesCart(int position){
        ArrayList<ShoesCart> list=user.getListShoesCarts();
        list.remove(position);
        MyFDB.UsersFDB.addCartUser(user.getId(), list, new MyFDB.IAddCallBack() {
            @Override
            public void onSuccess(boolean b) {
                Log.d(">>>>", "onSuccess: xóa item shoeCart thành công");
                user.setListShoesCarts(list);
            }
        });
    }
    public void addOrder(Orders orders){
        MyFDB.OrderFDB.addOrder(orders, new MyFDB.OrderFDB.IAddOrder() {
            @Override
            public void onSuccess(String id) {
                //Xóa danh sách trong giỏ hàng
                MyFDB.UsersFDB.addCartUser(user.getId(), new ArrayList<ShoesCart>(), new MyFDB.IAddCallBack() {
                    @Override
                    public void onSuccess(boolean b) {
                        Toast.makeText(HomePageActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String erorrMessage) {

            }
        });
    }
    //fragment Order

    //fragment Profile
    public void toLoginOrRegisterActivity(){
        Intent intent=new Intent(HomePageActivity.this, LoginOrRegisterActivity.class);
        startActivity(intent);
        finishAffinity();
    }
    public void toChatWithShopActivity(){
        Intent intent=new Intent(this, ChatActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }
}