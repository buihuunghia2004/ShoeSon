package com.example.shoeson.Activities.VerifyAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityLoginBinding;
import com.example.shoeson.databinding.ActivityLoginOrRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    User user;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String email=binding.edtEmail.getText().toString();
                String password=binding.edtPassword.getText().toString();
                if (checkInfo(email,password)){
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                MyFDB.UsersFDB.getUserByUid(mAuth.getCurrentUser().getUid(), new MyFDB.IGetUserByUid() {
                                    @Override
                                    public void onSuccess(User user) {
                                        progressDialog.dismiss();
                                        if (user.isProfile()){
                                            Intent intent=new Intent(LoginActivity.this, HomePageActivity.class);
                                            intent.putExtra("user",user);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Intent intent=new Intent(LoginActivity.this, FillProfileActivity.class);
                                            intent.putExtra("user",user);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                    @Override
                                    public void onFailure(String errorMessage) {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        binding.tvRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
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