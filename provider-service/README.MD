# Service Provider Project Template
This is a micro service template project which provides service interfaces for consumer.
Using `provider-service` for service name in this template. For your project you should replace `provider-service` with your real service name.

<!-- TOC depthFrom:1 depthTo:6 withLinks:1 updateOnSave:1 orderedList:0 -->

[TOC]

## Root project
- Generate a SpringBoot gradle project with java from [SPRING INITIALIZR] with `Web`, `Eureka Discovery`, `Actuator`, `DevTools` dependencies, `com.siemens.mindsphere.maa` Group & `provider-service` Artifact.

### settings.gradle
- Create `settings.gradle` file in `provider-service` directory
- Add subproject definitions in root project `settings.gradle`:
```include 'provider-service-api'
   include 'provider-service-domain'
   
   rootProject.name = 'provider-service'
```

### build.gradle
- Add `defaultTasks` to build all projects for different task. The `defaultTasks` is used for gradle build without task parameter.
```gradle
	defaultTasks 'clean', ':provider-service-domain:jar', ':provider-service-api:jar', 'build'
```
- Add common configurations for all projects :
```buildscript {
   	ext {
   		springBootVersion = '1.5.10.RELEASE'
   		springCloudVersion = 'Dalston.SR4'
   		localNexusServer = 'http://146.122.219.146:8081/repository/maven-public/'
   		localNexusReleases = 'http://146.122.219.146:8081/repository/maven-releases'
   		localNexusSnapshots = 'http://146.122.219.146:8081/repository/maven-snapshots'
   	}
   	repositories {
   		maven { url localNexusServer }
   	}
   	dependencies {
   		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
   	}
   }
   
   allprojects {
   	// Declare the all subprojects shared plugin here.
   	apply plugin: 'java'
   	apply plugin: 'maven'
   	apply plugin: 'idea'
   	apply plugin: 'org.springframework.boot'
   
   	// All projects shared group,version
   	// Using the default project.name of each subproject for artifact id.
   
   	group = 'com.siemens.mindsphere.maa'
   	version = '0.0.1-SNAPSHOT'
   	sourceCompatibility = 1.8
   
   	// Use local Nexus Maven repository for external dependency jar and internal jar
   	repositories {
   		maven { url localNexusServer }
   	}
   
   	uploadArchives {
   		repositories {
   			mavenDeployer {
   				// Internal releases repo
   				repository (url: localNexusReleases) {
   					authentication (userName: "admin", password: "admin123")
   				}
   				// Internal snapshots repo
   				snapshotRepository (url: localNexusSnapshots) {
   					authentication(userName: "admin", password: "admin123")
   				}
   			}
   
   		}
   	}
   }
```
The `group`, `version` and default `projec.name` compose the artifact Maven coordination which is shared accross all subprojects.
The `group`, `version` & `archivesBaseName` definition in subproject `build.gradle` can override the shared Maven coordincation .
- Add common configurations for all subprojects :
```gradle
subprojects{
    dependencies {
      // Declare subprojects common dependencies here
    }
}
```

## Subprojects
### provider-service-domain
This subproject defines the share domain object models which is used accross service provider and consumer applications.
- Create directories `service-provider-domain/src/main/java` and `service-provider-domain/src/main/resources` under root project.
- Create a new `build.gradle` file for `domain` subproject build:
```gradle
    dependencies {
        //Declare domain subproject dependencies here
    }
```
- Create shared domain object into subproject `provider-service-domain`.


#### build.gradle

```
	dependencies {
      compile project(':provider-service-domain')
  }
```

#### Enable Eureka client
- Make sure below dependency in `build.gradle`:
```gradle
	compile('org.springframework.cloud:spring-cloud-starter-eureka')
```

- Add `@EnableDiscoveryClient` annotation into `ProviderServiceApplication`.
```java
//@EnableDiscoveryClient
//@SpringBootApplication
//@EnableCircuitBreaker
@SpringCloudApplication //Replace above 3 annotations
@EnableSwagger2
public class ProviderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderServiceApplication.class, args);
	}
}
```
- Add configuration into `application.yml` to confiure listening port and eureka configuration.
```
spring:
  application:
    name: provider-service
server:
  port: 1999
eureka:
  server:
    hostname: localhost
    port: 2222
  client:
    serviceUrl:
      defaultZone: http://${eureka.server.hostname}:${eureka.server.port}/eureka/
security:
  basic:
    enabled: false
management:
  security:
    enabled: false
#spring:
#  zipkin:
#    base-url: http: //localhost:9411

# Log
logging:
  file: provider.log
  level:
    root: INFO
    com.siemens.com:  INFO
```
- Create controlller class [ProviderServiceController] Then add a `/user/hello` endpoint implementation.
```java
@RestController
@RequestMapping("/users")

public class ProviderServiceController {

    @Autowired
    private ProviderService providerService;
    //private List<ProviderServiceDomainUser> users;

    @GetMapping("/hello")
    public String hello(){
        //logger.info("Receive hello request")
        return "Hello from provider update";
    }
}
```
Refer to [ProviderServiceController]for more controller APIs implementation for PUT/POST/PUT/PATCH/DELETE operations implementation.

#### Enable Actuator
- Add below dependency in `build.gradle`
```gradle
	compile('org.springframework.boot:spring-boot-starter-actuator')
```
- Add below configuration in `application.properties` so that `/info` can response user info with service name.
```
	info:
    microServiceName: ${spring.application.name}
```
- By default Actuator endpoints are protected by security with reporting `401 Unauthorized`. Add below configuration to disable the secrurity control: (Refer to [Actuator Security](https://docs.spring.io/spring-boot/docs/1.5.8.RELEASE/reference/htmlsingle/#boot-features-security-actuator))
```
management:
  security:
    enabled: false
```
- Refer to [Endpoints](https://docs.spring.io/spring-boot/docs/1.5.8.RELEASE/reference/htmlsingle/#production-ready-endpoints) for Actuaotr endpoints list.
- Refer to http://blog.didispace.com/spring-boot-actuator-1/ for more demos.

#### Enable Devtools
Refer to [SpringBoot Developer tools](https://docs.spring.io/spring-boot/docs/1.5.8.RELEASE/reference/htmlsingle/#using-boot-devtools)
Devtools helps with hot deploy for development.Check below dependency in `build.gradle`:
```gradle
runtime('org.springframework.boot:spring-boot-devtools')
```
**TODO: Refer https://github.com/spring-gradle-plugins/propdeps-plugin for optional dependency of gradle to exclude the devtools out of the production environment.**

### provider-aservice-pi
This subproject defines the Feign service interfaces which are called by conumser application directly.
#### Declare FeignClient Interface
- Create directories `provider-service-api/src/main/java` and `provider-service-api/src/main/resources` under root project.
- Create a new `build.gradle` file for `api` subproject build. SpringCloud dependencies should be decared for `FeignClient`.

```gradle
    dependencies {
        //declared for FeignClient
        compile('org.springframework.cloud:spring-cloud-starter-feign')
        compile project(":provider-service-domain")
    }
    
    dependencyManagement {
        imports {
            mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
        }
    }
```
- Create `FeignClient` interface [ProdiverServiceApi] which exposes the `provider-service-service` contorllers [ProviderServiceController] interfaces.
**Notice: The `@PathVariable("pathVar")`, `@RequestParam("paramName")` and `@RequestHeader("headerName")` must specified the `value` string to explicitly bind the name from HTTP in FeignClient interface declaration otherwise `IllegalStateException` is thrown due to empty value.**

```java
@FeignClient(name = "provider-service")
public interface ProviderServiceApi {

    @GetMapping("/users/hello")
    String hello();

    @GetMapping("/users/")
    List<ProviderServiceDomainUser> getAllUsers();

    @PostMapping("/users/")
    ProviderServiceDomainUser addNewUser(@RequestBody ProviderServiceDomainUser user);

    @PostMapping("/users/form")
    ProviderServiceDomainUser addNewUserByForm(@ModelAttribute ProviderServiceDomainUser user);

    @PutMapping("/users/{id}")
    ProviderServiceDomainUser updateSingleUser(@PathVariable("id") Long id,
                                                @ModelAttribute ProviderServiceDomainUser user);

    @DeleteMapping("/users/{id}")
    void deleteSingleUser(@PathVariable("id") Long id);
}
```

## Build projects
### Build default tasks
Run `gradlew.bat` without task parameter will run the `defaultTasks` in root project one by one.
```gradle
defaultTasks 'clean', ':provider-service-domain:jar', ':provider-service-api:jar', 'build'
```

###  domain subproject only
Run `gradlew.bat :provider-service-domain:jar`to build `provider-service-domain` only with its depdendency projects.

### api subproject only
Run `gradlew.bat :provider-service-api:jar`to build `provider-service-api` only with its depdendency projects.


## Upload jars to Internal Nexus Maven Server
**The `provider-service-domain` and `provider-service-api` jars need to be deployed into local Nexus server for share because both service provider & service consumer share the same domain objects and service consumer need refer to the service provider interfaces.**
### All subprojects
Run `gradlew.bat clean upload` to build all subprojects into jar and upload into Nexus Maven server.
In normal case the root project `provider-service` needn't to deploy into Nexus Maven server because there is no share requirement for this subproject.
### domain project only
Run `gradlew.bat clean provider-service-domain:upload` to build only `provider-service-domain` into jar and upload into Nexus Maven server.
### api project only
Run `gradlew.bat clean provider-service-api:upload` to build only `provider-service-api` into jar and upload into Nexus Maven server.

### Log configuration
Refer to http://blog.didispace.com/springbootlog/
- Enable log file output:
```
logging.file=provider.log
```
**Note:** There is also a `logging.path` property to specify a path for a logging file. If you use it, Spring Boot creates a `spring.log` file in the specified path. However, you cannot specify both the `logging.file` and `logging.path` properties together. If done, Spring Boot will ignore both.
- Control the log level: `logging.level.*.LEVEL` format.
	- `*` specify the package name or Logger name.
	- `LEVEL` values: TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF
```
logging.level.root=WARN
logging.level.com.siemens=INFO
```
# Need to do in the future.
### Log trace with Spring Cloud Sleuth (Mandatory)
#### Enable Sleuth log trace
- - Add `sleuth` dependency into `build.gradle` both for `service-provider` and `service-consumer` `-service` subproject.
```gradle
compile('org.springframework.cloud:spring-cloud-starter-sleuth')
```
- Restart `service-provider` and `service-consumer` applications and send request by `service-consumer` application like `POST` URL `http://localhost:1888/consume/userAdd` to add a new user, the log output would automatically added with additional Sleuth TraceId, SpanId and Sampling Flag like below:
  `d957c4826dd272ec` is the TraceId which is across both  `service-provider` and `service-consumer` application.
	- `service-consumer` application log
	```
	2017-11-14 16:05:59.233  INFO [service-consumer,d957c4826dd272ec,d957c4826dd272ec,false] 4576 --- [nio-1888-exec-6] c.e.s.c.ServiceConsumerController        : Ready to add user: ServiceProviderDomainUser{id=0, username='string1', password='123456'}
	```
	- `service-provider` applicaiton log
	```
	2017-11-14 16:05:59.238  INFO [service-provider,d957c4826dd272ec,b53937a6f3a264c1,false] 18252 --- [nio-1999-exec-5] c.e.s.c.ServiceProviderController        : Received add user request: ServiceProviderDomainUser{id=0, username='string1', password='123456'}
2017-11-14 16:05:59.239  INFO [service-provider,d957c4826dd272ec,b53937a6f3a264c1,false] 18252 --- [nio-1999-exec-5] c.e.s.service.impl.ProviderServiceImpl   : Create a new user: ServiceProviderDomainUser{id=0, username='string1', password='123456'}
	```

#### Integration with Zipkin for Trace Performance (Optional)
##### Setup Zipkin Server
- Create `zipkin-service` SpringBoot gradle project with java from [SPRING INITIALIZR]with `Zipkin UI` dependency.
- Add below dependency into `build.gradle`
```gradle
dependencies {
	compile('io.zipkin.java:zipkin-server')
	runtime('io.zipkin.java:zipkin-autoconfigure-ui')
}
```
- Configure the applicaiton port and name in `application.properties`. Note the default known server port for Zipkin is 9411.
```
spring.application.name=zipkin-service
server.port=9411
```
- Start the `zipkin-service` application and the Zipkin UI can be accessed by http://localhost:9411 .

##### Enable Zipkin Service for Application
- Add `spring-cloud-sleuth-zipkin` dependency into both `provider-service` & `consumer-service` applications `build.gradle`.
```gradle
compile('org.springframework.cloud:spring-cloud-sleuth-zipkin')
```
- Add `spring.zipkin.base-url` into both `provider-service` & `consumer-service` applications `application.properties`.
```
spring.zipkin.base-url=http://localhost:9411
```
For the developement purspose, the log sample percentage is set to 100% with below property:
```
spring.sleuth.sampler.percentage=1
```
- Restart the `provider-service` & `consumer-service` applications and access some conumer URLs to generated some log. Make sure the
- Check the Zipkin server for the log trace.


## Swagger2 need to Do 
### Swagger2 Doc for RESTful API (Mandatory)
Refer to http://blog.didispace.com/springbootswagger2/
- Add dependencies into [`build.gradle`](service-provider-service/build.gradle)

```gradle
compile ('io.springfox:springfox-swagger2:2.7.0')
compile ('io.springfox:springfox-swagger-ui:2.7.0')
```

- Create [`Swagger2`] in the same package where `ProviderServiceApplication` locates.
**Notices**
 1. **The `serviceName` refer to the `spring.application.name` property configurated in the `application.properties` file.**
 2. **Enable Swagger2 doc in non-production env only by adding `@Profile` annotation.**

```java
@Configuration
@EnableSwagger2
@Profile({"default", "dev", "test"})
public class Swagger2 {

    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors
                .basePackage("com.example.serviceprovider.controller")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("service-provider RESTful APIs").description("Service name: " +
                serviceName).version("1.0.0").build();
    }
}
```
- Add Swagger2 doc annation on controller class method:
  - `@ApiIgnore` to ignore the controller which needn't Swagger2 doc.
    ```java
    @ApiIgnore
	@GetMapping("/ignoreAPi")
	public String ignoredApi(){
		return "This API is ignored by Swagger2.";
	}
    ```
  - `@ApiOperation` to add API description and `@ApiImplicitParam` to add single parameter description. Refer to the Java Doc for more errorDetail about the paramenters for `@ApiOperation` & `@ApiImplicitParam` annotations. `@ApiResponses` defines the HTTP response code and message.
  ```java
    @ApiOperation(value = "Add user", notes = "Create a new user by POST")
    @ApiImplicitParam(name = "user", value = "User object", required = true, dataType = "ServiceProviderUser")
    @ApiResponses({@ApiResponse(code = 201, message = "Created")})
    @PostMapping("/")
    public ResponseEntity<ServiceProviderUser> addUserByPostJson(@RequestBody ServiceProviderUser user) {
        logger.info("Received add user request: " + user);
        ServiceProviderUser savedUser = userRepository.save(user);

        return new ResponseEntity<ServiceProviderUser>(savedUser, HttpStatus.CREATED);
    }
  ```
  - Refer to [ServiceProviderController](service-provider-service/src/main/java/com/example/serviceprovider/controller/ServiceProviderController.java) for more Swagger2 Doc demos.

#### Validate parameter with JSR 303
`JSR 303 Bean Validation` for input Java Bean validation.
Refer to [Bean Validation specification](http://beanvalidation.org/1.1/spec/)
Refert to [Bean Validation API](http://docs.jboss.org/hibernate/beanvalidation/spec/1.1/api/)
- Add validation annotation into domain object fields. e.g. `NotBlank` to validate empty value and `@Length` to validate string length in [ServiceProviderUser](service-provider-service/src/main/java/com/example/serviceprovider/dao/entity/ServiceProviderUser.java).
```java
	@NotBlank
    @Length(min = 1, max = 10)
    private String username;

	@NotBlank
    @Length(min = 6)
    private String password;
```
- Add `@Valid` annotation on the controller method parameter following with `Errors` parameter to collecting the validation error results.
When there is errors after validation, throw the errors with customized Exception to Exception handler.
The **HTTP status for return** can be specified here in Exception constructor.
```java
public ResponseEntity<ServiceProviderUser> addUserByPostJson(@Valid @RequestBody ServiceProviderUser user,
                                                                 Errors errors) throws Exception {
        logger.info("Received add user request: " + user);

        if(errors.hasErrors()){
            throw new InputValidationException("Invalid input for user", errors, HttpStatus.BAD_REQUEST);
}
```
- Create a new Exception class like [InputValidationException](service-provider-service/src/main/java/com/example/serviceprovider/exception/InputValidationException.java) to collect validation error so that return error result to caller.
```java
public class InputValidationException extends Exception{

    private HttpStatus httpStatus;
    private List<ObjectError> allErrors = new ArrayList<>();

    public InputValidationException(String message, Errors errors, HttpStatus httpStatus) {
        super(message);
        allErrors = errors.getAllErrors();
        this.httpStatus = httpStatus;
    }

    public List<ObjectError> getAllErrors() {
        return allErrors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
```
- Generate all the excpetion result with reponse with the same Excetpion handler class [ServiceProviderControllerExceptionHandler](service-provider-service/src/main/java/com/example/serviceprovider/exception/handler/ServiceProviderControllerExceptionHandler.java).
The `@ControllerAdvice` annotation without parameter will act as global exception hander for all the controllers within this SpringBoot application.
However `@ControllerAdvice` annotation with parameter like `@ControllerAdvice(assignableTypes = {ServiceProviderController.class})` or `@ControllerAdvice(basePackages = {"com.example.serviceprovider.controller"})` can specify the controller class list or base package list for this Excetpion handler.
```java
@ControllerAdvice // For all controllers within this SpringBoot application
//@ControllerAdvice(assignableTypes = {ServiceProviderController.class})  // Specify controller classes
//@ControllerAdvice(basePackages = {"com.example.serviceprovider.controller"}) // Specify controller base package for scan
public class ServiceProviderControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderControllerExceptionHandler.class.getClass());

    @ExceptionHandler(InputValidationException.class)
    @ResponseBody
    public ResponseEntity<?> josnErrorResponse(HttpServletRequest request, InputValidationException exception){
       logger.error("Input validation failed with excetpion for request url: " + request.getRequestURI(), exception);
        ErrorResponse<List> errorResponse = new ErrorResponse<>();
        errorResponse.setErrorDetail(exception.getAllErrors());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setException(exception.getClass());
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }
}
```
- Define the [ErrorResponse](service-provider-service/src/main/java/com/example/serviceprovider/exception/ErrorResponse.java) model for response.
For the `errorDetail` field you can customized the detail with your own error model class. Here just take the `List<ObjectError>` as an example to demostrate all the errors fromatted into JSON arrays.
```java
public class ErrorResponse<T> {

    private Date timestamp;

    private String message;

    private Class exception;

    private String path;

    private T errorDetail;
	... gettter & setter ignored
```
