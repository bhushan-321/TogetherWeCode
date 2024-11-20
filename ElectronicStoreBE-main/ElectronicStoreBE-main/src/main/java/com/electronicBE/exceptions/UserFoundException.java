package com.electronicBE.exceptions;

public class UserFoundException extends  RuntimeException{

    public UserFoundException(){
        super("User found !!!");
    }

    public UserFoundException(String msg){
        super(msg);
    }
}
