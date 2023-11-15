package com.example.shoeson.Model;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private String id;
    private ArrayList<ShoesCart> list;

    public Test() {
    }

    public Test(String id, ArrayList<ShoesCart> list) {
        this.id = id;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ShoesCart> getList() {
        return list;
    }

    public void setList(ArrayList<ShoesCart> list) {
        this.list = list;
    }
}
