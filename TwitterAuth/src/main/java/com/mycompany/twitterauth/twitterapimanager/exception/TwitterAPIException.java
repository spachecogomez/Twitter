/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.twitterauth.twitterapimanager.exception;

/**
 *
 * @author sebastian
 */
public class TwitterAPIException extends Exception {

    private String message;

    public TwitterAPIException() {
        super();
    }
    
    public TwitterAPIException(String message) {
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
