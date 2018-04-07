package com.example.ricardogarcia.photocloud.model;

/**
 * Created by Ricardo Garcia on 3/18/2018.
 */

public class ServiceResponse {
    private int code;
    private String message;
    private Object object;

    public ServiceResponse(int code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
