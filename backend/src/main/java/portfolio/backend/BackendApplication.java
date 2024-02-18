package portfolio.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import portfolio.backend.authentication.config.properties.AppProperties;
import portfolio.backend.authentication.config.properties.CorsProperties;
@PropertySource(value = "classpath:/aws.properties", ignoreResourceNotFound = true)
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
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000", "http://atogato.duckdns.org", "https://atogato-fe-tau.vercel.app")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(true)
						.maxAge(3600);
			}
		};
	}
}
