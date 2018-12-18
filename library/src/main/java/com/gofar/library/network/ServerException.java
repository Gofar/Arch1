package com.gofar.library.network;

/**
 * @author lcf
 * @date 13/12/2018 下午 3:07
 * @since 1.0
 */
public class ServerException extends Throwable{
    private int code;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
