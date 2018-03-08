package com.siemens.mindsphere.maa.providerservice.dao;

import com.siemens.mindsphere.maa.providerservice.domain.MongoDBUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB repository interfaces for MongoDBUser entity access
 */
public interface MyMongoRepository extends MongoRepository<MongoDBUser, String> {

    MongoDBUser findByUsername(String username);

    MongoDBUser findByPassword(String password);
}
