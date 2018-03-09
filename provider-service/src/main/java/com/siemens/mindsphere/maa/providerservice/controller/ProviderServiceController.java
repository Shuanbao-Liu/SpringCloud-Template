package com.siemens.mindsphere.maa.providerservice.controller;


import com.siemens.mindsphere.maa.providerservice.domain.MongoDBUser;
import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import com.siemens.mindsphere.maa.providerservice.domain.RedisUser;
import com.siemens.mindsphere.maa.providerservice.service.ProviderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wsdgc4 on 3/5/2018.
 */
@RestController
public class ProviderServiceController {

    @Autowired
    private ProviderService providerService;

    @GetMapping("/users/hello")
    public String hello() {
        return "Hello from provider";
    }

    // PostgreSQL
    @ApiOperation(value = "Get user list", notes = "Get all users list")
    @GetMapping("/postgre/users/getUser")
    public List<ProviderServiceDomainUser> getPgUser() {
        return providerService.getPgUserList();
    }

    @ApiOperation(value = "Add user", notes = "Create a new user by POST")
    @ApiImplicitParam(name = "user", value = "User object", required = true, dataType = "ProviderServiceDomainUser")
    @PostMapping("/postgre/users/addUser")
    public void addPgUser(@Valid @RequestBody ProviderServiceDomainUser user) {
        providerService.addPgUser(user);
    }



    //  MongoDB
    @ApiOperation(value = "Get user list", notes = "Get all users list")
    @GetMapping("/mongo/users/getUser")
    public List<MongoDBUser> getMgUser() {
        return providerService.getMgUserList();
    }

    @ApiOperation(value = "Add user", notes = "Create a new user by POST")
    @ApiImplicitParam(name = "user", value = "User object", required = true, dataType = "ProviderServiceDomainUser")
    @PostMapping("/mongo/users/addUser")
    public void addMgUser(@RequestBody MongoDBUser user) {
        providerService.addMgUser(user);
    }

    // Redis
    @ApiOperation(value = "Get user list", notes = "Get all users list")
    @GetMapping("/redis/users/getUser")
    public RedisUser getRdUser() {
        return providerService.getRdUserList();
    }

    @ApiOperation(value = "Add user", notes = "Create a new user by POST")
    @ApiImplicitParam(name = "user", value = "User object", required = true, dataType = "ProviderServiceDomainUser")
    @PostMapping("/redis/users/addUser")
    public void addRdUser(@RequestBody RedisUser user) {
        providerService.addRdUser(user);
    }
}
