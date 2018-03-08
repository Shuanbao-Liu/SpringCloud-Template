package com.siemens.mindsphere.maa.providerservice.service;

import com.siemens.mindsphere.maa.providerservice.domain.MongoDBUser;
import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import com.siemens.mindsphere.maa.providerservice.domain.RedisUser;
import java.util.List;

/**
 * Created by wsdgc4 on 3/7/2018.
 */
public interface ProviderService {

    List<ProviderServiceDomainUser> getPostgreUserList();

    void addPostgreUser(ProviderServiceDomainUser user);

    List<MongoDBUser> getMongoUserList();

    void addMongoUser(MongoDBUser user);

//    List<RedisUser> getRedisUserList();
//
//    void addRedisUser(RedisUser user);

}
