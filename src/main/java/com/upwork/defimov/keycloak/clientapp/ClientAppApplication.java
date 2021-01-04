package com.upwork.defimov.keycloak.clientapp;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class ClientAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientAppApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasenames("classpath:errorMessages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageSource.setDefaultLocale(Locale.US);

		return messageSource;
	}

}
