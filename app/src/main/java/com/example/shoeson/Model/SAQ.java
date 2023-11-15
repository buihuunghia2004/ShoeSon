package com.example.shoeson.Model;

import java.io.Serializable;
import java.util.HashMap;

public class SAQ implements Serializable {
    private String id;
    HashMap<String,Integer> hashMapSize;

    public SAQ() {
    }

    public SAQ(String id,HashMap<String, Integer> hashMapSize) {
        this.id = id;
        this.hashMapSize = hashMapSize;
    }
    public SAQ(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public HashMap<String, Integer> getHashMapSize() {
        return hashMapSize;
    }

    public void setListSize(HashMap<String, Integer> hashMapSize) {
        this.hashMapSize = hashMapSize;
    }

    public void putHashMapSize(String key,Integer value){
        hashMapSize.put(key,value);
    }
    public int getHashMapSize(String key){
        return hashMapSize.get(key);
    }
}
