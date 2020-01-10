package com.example.demosfile.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ServerResponse<T> implements Serializable {
    private String code;
    private String msg;
    private T data;

    private ServerResponse(String code) {
        this.code = code;
    }

    private ServerResponse(String code, T data) {
        this(code, data, null);
    }

    private ServerResponse(String code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    private ServerResponse(String code, String msg) {
        this(code);
        this.msg = msg;
    }
    @JsonIgnore     //json序列化到时候就不会显示这个public方法在json里面
    public String isSuccess() {
        return this.code;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    //成功时返回到数据封装ServerResponse对象
    public static <T> ServerResponse<T> createBySucess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse<T> createBySucessMsg(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> ServerResponse<T> createBySucess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T> ServerResponse<T> createBySucess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data,msg);
    }

    public static <T> ServerResponse<T> createByErrorMessage(String msg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),msg);
    }
    public static <T> ServerResponse<T> createByErrorCodeMessage(String code,String msg){
        return new ServerResponse<T>(code,msg);
    }
}


