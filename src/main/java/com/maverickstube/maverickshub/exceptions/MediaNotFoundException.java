package com.maverickstube.maverickshub.exceptions;

public class MediaNotFoundException extends RuntimeException{
    public MediaNotFoundException(String message){
        super(message);
    }
}
