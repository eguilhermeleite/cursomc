package com.leite.edvaldo.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.leite.edvaldo.cursomc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String str;

	@Bean
	public boolean instantiateDatabase() throws ParseException {

		if(!"create".equals(str)) {
			return false;
		}
		
		dbService.instantiateTestDatabase();
		return true;
	}

}
