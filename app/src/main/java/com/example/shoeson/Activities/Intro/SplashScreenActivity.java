package com.example.shoeson.Activities.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.Activities.VerifyAccount.FillProfileActivity;
import com.example.shoeson.Activities.VerifyAccount.LoginOrRegisterActivity;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.MainActivity;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    //firebase
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        // Add a delay of 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mUser != null){
                    //get User
                    Log.d(">>>>>>", "run: "+mUser.getUid());
                    MyFDB.UsersFDB.getUserByUid(mUser.getUid(), new MyFDB.IGetUserByUid() {
                        @Override
                        public void onSuccess(User user) {
                            if (user!=null){
                                if (user.isProfile()){
                                    Intent intent=new Intent(SplashScreenActivity.this, HomePageActivity.class);
                                    intent.putExtra("user",user);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Intent intent=new Intent(SplashScreenActivity.this, FillProfileActivity.class);
                                    intent.putExtra("user",user);
                                    startActivity(intent);
                                    finish();
                                }
                            }else{
                                MyFDB.UsersFDB.addUser(new User(mUser.getUid(), false), new MyFDB.IAddCallBack() {
                                    @Override
                                    public void onSuccess(boolean b) {
                                        Intent intent=new Intent(SplashScreenActivity.this, FillProfileActivity.class);
                                        intent.putExtra("user",user);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String errorMessage) {

                        }
                    });
                }else{
                    Intent intent = new Intent(SplashScreenActivity.this, LoginOrRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000); // 2000 milliseconds = 2 secondsv
    }
}