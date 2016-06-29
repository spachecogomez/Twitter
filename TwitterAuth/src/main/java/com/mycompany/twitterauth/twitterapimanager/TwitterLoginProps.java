/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.twitterauth.twitterapimanager;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author sebastian
 */
@Configuration
@PropertySource("classpath:credentials.properties")
@ConfigurationProperties(prefix = "twitter")
public class TwitterLoginProps {

    private String consumerkey;

    private String consumerSecret;

    private String authSuffix;

    private String propSeparator;

    private String authEndpoint;

    private String authBody;
    
    private String contentType;
    
    private String acceptEncoding;
    
    private String accessTokenKey;
    
    private String tokenTypeKey;
    
    private String trendsEndpoint;
    
    private String tweetsEndpoint;

    public String getConsumerkey() {
        return consumerkey;
    }

    public void setConsumerkey(String consumerkey) {
        this.consumerkey = consumerkey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getAuthSuffix() {
        return authSuffix;
    }

    public void setAuthSuffix(String authSuffix) {
        this.authSuffix = authSuffix;
    }

    public String getPropSeparator() {
        return propSeparator;
    }

    public void setPropSeparator(String propSeparator) {
        this.propSeparator = propSeparator;
    }

    public String getAuthEndpoint() {
        return authEndpoint;
    }

    public void setAuthEndpoint(String authEndpoint) {
        this.authEndpoint = authEndpoint;
    }

    public String getAuthBody() {
        return authBody;
    }

    public void setAuthBody(String authBody) {
        this.authBody = authBody;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAccessTokenKey() {
        return accessTokenKey;
    }

    public void setAccessTokenKey(String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
    }

    /**
     * @return the tokenTypeKey
     */
    public String getTokenTypeKey() {
        return tokenTypeKey;
    }

    /**
     * @param tokenTypeKey the tokenTypeKey to set
     */
    public void setTokenTypeKey(String tokenTypeKey) {
        this.tokenTypeKey = tokenTypeKey;
    }

    public String getTrendsEndpoint() {
        return trendsEndpoint;
    }

    public void setTrendsEndpoint(String trendsEndpoint) {
        this.trendsEndpoint = trendsEndpoint;
    }

    /**
     * @return the tweetsEndpoint
     */
    public String getTweetsEndpoint() {
        return tweetsEndpoint;
    }

    /**
     * @param tweetsEndpoint the tweetsEndpoint to set
     */
    public void setTweetsEndpoint(String tweetsEndpoint) {
        this.tweetsEndpoint = tweetsEndpoint;
    }
    
    
}
