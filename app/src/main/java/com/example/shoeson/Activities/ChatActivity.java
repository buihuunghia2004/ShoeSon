package com.example.shoeson.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shoeson.Adapter.ChatAdapter;
import com.example.shoeson.Model.Chat;
import com.example.shoeson.Model.Chats;
import com.example.shoeson.Model.Orders;
import com.example.shoeson.Model.User;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    User user;
    ChatAdapter adapter;
    Chats chats;
    ArrayList<Chat> listChat;
    ProgressDialog progressDialog;
    private CollectionReference db=FirebaseFirestore.getInstance().collection("chats");
    private DocumentReference documentReference;
    ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listChat=new ArrayList<>();
        adapter=new ChatAdapter(listChat);
        progressDialog=new ProgressDialog(this);

        Intent intent=getIntent();
        if (intent != null){
            user= (User) intent.getSerializableExtra("user");
            documentReference=db.document(user.getId());
        }

        chats=new Chats(user.getId(),listChat);
        binding.rcv.setAdapter(adapter);
        binding.rcv.setLayoutManager(new LinearLayoutManager(this));

        getData();
    }



    @Override
    protected void onResume() {
        super.onResume();
        binding.edtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    binding.itemImage.setVisibility(View.GONE);
                    binding.itemSend.setVisibility(View.VISIBLE);
                }else{
                    binding.itemImage.setVisibility(View.VISIBLE);
                    binding.itemSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.itemSend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Chat chat=new Chat(0,EpochTime.getEpochTime(),binding.edtMessage.getText().toString());
                // Thêm một phần tử vào mảng
                documentReference.update("listChat", FieldValue.arrayUnion(chat))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Xử lý khi cập nhật thành công
                                binding.edtMessage.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(">>>>", "onFailure: "+e.getMessage());
                                // Xử lý khi cập nhật thất bại
                                Toast.makeText(ChatActivity.this, "fail", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Chats newChats=value.toObject(Chats.class);
                listChat.add(newChats.getListChat().get(newChats.getListChat().size()-1));
                adapter.notifyItemInserted(listChat.size()-1);
            }
        });
    }
    private void getData(){
        progressDialog.show();
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Tài liệu tồn tại trong Firestore
                        chats=document.toObject(Chats.class);

                        listChat.clear();;
                        listChat.addAll(chats.getListChat());

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        // Tài liệu không tồn tại trong Firestore thì tạo mới
                        documentReference.set(chats);
                    }
                } else {
                    // Xử lý khi truy vấn không thành công
                }
            }
        });
    }
}