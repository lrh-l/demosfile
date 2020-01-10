package com.example.demosfile.utils;

public enum ResponseCode { SUCCESS("0000"),
    ERROR("9999");
    private String desc;

    ResponseCode(String desc) {
        this.desc = desc;
    }
    public String getCode() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
