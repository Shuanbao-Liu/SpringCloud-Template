package com.siemens.mindsphere.maa.consumerservice.service;


import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import java.util.List;

/**
 * Created by wsdgc4 on 3/7/2018.
 */
public interface ConsumerService {

    String hello();

    List<ProviderServiceDomainUser> retrieveUsers();

    void addUser(ProviderServiceDomainUser newUser);
}
