package com.amarmodi.cameldemo.domain;

public class Account {

    private int id;
    private String s;
    private int i1;
    private Integer customerId;

    public Account(int i, String s, int i1, Integer customerId) {
        this.id = i;
        this.s = s;
        this.i1 = i1;
        this.customerId = customerId;
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
