package com.siemens.mindsphere.maa.providerservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class ProviderServiceDomainUser {

    @Id
    @GeneratedValue
    private Long id;

//    @NotNull
//    @Size(min = 2, max = 8)
    private String username;

//    @NotNull
    //@Pattern(regexp="/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message="include 6~10 numbers and characters")
//    @Pattern(regexp = "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])).{6,10}$",message="include 6~10 numbers and characters")
//    @Size(min = 6, max = 10)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "ProviderServiceDomainUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}