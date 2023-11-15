package com.example.shoeson.Activities.VerifyAccount;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityLoginOrRegisterBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginOrRegisterActivity extends AppCompatActivity {
    //login gg
    ActivityResultLauncher<Intent> loginGGLaucher;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;

    //
    ActivityLoginOrRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginOrRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginGGLaucher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {

                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(o.getData());
                        try {
                            // Lấy thông tin người dùng đã đăng nhập thành công
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            String idToken = account.getIdToken();

                            // Sử dụng idToken để xác thực với Firebase Authentication
                            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                    .addOnCompleteListener(LoginOrRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Đăng nhập thành công
                                                Toast.makeText(LoginOrRegisterActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

                                                User user=new User(mUser.getUid(),false);
                                                //kiểm tra tồn tại của tài khoản
                                                MyFDB.UsersFDB.getUserByUid(mUser.getUid(), new MyFDB.IGetUserByUid() {
                                                    @Override
                                                    public void onSuccess(User user) {
                                                        if (user!=null){
                                                            if (user.isProfile()){
                                                                Intent intent=new Intent(LoginOrRegisterActivity.this, HomePageActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }else{
                                                                Intent intent=new Intent(LoginOrRegisterActivity.this, FillProfileActivity.class);
                                                                intent.putExtra("user",user);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }else{
                                                            MyFDB.UsersFDB.addUser(new User(mUser.getUid(), false), new MyFDB.IAddCallBack() {
                                                                @Override
                                                                public void onSuccess(boolean b) {
                                                                    Intent intent=new Intent(LoginOrRegisterActivity.this, FillProfileActivity.class);
                                                                    intent.putExtra("user",user);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            });
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(String errorMessage) {
                                                        //tạo tài khoản mới
                                                        MyFDB.UsersFDB.addUser(user, new MyFDB.IAddCallBack() {
                                                            @Override
                                                            public void onSuccess(boolean b) {
                                                                Intent intent=new Intent(LoginOrRegisterActivity.this, FillProfileActivity.class);
                                                                intent.putExtra("user",user);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                });
                                            } else {
                                                // Đăng nhập thất bại
                                                // Xử lý lỗi
                                            }
                                        }
                                    });
                        } catch (ApiException e) {
                            // Xử lý lỗi
                        }
                    }
                });
        // Khởi tạo GoogleSignInOptions
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Khởi tạo GoogleSignInClient
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Khi người dùng nhấn nút đăng nhập bằng Google
        binding.btnGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                loginGGLaucher.launch(signInIntent);
            }
        });

        binding.btnLoginWithPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginOrRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginOrRegisterActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}