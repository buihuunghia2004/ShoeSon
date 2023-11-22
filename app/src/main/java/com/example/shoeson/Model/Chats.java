package com.example.shoeson.Model;

import java.util.ArrayList;

public class Chats {
    private String id;
    private ArrayList<Chat> listChat;
    public Chats() {
    }

    public Chats(String id, ArrayList<Chat> listChat) {
        this.id = id;
        this.listChat = listChat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Chat> getListChat() {
        return listChat;
    }

    public void setListChat(ArrayList<Chat> listChat) {
        this.listChat = listChat;
    }
}
