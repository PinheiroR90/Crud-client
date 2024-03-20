package com.project.crudclient.services.exceptions;

public class NotFoundClientException extends RuntimeException{
    public NotFoundClientException(String msg){
        super(msg);
    }
}
