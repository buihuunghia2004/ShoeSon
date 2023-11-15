package com.example.shoeson.Activities.VerifyAccount;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.shoeson.Activities.HomePageActivity;
import com.example.shoeson.FirebaseDatabase.MyFDB;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityFillProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FillProfileActivity extends AppCompatActivity {
    User user;
    ActivityResultLauncher<Intent> selectImgLauncher;
    Uri imgUri;
    String linkImg;

    //firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;
    //process dialog
    ProgressDialog progressDialog;
    ActivityFillProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFillProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog=new ProgressDialog(this);
        //nhận user
        Intent intent=getIntent();
        if (intent != null){
            user= (User) intent.getSerializableExtra("user");
            Log.d(">>>>>", "onCreate: "+user.getId());
        }

        //launcher image
        selectImgLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imgUri = data.getData();
                            binding.imgAvatar.setImageURI(imgUri);
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //chọn avatar
        binding.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                selectImgLauncher.launch(intent);
            }
        });

        //continue
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=binding.edtName.getText().toString();
                String phoneNumber=binding.edtPhoneNumber.getText().toString();
                if (checkInfo()){
                    showProcessDialog();
                    //up ảnh lên firebase
                    storageRef= storage.getReference().child("imgAvatar/"+user.getId()+".png");
                    UploadTask uploadTask=storageRef.putFile(imgUri);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    linkImg=uri.toString();linkImg=uri.toString();

                                    user.setName(name);
                                    user.setPhoneNumber(phoneNumber);
                                    user.setLinkImg(linkImg);
                                    user.setProfile(true);

                                    MyFDB.UsersFDB.addUser(user, new MyFDB.IAddCallBack() {
                                        @Override
                                        public void onSuccess(boolean b) {
                                            progressDialog.dismiss();
                                            Intent intent=new Intent(FillProfileActivity.this, CreateNewPinActivity.class);
                                            intent.putExtra("user",user);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }
    private boolean checkInfo(){

        if (imgUri==null){
            Toast.makeText(this, "Vui lòng chọn ảnh đại diện", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.edtName.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return false;
        }else if (binding.edtPhoneNumber.toString().isEmpty() || !Patterns.PHONE.matcher(binding.edtPhoneNumber.getText().toString()).matches()){
            Toast.makeText(this, "Chưa nhập số điện thoại hoặc số không đúng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void showProcessDialog()
    {
        progressDialog.setTitle("HowKteam");
        progressDialog.setMessage("Dang xu ly...");
        progressDialog.show();
    }
}