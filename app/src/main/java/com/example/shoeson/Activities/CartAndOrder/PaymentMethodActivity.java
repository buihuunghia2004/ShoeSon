package com.example.shoeson.Activities.CartAndOrder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.Activities.ToUpWallet.EnterPINActivity;
import com.example.shoeson.Api.CreateOrder;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.MainActivity;
import com.example.shoeson.Model.Orders;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityPaymentMethodBinding;

import org.json.JSONObject;

import java.util.ArrayList;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentMethodActivity extends AppCompatActivity {
    User user;
    Orders orders;
    ArrayList<ShoesCart> list;
    int method=1;
    ActivityPaymentMethodBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list=new ArrayList<>();
        Intent intent=getIntent();
        if (intent != null){
            user= (User) intent.getSerializableExtra("user");
            orders= (Orders) intent.getSerializableExtra("order");
            list.clear();
            list.addAll(orders.getListShoesCarts());
        }

        //zalopay
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2554, Environment.SANDBOX);



    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.rdbtnWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    binding.rdbtnZalopay.setChecked(false);
                }
            }
        });
        binding.rdbtnZalopay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    binding.rdbtnWallet.setChecked(false);
                }
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.rdbtnWallet.isChecked()){
                    if (user.getMoney() >= orders.getPriceOfOrder()){
                        user.setMoney(user.getMoney()-orders.getPriceOfOrder());
                        Intent intent=new Intent(PaymentMethodActivity.this, EnterPINActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("type",1);
                        bundle.putSerializable("user",user);
                        bundle.putSerializable("order",orders);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Toast.makeText(PaymentMethodActivity.this, "Tiền trong tài khoản không đủ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    CreateOrder orderApi = new CreateOrder();

                    try {
                        JSONObject data = orderApi.createOrder(String.valueOf(orders.getPriceOfOrder()));
                        String code = data.getString("return_code");
                        Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                        if (code.equals("1")) {
                            String token = data.getString("zp_trans_token");
                            Toast.makeText(PaymentMethodActivity.this, "1", Toast.LENGTH_SHORT).show();
                            ZaloPaySDK.getInstance().payOrder(PaymentMethodActivity.this, token, "demozpdk://app", new PayOrderListener() {
                                @Override
                                public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MyFDB.OrderFDB.addOrder(orders, new MyFDB.OrderFDB.IAddOrder() {
                                                @Override
                                                public void onSuccess(String id) {
                                                    Toast.makeText(PaymentMethodActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(PaymentMethodActivity.this, HomePageActivity.class);
                                                    intent.putExtra("user",user);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                }

                                                @Override
                                                public void onFailure(String erorrMessage) {

                                                }
                                            });
                                        }
                                    });
                                    //IsLoading();
                                }

                                @Override
                                public void onPaymentCanceled(String zpTransToken, String appTransID) {
                                    new AlertDialog.Builder(PaymentMethodActivity.this)
                                            .setTitle("User Cancel Payment")
                                            .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .setNegativeButton("Cancel", null).show();
                                }

                                @Override
                                public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                                    new AlertDialog.Builder(PaymentMethodActivity.this)
                                            .setTitle("Payment Fail")
                                            .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .setNegativeButton("Cancel", null).show();
                                }
                            });
                        }else {
                            Toast.makeText(PaymentMethodActivity.this, "2", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}