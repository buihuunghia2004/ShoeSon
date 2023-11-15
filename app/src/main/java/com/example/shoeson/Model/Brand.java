package com.example.shoeson.Model;

import java.io.Serializable;

public class Brand implements Serializable {
    private String id;
    private String name;
    private String linkLogo;

    public Brand() {

    }

    public Brand(String id, String name) {
        this.id = id;
        this.name = name;
        this.linkLogo = linkLogo;
    }
    public Brand(String id, String name,String linkLogo){
        this.id = id;
        this.name = name;
        this.linkLogo = linkLogo;
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

    public String getLinkLogo() {
        return linkLogo;
    }

    public void setLinkLogo(String linkLogo) {
        this.linkLogo = linkLogo;
    }
}
