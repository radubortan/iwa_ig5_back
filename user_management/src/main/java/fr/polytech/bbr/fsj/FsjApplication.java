package fr.polytech.bbr.fsj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FsjApplication {
	public static void main(String[] args) {
		SpringApplication.run(FsjApplication.class, args);
	}

}