package portfolio.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import portfolio.backend.authentication.config.properties.AppProperties;
import portfolio.backend.authentication.config.properties.CorsProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
public class BackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendApplication.class, args);
	}

}
