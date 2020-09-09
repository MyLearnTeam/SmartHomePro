package com.qcode365.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@MapperScan("com.qcode365.project")
@EnableCaching
public class projectApplication {

   public static void main(String[] args) {
       SpringApplication.run(projectApplication.class, args);
   }


	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		return (container -> {
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/jsp/systemManagement/error/404.jsp");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
					"/jsp/systemManagement/error/500.jsp");
			container.addErrorPages(error404Page, error500Page);
		});
	}
   
}
