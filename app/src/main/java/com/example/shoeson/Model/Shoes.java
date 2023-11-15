package com.example.shoeson.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Shoes implements Serializable {
    private String id;
    private String name;
    private String linkImg;
    private int priceImport;
    private int priceSell;
    private String brand;
    private SAQ saq;
    public Shoes() {
    }

    public Shoes(String id, String name, String linkImg, int priceImport, int priceSell, String brand) {
        this.id = id;
        this.name = name;
        this.linkImg = linkImg;
        this.priceImport = priceImport;
        this.priceSell = priceSell;
        this.brand = brand;
    }

    public Shoes(String id, String name, String linkImg, int priceImport, int priceSell, String brand, SAQ saq) {
        this.id = id;
        this.name = name;
        this.linkImg = linkImg;
        this.priceImport = priceImport;
        this.priceSell = priceSell;
        this.brand = brand;
        this.saq = saq;
    }

    protected Shoes(Parcel in) {
        id = in.readString();
        name = in.readString();
        linkImg = in.readString();
        priceImport = in.readInt();
        priceSell = in.readInt();
        brand = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getPriceImport() {
        return priceImport;
    }

    public void setPriceImport(int priceImport) {
        this.priceImport = priceImport;
    }

    public int getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(int priceSell) {
        this.priceSell = priceSell;
    }

    public String getIdBrand() {
        return brand;
    }

    public void setIdBrand(String idBrand) {
        this.brand = idBrand;
    }

    public SAQ getSaq() {
        return saq;
    }

    public void setSaq(SAQ saq) {
        this.saq = saq;
    }

}
