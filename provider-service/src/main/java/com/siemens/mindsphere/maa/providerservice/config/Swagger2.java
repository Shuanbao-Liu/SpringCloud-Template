package com.siemens.mindsphere.maa.providerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by wsdgc4 on 3/1/2018.
 */
@Configuration
@EnableSwagger2
//@Profile({"default", "dev","test"})
public class Swagger2 {

    @Value("${spring.application.name}")

    private String serviceName;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors
                .basePackage("com.siemens.mindsphere.maa.providerservice.controller")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("provider RESTful APIs").description("Service name: " +
                serviceName).version("0.0.1").build();
    }
}
