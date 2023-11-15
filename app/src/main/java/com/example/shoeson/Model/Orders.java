package com.example.shoeson.Model;

import java.util.ArrayList;

public class Orders {
    private String id;
    private String idUser;
    private String nameUser;
    private String address;
    private String phoneNumber;
    private ArrayList<ShoesCart> listShoesCarts;
    private long orderTime;
    private long confirmTime;
    private long deliveryTime;
    private long receiverTime;
    private long status;

    public Orders() {
    }

    public Orders(String id, String idUser, String nameUser, String address, String phoneNumber, ArrayList<ShoesCart> listShoesCarts, long orderTime, long confirmTime, long deliveryTime, long receiverTime, long status) {
        this.id = id;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.listShoesCarts = listShoesCarts;
        this.orderTime = orderTime;
        this.confirmTime = confirmTime;
        this.deliveryTime = deliveryTime;
        this.receiverTime = receiverTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<ShoesCart> getListShoesCarts() {
        return listShoesCarts;
    }

    public void setListShoesCarts(ArrayList<ShoesCart> listShoesCarts) {
        this.listShoesCarts = listShoesCarts;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public long getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(long confirmTime) {
        this.confirmTime = confirmTime;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public long getReceiverTime() {
        return receiverTime;
    }

    public void setReceiverTime(long receiverTime) {
        this.receiverTime = receiverTime;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long statusTime) {
        this.status = statusTime;
    }
}
