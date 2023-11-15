package com.example.shoeson.Activities.VerifyAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    //firebase
    private FirebaseAuth mAuth;
    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.edtEmail.getText().toString();
                String password=binding.edtPassword.getText().toString();

                if (checkInfo(email,password)){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            User user=new User(mAuth.getCurrentUser().getUid(),false);
                            MyFDB.UsersFDB.addUser(user, new MyFDB.IAddCallBack() {
                                @Override
                                public void onSuccess(boolean b) {
                                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    });
                }

            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean checkInfo(String email,String password){
        if (!email.isEmpty() || !password.isEmpty()){
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if (password.length() <8){
                    Toast.makeText(this, "Mật khẩu phải chứa ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Toast.makeText(this, "Nhập email đúng định dạng", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(this, "Nhập email và password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}