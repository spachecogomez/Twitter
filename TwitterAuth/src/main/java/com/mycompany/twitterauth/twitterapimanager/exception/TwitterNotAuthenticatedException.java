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
public class TwitterNotAuthenticatedException extends TwitterAPIException{
    
    private String exceptionInfo = "You're not loggedin.";
    
    public TwitterNotAuthenticatedException(){
        super();
        setMessage(exceptionInfo);
    }
    
}
