package com.siemens.mindsphere.maa.providerservice.service.Impl;

import com.siemens.mindsphere.maa.providerservice.dao.PostgresRepository;
import com.siemens.mindsphere.maa.providerservice.domain.MongoDBUser;
import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import com.siemens.mindsphere.maa.providerservice.domain.RedisUser;
import com.siemens.mindsphere.maa.providerservice.service.ProviderService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by wsdgc4 on 3/7/2018.
 */
@Service
@Repository
public class ProviderServiceImpl implements ProviderService {

    //private List<ProviderServiceDomainUser> users = new ArrayList<>();

    /*  For PostgresQL*/
    @Autowired
    PostgresRepository postgresRepository;

    /*  For MongoDB*/
    @Autowired
    MongoTemplate mongo;

    /*  For Redis*/
    @Autowired
    RedisTemplate<Object,Object> redis;

    //@Resource(name = "redis")
    //ValueOperations<Object,Object> valueOperations;

    /*  For PostgresQL*/
    @Override
    public List<ProviderServiceDomainUser> getPgUserList(){
        return postgresRepository.findAll();
    }

    @Override
    public void addPgUser(ProviderServiceDomainUser user) {
        postgresRepository.save(user);
    }

    /*  For MongoDB*/
    @Override
    public List<MongoDBUser> getMgUserList(){
        return mongo.findAll(MongoDBUser.class);
    }

    @Override
    public void addMgUser(MongoDBUser user) {
        mongo.save(user);
    }

    /*  For Redis*/
    @Override
    public RedisUser getRdUserList(){
        //return (RedisUser) valueOperations.get("1");
        return (RedisUser) redis.opsForValue().get("1");
    }

    @Override
    public void addRdUser(RedisUser user) {
        //valueOperations.set(user.getId(),user);
        redis.opsForValue().set("1", user);
    }
}
