package com.example.shoeson.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private String id;
    private boolean profile;
    private String name;
    private String linkImg;
    private String phoneNumber;
    private String address;
    private int money;
    private String pin;
    private ArrayList<ShoesCart> listShoesCarts;
    private ArrayList<String> listIdOrder;

    public User() {
    }

    public User(String id,boolean profile) {
        this.id = id;
        this.profile=profile;
    }

    public User(String id, boolean profile, String name, String linkImg, String phoneNumber, String address, int money,String pin, ArrayList<ShoesCart> listShoesCarts) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.linkImg = linkImg;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.money = money;
        this.pin=pin;
        this.listShoesCarts = listShoesCarts;
    }

    public User(String id, boolean profile, String name, String linkImg, String phoneNumber, String address, int money,String pin, ArrayList<ShoesCart> listShoesCarts,ArrayList<String> listIdOrder) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.linkImg = linkImg;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.money = money;
        this.pin=pin;
        this.listShoesCarts = listShoesCarts;
        this.listIdOrder=listIdOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isProfile() {
        return profile;
    }

    public void setProfile(boolean profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<ShoesCart> getListShoesCarts() {
        return listShoesCarts;
    }

    public void setListShoesCarts(ArrayList<ShoesCart> listShoesCarts) {
        this.listShoesCarts = listShoesCarts;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public ArrayList<String> getListIdOrder() {
        return listIdOrder;
    }

    public void setListIdOrder(ArrayList<String> listIdOrder) {
        this.listIdOrder = listIdOrder;
    }

    public void setSize(int position, ShoesCart shoesCart){
        listShoesCarts.set(position,shoesCart);
    }

}
