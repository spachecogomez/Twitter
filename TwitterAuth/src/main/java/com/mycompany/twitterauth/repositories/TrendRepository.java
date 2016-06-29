/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.twitterauth.repositories;

import com.mycompany.twitterauth.entities.Trend;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author sebastian
 */
public interface TrendRepository extends MongoRepository<Trend, String>{
    
}
