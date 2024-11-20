package com.electronicBE.exceptions;

public class BadApiException extends  RuntimeException{

    public BadApiException(String message){
        super( message);
    }

    public BadApiException(){
        super("Bad Api Exception");
    }



}
