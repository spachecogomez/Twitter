/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.twitterauth.processing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mycompany.twitterauth.entities.Constants;
import com.mycompany.twitterauth.entities.Trend;
import com.mycompany.twitterauth.entities.Tweet;
import com.mycompany.twitterauth.repositories.TrendRepository;
import com.mycompany.twitterauth.twitterapimanager.TwitterLoginManager;
import com.mycompany.twitterauth.twitterapimanager.TwitterLoginProps;
import com.mycompany.twitterauth.twitterapimanager.exception.TwitterAPIException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author sebastian
 */
@Service
public class TrendsProcessing implements Runnable {

    @Autowired
    private TrendRepository repository;

    @Autowired
    TwitterLoginManager twitterLoginManager;

    @Autowired
    private TwitterLoginProps twitterLoginProps;

    private RestTemplate rt;

    private Logger LOGGER = LogManager.getLogger(TrendRepository.class);

    @PostConstruct
    public void init() {
        initRestTemplate();
        twitterLoginManager.getAccessToken();
    }
    
    private void initRestTemplate(){
        rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void run() {
        try {
            LOGGER.info("Trends Gathering started...");
            getTrendingTopicsByPlace();
            LOGGER.info("Trends Gathering finished...");
        } catch (Exception e) {
            LOGGER.error("Exception thrown getting the trends", e);
        }
    }

    private void getTrendingTopicsByPlace() throws TwitterAPIException, IOException {
        initRestTemplate();
        /*TODO:Set dynamically the location*/
        String trendsEndpoint = String.format(twitterLoginProps.getTrendsEndpoint(), 368148);
        HttpEntity<String> requestEntity = new HttpEntity<String>(getTrendsHeaders());
        ResponseEntity response = rt.exchange(trendsEndpoint,
                HttpMethod.GET, requestEntity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode actualObj = (ArrayNode) mapper.readTree(response.getBody().toString()).get(Constants.TRENDS_INDEX).get(Constants.TRENDS);
        Iterator<JsonNode> elementsIterator = actualObj.elements();
        JsonNode currNode = null;
        Trend trend;
        while(elementsIterator.hasNext()){
            currNode = elementsIterator.next();
            trend = new Trend();
            trend.setHashtag(currNode.get("name").asText());
            trend.setUrl(currNode.get("query").asText());
            trend.setPlatform(Constants.TWITTER_PLATFORM);
            trend.setTweets(new ArrayList<Tweet>());
            trend.setDate(new Date());
            getTweetsByTrend(trend);
            
            repository.save(trend);
            
            try{
                Thread.sleep(15*60000);
            }catch(Exception e){
                LOGGER.error("Exception waiting to hit until the next time frame");
            }
            LOGGER.debug("Trend ->"+currNode.get("name")+" "+currNode.get("query"));
        }
    }
    
    private void getTweetsByTrend(Trend trend) throws TwitterAPIException, IOException{
        initRestTemplate();
        LOGGER.debug(String.format("Gathering twwets with the trending topic '%s' and url '%s'",trend.getHashtag(),trend.getUrl()));
        String tweetsEndpoint = String.format(twitterLoginProps.getTweetsEndpoint(), trend.getUrl());
        LOGGER.debug(String.format("Getting tweets with the url [%s]", tweetsEndpoint));
        HttpEntity<String> requestEntity = new HttpEntity<String>(getTrendsHeaders());
        ResponseEntity responseTweets = rt.exchange(tweetsEndpoint,
                HttpMethod.GET, requestEntity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode responseArr = (ArrayNode) mapper.readTree(responseTweets.getBody().toString()).get(Constants.TWEETS_TAG);
        LOGGER.debug(responseTweets.getBody().toString());
        Iterator<JsonNode> elementsIterator = responseArr.elements();
        JsonNode currNode = null;
        
        Tweet tweet = null;
        while(elementsIterator.hasNext()){
            currNode = elementsIterator.next();
            tweet = new Tweet();
            tweet.setTweetText(currNode.get("text").asText());
            tweet.setLocation(currNode.get("user").get("location").asText());
            tweet.setSource(currNode.get("source").asText());
            tweet.setUser(currNode.get("user").get("name").asText());
            trend.getTweets().add(tweet);
        }
    }

    private HttpHeaders getTrendsHeaders() throws TwitterAPIException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.AUTHORIZATION_HEADER, twitterLoginManager.getToken());
        return headers;
    }

}
