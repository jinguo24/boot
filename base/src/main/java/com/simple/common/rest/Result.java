package com.simple.common.rest;

/**
 * @author chenkx
 * @date 2018-01-05.
 */
public class Result {

    public int code;
    public String message;
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;
    public static final int UNLOGIN = 701;

    public Result() {
        this.code = 200;
        this.message = "success";
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
