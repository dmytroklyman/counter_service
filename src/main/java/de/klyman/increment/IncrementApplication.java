package de.klyman.increment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
	    info = @Info(
	        title = "REST API counter service", 
	        version = "0.0.1", 
	        description = "Maybe the most fantastic REST counter service in the world",
	        termsOfService = "",
	        contact = @Contact(email = "klyman.dmytro@gmail.com"),
	        license = @License(
	            name = "Apache License 2.0",
	            url = "https://www.apache.org/licenses/LICENSE-2.0"
	        )
	    )
	)
public class IncrementApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncrementApplication.class, args);
	}

}
