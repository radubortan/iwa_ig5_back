package fr.polytech.bbr.fsj;

import fr.polytech.bbr.fsj.model.Role;
import fr.polytech.bbr.fsj.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FsjApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsjApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		return args -> {
			appUserService.saveRole(new Role(null, "ROLE_EMPLOYER"));
			appUserService.saveRole(new Role(null, "ROLE_CANDIDATE"));
		};
	}

}
