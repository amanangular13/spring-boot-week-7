package com.Aman.TestingApp;

import com.Aman.TestingApp.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestingAppApplication implements CommandLineRunner {

//    @Autowired
//    private DataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(TestingAppApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(dataService.getData());
    }
}
