package com.TradeProject.ClientConnectivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ClientConnectivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientConnectivityApplication.class, args);
	}

}
