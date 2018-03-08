package com.siemens.mindsphere.maa.providerservice.service.Impl;

import com.siemens.mindsphere.maa.providerservice.dao.PostgresRepository;
import com.siemens.mindsphere.maa.providerservice.domain.MongoDBUser;
import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import com.siemens.mindsphere.maa.providerservice.domain.RedisUser;
import com.siemens.mindsphere.maa.providerservice.service.ProviderService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

/**
 * Created by wsdgc4 on 3/7/2018.
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    //private List<ProviderServiceDomainUser> users = new ArrayList<>();

    /*  For PostgresQL*/
    @Autowired
    PostgresRepository postgresRepository;

    /*  For MongoDB*/

    @Autowired
    MongoOperations mongo;

    /*  For Redis*/


    @Override
    public List<ProviderServiceDomainUser> getPostgreUserList(){
        return postgresRepository.findAll();
    }

    @Override
    public void addPostgreUser(ProviderServiceDomainUser user) {
        postgresRepository.save(user);
    }


    @Override
    public List<MongoDBUser> getMongoUserList(){
        return mongo.findAll(MongoDBUser.class);
    }

    @Override
    public void addMongoUser(MongoDBUser user) {
        mongo.save(user);
    }

//    @Override
//    public List<RedisUser> getRedisUserList(){
//
//    }
//
//    @Override
//    public void addRedisUser(RedisUser user) {
//
//    }

}
