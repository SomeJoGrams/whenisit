package com.joGram.whenisit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@SpringBootApplication
public class WhenisitApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhenisitApplication.class, args);
	}

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // liverelaod and server default address
                registry.addMapping("/date").allowedOrigins("http://localhost:8080", "http://localhost:63342");
            }
        };
    }
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
            CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
            filter.setIncludeQueryString(true);
            filter.setIncludePayload(true);
            filter.setMaxPayloadLength(10000);
            filter.setIncludeHeaders(false);
            filter.setAfterMessagePrefix("REQUEST DATA: ");
            return filter;
        }

}
