package com.example.r2dbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.r2dbc.spi.ConnectionFactory;

@RestController
@SpringBootApplication
public class R2dbcApplication {

	@Qualifier("connectionFactory")
	@Autowired
	private ConnectionFactory connectionFactory;


	public static void main(String[] args) {
		SpringApplication.run(R2dbcApplication.class, args);
	}

	@GetMapping("test")
	public String test(){
		DatabaseClient client = DatabaseClient.create(connectionFactory);
		client.execute("select 100000").fetch().all().subscribe(stringObjectMap -> System.out.println(stringObjectMap.size()));
		return "test";
	}
}
