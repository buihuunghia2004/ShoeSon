package com.example.shoeson.FirebaseDatabase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shoeson.Model.Brand;
import com.example.shoeson.Model.Orders;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.Model.ShoesCart;
import com.example.shoeson.Model.Test;
import com.example.shoeson.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFDB {
    private static FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;

    public static class ShoeFDB
    {
        private static CollectionReference crShoe = db.collection("products");
        public static void getListIdShoe(final IGetIdShoesCallBack callback) {
            // Lấy dữ liệu từ Firebase Realtime Database
            crShoe.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<String> listId=new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(">>>>>", "onComplete: r"+document.getId());
                                    String productCode = document.getId();
                                    listId.add(productCode);
                                }
                                callback.onSuccess(listId);
                            } else {
                                callback.onFailure("erorr");
                                Log.w(">>>>", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        public static void getAllShoes(final IGetShoesCallBack callback) {
            // Lấy dữ liệu từ Firebase Realtime Database
            crShoe.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Shoes> list=new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Shoes shoes=document.toObject(Shoes.class);
                                    list.add(shoes);
                                }
                                callback.onSuccess(list);
                            } else {
                                callback.onFailure("erorr");
                                Log.w(">>>>", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        public interface IGetIdShoesCallBack{
            void onSuccess(ArrayList<String> listId);
            void onFailure(String errorMesage);
        }
        public interface IGetShoesCallBack{
            void onSuccess(ArrayList<Shoes> list);
            void onFailure(String errorMesage);
        }
    }
    public static class BrandFDB
    {
        private static CollectionReference crBrand = db.collection("brands");

        public static void addBrand(Brand brand, IAddCallBack callBack) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", brand.getId());
            map.put("name", brand.getName());

            // Add a new document with a generated ID
            crBrand.document(brand.getId())
                    .set(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            callBack.onSuccess(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callBack.onSuccess(false);
                        }
                    });
        }

        public static void getAllBrand(final IGetAllObjectCallBack callback) {
            // Lấy dữ liệu từ Firebase Realtime Database
            crBrand.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Object> list = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Brand brand = document.toObject(Brand.class);
                                    list.add(brand);
                                }
                                callback.onSuccess(list);
                            } else {
                                callback.onFailure("erorr");
                                Log.w(">>>>", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
    }

    public static class UsersFDB
    {
        private static CollectionReference crUser = db.collection("users");

        public static void addUser(User user, IAddCallBack callBack){
            // Add a new document with a generated ID]
            crUser.document(user.getId())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            callBack.onSuccess(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callBack.onSuccess(false);
                        }
                    });
        }
        public static void addCartUser(String id,ArrayList<ShoesCart> list,IAddCallBack callBack){
            crUser.document(id)
                    .update("listShoesCarts",list)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            callBack.onSuccess(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callBack.onSuccess(false);
                        }
                    });

        }
        public static void getUserByUid(String uid, IGetUserByUid callBack){
            // Add a new document with a generated ID]
            crUser.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user=documentSnapshot.toObject(User.class);
                    callBack.onSuccess(user);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callBack.onFailure(e.getMessage());
                }
            });
        }
        public static void updateMoney(String uid,int money,IAddCallBack callBack){
            HashMap<String,Object> map=new HashMap<>();
            map.put("money",money);
            crUser.document(uid).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    callBack.onSuccess(true);
                }
            });
        }
    }
    public static class OrderFDB
    {
        private static CollectionReference crOrder = db.collection("orders");

        public static void addOrder(Orders orders, IAddCallBack callBack){
            // Add a new document with a generated ID]
            DocumentReference documentReference=crOrder.document();
            orders.setId(documentReference.getId());
            documentReference
                    .set(orders)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            callBack.onSuccess(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callBack.onSuccess(false);
                        }
                    });
        }

        public static void getAllShoes(final ShoeFDB.IGetShoesCallBack callback) {
            // Lấy dữ liệu từ Firebase Realtime Database
            crOrder.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        }
                    });
        }
    }

    public interface IGetUserByUid{
        void onSuccess(User user);
        void onFailure(String errorMessage);
    }
    public interface IAddCallBack {
        void onSuccess(boolean b);
    }
    public interface IGetAllObjectCallBack{
        void onSuccess(ArrayList<Object> list);
        void onFailure(String errorMesage);
    }
}
