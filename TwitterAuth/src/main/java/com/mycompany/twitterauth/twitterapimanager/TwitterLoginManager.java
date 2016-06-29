/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.twitterauth.twitterapimanager;

import com.mycompany.twitterauth.entities.Constants;
import com.mycompany.twitterauth.twitterapimanager.exception.TwitterAPIException;
import com.mycompany.twitterauth.twitterapimanager.exception.TwitterNotAuthenticatedException;
import java.util.Base64;
import java.util.LinkedHashMap;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author sebastian
 */
@Component
@EnableConfigurationProperties(TwitterLoginProps.class)
public class TwitterLoginManager {

    private Logger LOGGER = LogManager.getLogger(TwitterLoginManager.class);

    @Autowired
    private TwitterLoginProps twitterLoginProps;

    private RestTemplate rt = new RestTemplate();

    private String accessToken;

    @PostConstruct
    public void init() {
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    private String getAuthToken() {
        StringBuilder authToken = new StringBuilder();
        authToken.append(twitterLoginProps.getConsumerkey());
        authToken.append(twitterLoginProps.getPropSeparator());
        authToken.append(twitterLoginProps.getConsumerSecret());
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(authToken.toString().getBytes());
        StringBuilder req = new StringBuilder(twitterLoginProps.getAuthSuffix());
        req.append(encodedString);
        LOGGER.debug(String.format("Authorization Token : %s", req.toString()));
        return req.toString();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.AUTHORIZATION_HEADER, getAuthToken());
        headers.add(Constants.ACCEPT_ENCODING_HEADER, twitterLoginProps.getAcceptEncoding());
        headers.add(Constants.CONTENT_TYPE_HEADER, twitterLoginProps.getContentType());
        return headers;
    }

    public void getAccessToken() {
        HttpEntity<String> requestEntity = new HttpEntity<String>(twitterLoginProps.getAuthBody(), getHeaders());
        LinkedHashMap<String, String> re = rt.postForObject(twitterLoginProps.getAuthEndpoint(), requestEntity, LinkedHashMap.class);
        StringBuilder sb = new StringBuilder();
        sb.append(re.get(twitterLoginProps.getTokenTypeKey()));
        sb.append(" ");
        sb.append(re.get(twitterLoginProps.getAccessTokenKey()));
        accessToken = sb.toString();
        LOGGER.debug(String.format("Access Token : %s", sb.toString()));
    }
    
    public String getToken() throws TwitterAPIException{
        if(accessToken == null){
            throw new TwitterNotAuthenticatedException();
        }else{
            return accessToken;
        }
    }

}
