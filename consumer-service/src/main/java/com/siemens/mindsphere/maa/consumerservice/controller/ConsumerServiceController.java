package com.siemens.mindsphere.maa.consumerservice.controller;


import com.siemens.mindsphere.maa.consumerservice.fallback.ConsumerServiceApiFallback;
import com.siemens.mindsphere.maa.consumerservice.service.ConsumerService;

import com.siemens.mindsphere.maa.providerservice.api.ProviderServiceApi;
import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wsdgc4 on 3/5/2018.
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerServiceController {

    @Autowired
    private ProviderServiceApi providerServiceApi;

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/hello")
    String hello() {
        return providerServiceApi.hello();
    }

    @Autowired
    ConsumerServiceApiFallback consumerServiceApiFallback;

    @RequestMapping(value = "/consumer", method = RequestMethod.GET)
    public String helloConsumer() {
        return consumerServiceApiFallback.helloUser();
    }

    @GetMapping("/getUsers")
    public List<ProviderServiceDomainUser> getUsers() {
        List<ProviderServiceDomainUser> Users = consumerService.retrieveUsers();
        return Users;
    }

    @PostMapping("/addUser")
    public void consumeAddUser(@RequestBody ProviderServiceDomainUser user) {
        consumerService.addUser(user);
    }
}