package fr.cacib.routingservice.message.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("CACIB ROUTING MESSAGE SERVICE")
						.version("1.0")
						.description("CACIB MESSAGE AND PARTNERS SERVICE"));
	}
}
