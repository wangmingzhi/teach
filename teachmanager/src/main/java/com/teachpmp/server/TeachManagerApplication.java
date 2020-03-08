package com.teachpmp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TeachManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeachManagerApplication.class, args);
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
		return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
			@Override
			public void customize(ConfigurableWebServerFactory factory) {
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/login");
				factory.addErrorPages(error404Page, error401Page);
			}
		};
	}

}
