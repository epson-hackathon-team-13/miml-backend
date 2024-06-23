package com.miml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(PrintingProperties.class)
public class MimlApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MimlApiApplication.class, args);
	}

}
