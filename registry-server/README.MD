# Service Registry Project Template
This is the Eureka registry service project.  
Service registry service must start firstly so that other micro services can register themselves into this registry server.


## Initialize project
- Generate a SpringBoot gradle project with java from [SPRING INITIALIZR] with  `Eureka Server` dependency, `com.siemens.mindsphere.maa` Group & `registry-server` Artifact.
- Add `@EnableEurekaServer` into `RegistryServerApplication`:
```java
@EnableEurekaServer
@SpringBootApplication
public class RegistryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistryServerApplication.class, args);
	}
}
```
- Add configuration into `applicattion.yml` :
```
spring:
  application:
    name: registry-server
server:
  port: 2222
eureka:
  client:
    hostname: localhost
    register-with-eureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://${eureka.client.hostname}:${server.port}/eureka/

# Log
logging:

  file: registry.log
  level:
    root: INFO
```

## Start Eureka server locally
- Run the [`RegistryServerApplication`]in local Windows IDEA.
- Visit the http://localhost:2222 to check the Eureka server UI.



