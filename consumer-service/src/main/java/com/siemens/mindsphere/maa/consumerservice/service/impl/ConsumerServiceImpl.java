package com.siemens.mindsphere.maa.consumerservice.service.impl;


import com.siemens.mindsphere.maa.consumerservice.fallback.ConsumerServiceApiFallback;
import com.siemens.mindsphere.maa.consumerservice.service.ConsumerService;
import com.siemens.mindsphere.maa.providerservice.api.ProviderServiceApi;
import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wsdgc4 on 3/7/2018.
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ProviderServiceApi providerServiceApi;

    @Autowired
    private ConsumerServiceApiFallback consumerServiceApiFallback;

    @Override
    public String hello() {
        return providerServiceApi.hello();
    }

    @Override
    public List<ProviderServiceDomainUser> retrieveUsers() {
        return providerServiceApi.getUser();
    }

    @Override
    public void addUser(ProviderServiceDomainUser newUser) {
        providerServiceApi.addUser(newUser);
    }


}
