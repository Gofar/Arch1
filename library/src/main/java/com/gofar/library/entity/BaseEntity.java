package com.gofar.library.entity;

/**
 * @author lcf
 * @date 13/12/2018 下午 5:05
 * @since 1.0
 */
public class BaseEntity<T> {
    public static final int SUCCESS = 0;

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code == SUCCESS;
    }
}
