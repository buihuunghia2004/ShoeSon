package com.example.shoeson.Model;

public class Chat {
    private int type;
    private long time;
    private String content;

    public Chat() {
    }

    public Chat(int type, long time, String content) {
        this.type = type;
        this.time = time;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
