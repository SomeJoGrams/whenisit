package com.joGram.whenisit;

import com.joGram.whenisit.dates.TimeZoneCity;
import com.joGram.whenisit.dates.TimeZonesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.Collectors;

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
                registry.addMapping("/timezones").allowedOrigins("http://localhost:8080", "http://localhost:63342");

            }
        };
    }

    @Bean
    public CommandLineRunner addTimeZones(TimeZonesRepository repository) {
        return (args) -> {
            // save a few customers
            repository.save(new TimeZoneCity("Berlin", "GMT+1"));
//            repository.saveAll(ZoneId.SHORT_IDS.keySet().stream()
//                    .map(zoneId -> new TimeZoneCity(ZoneId.of.get(zoneId),zoneId))
//                    .toList()
//            );
            repository.saveAll(ZoneId.getAvailableZoneIds().stream()
                    .map(zoneId -> new TimeZoneCity(zoneId,zoneId))
                    .toList()
            ); // TODO parse this better
            //ZoneId.of(cityOrSymbol).getDisplayName(TextStyle.NARROW, Locale.GERMAN)
//            TimeZoneCity res = repository.findByCity("Berlin");
//            System.out.println(res.getPair());
//
//            for (TimeZoneCity customer : repository.findAll()) {
//                System.out.println(customer.getPair());
//            }


            // fetch all timezones
//            for (Customer customer : repository.findAll()) {
//                log.info(customer.toString());
//            }
//            log.info("");
//
//            // fetch an individual customer by ID
//            Customer customer = repository.findById(1L);
//            log.info("Customer found with findById(1L):");
//            log.info("--------------------------------");
//            log.info(customer.toString());
//            log.info("");
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            repository.findByLastName("Bauer").forEach(bauer -> {
//                log.info(bauer.toString());
//            });
//            // for (Customer bauer : repository.findByLastName("Bauer")) {
//            //  log.info(bauer.toString());
//            // }
//            log.info("");
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
