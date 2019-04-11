package com.amarmodi.cameldemo.domain;


import java.io.Serializable;

public class InputPost implements Serializable {

    private int id;
    private String name;

    public InputPost(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public InputPost() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
