package com.siemens.mindsphere.maa.providerservice.dao;

import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wsdgc4 on 3/7/2018.
 */
public interface PostgresRepository extends JpaRepository<ProviderServiceDomainUser,Long>{

}
