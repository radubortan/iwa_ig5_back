package fr.polytech.bbr.fsj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FsjApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsjApplication.class, args);
	}

/*	@Bean
	CommandLineRunner run(AppUserServiceImplementation appUserServiceImplementation) {
		return args -> {
			appUserServiceImplementation.saveRole(new Role(null, "ROLE_USER"));
			appUserServiceImplementation.saveRole(new Role(null, "ROLE_ADMIN"));

			appUserServiceImplementation.saveUser(new AppUser(null, "bortanradu@gmail.com", "password", "0603515270"));

			appUserServiceImplementation.addRoleToAppUser("bortanradu@gmail.com", "ROLE_USER");
		};
	}*/

}
