/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.twitterauth;

import com.mycompany.twitterauth.processing.TrendsProcessing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author sebastian
 */
@SpringBootApplication
public class Main implements CommandLineRunner {

    private static Logger LOGGER = LogManager.getLogger(Main.class);
    
    @Autowired
    TrendsProcessing processing ;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... string) throws Exception {
        Thread thread = new Thread(processing);
        thread.start();
    }
}
