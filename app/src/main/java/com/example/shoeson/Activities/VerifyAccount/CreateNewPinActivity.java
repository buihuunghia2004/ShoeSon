package com.example.shoeson.Activities.VerifyAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.Activities.ToUpWallet.EnterPINActivity;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityCreateNewPinBinding;

public class CreateNewPinActivity extends AppCompatActivity {
    User user;
    String pin="";
    int stamp=0;
    EditText[] editTexts;
    ActivityCreateNewPinBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCreateNewPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent=getIntent();
        if (intent!=null){
            user= (User) intent.getSerializableExtra("user");
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
                user.setPin(pin);
                MyFDB.UsersFDB.addUser(user, new MyFDB.IAddCallBack() {
                    @Override
                    public void onSuccess(boolean b) {
                        Toast.makeText(CreateNewPinActivity.this, "ok", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CreateNewPinActivity.this, HomePageActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                    }
                });
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