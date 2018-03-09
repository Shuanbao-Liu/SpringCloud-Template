package com.siemens.mindsphere.maa;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class ServiceAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAdminServiceApplication.class, args);
	}
}
