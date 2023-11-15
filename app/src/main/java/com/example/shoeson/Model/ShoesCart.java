package com.example.shoeson.Model;

import android.os.Parcel;

public class ShoesCart extends Shoes{
    private int quantity;
    private int size;

    public ShoesCart() {

    }

    public ShoesCart(Shoes shoes){
        super(shoes.getId(),shoes.getName(),shoes.getLinkImg(),shoes.getPriceImport(),shoes.getPriceSell(),shoes.getIdBrand());

    }
    public ShoesCart(String id, String name, String linkImg, int priceImport, int priceSell, String brand, int quantity, int size) {
        super(id, name, linkImg, priceImport, priceSell, brand);
        this.quantity = quantity;
        this.size = size;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setShoes(Shoes shoes){

    }
}
