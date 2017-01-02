package com.tt.frameworkdemo.api;

/**
 * Created by TT on 2016/12/11.
 */
public class ApiException extends RuntimeException {
    public ApiException(String msg){
        super(msg);
    }
}
