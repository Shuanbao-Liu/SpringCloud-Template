package com.siemens.mindsphere.maa.providerservice.api;



import com.siemens.mindsphere.maa.providerservice.domain.ProviderServiceDomainUser;
import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Service interfaces provided by service-provider for consumer app call
 */
@FeignClient(name = "provider-service")
public interface ProviderServiceApi {

    @GetMapping("/users/hello")
    String hello();

    @GetMapping("/users/getUser")
    List<ProviderServiceDomainUser> getUser();

    @PostMapping("/users/addUser")
    void  addUser(@RequestBody ProviderServiceDomainUser user);

}
