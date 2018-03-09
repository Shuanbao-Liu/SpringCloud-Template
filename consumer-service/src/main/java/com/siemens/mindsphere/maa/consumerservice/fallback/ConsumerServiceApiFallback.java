package com.siemens.mindsphere.maa.consumerservice.fallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by wsdgc4 on 3/7/2018.
 */
@Component
public class ConsumerServiceApiFallback {

    @Autowired
    public String helloUser() {
        return "Hello from the ConsumerServiceProviderApiFallback.";
    }

}
