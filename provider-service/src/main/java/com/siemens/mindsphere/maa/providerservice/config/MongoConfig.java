package com.siemens.mindsphere.maa.providerservice.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by wsdgc4 on 3/8/2018.
 */

@Configuration
@EnableMongoRepositories
public class MongoConfig {

    @Bean
    public MongoClient client() throws UnknownHostException{
        MongoClient client = new MongoClient(new ServerAddress("127.0.0.1",27017));
        return client;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception{
        String database = new MongoClientURI("mongodb://localhost/test").getDatabase();
        return new SimpleMongoDbFactory(client(),database);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) throws UnknownHostException{
        return new MongoTemplate(mongoDbFactory);
    }
}
