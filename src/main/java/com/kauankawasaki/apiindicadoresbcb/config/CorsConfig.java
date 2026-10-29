package com.kauankawasaki.apiindicadoresbcb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// API pública e somente leitura: qualquer site pode consultar via GET
		registry.addMapping("/indicadores/**")
				.allowedOrigins("*")
				.allowedMethods("GET");
	}

}
