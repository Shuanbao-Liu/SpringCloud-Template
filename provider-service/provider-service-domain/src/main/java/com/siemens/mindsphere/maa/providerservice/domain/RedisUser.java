package com.siemens.mindsphere.maa.providerservice.domain;

import java.io.Serializable;

/**
 * Created by wsdgc4 on 3/7/2018.
 */
public class RedisUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String username;

    private String password;

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
