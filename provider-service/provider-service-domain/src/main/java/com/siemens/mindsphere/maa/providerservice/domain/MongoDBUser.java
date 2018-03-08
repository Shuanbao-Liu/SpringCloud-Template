package com.siemens.mindsphere.maa.providerservice.domain;


import javax.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Demo class for MongoDB domain object
 */
@Document(collection = "mongoDbUser")
public class MongoDBUser {

    @Id // @Id is org.springframework.data.annotation.Id, not @javax.persistence.Id
    @GeneratedValue
    private long id;

    private String username;

    private String password;

    public MongoDBUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

