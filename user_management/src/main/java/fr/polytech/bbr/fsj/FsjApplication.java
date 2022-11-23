package fr.polytech.bbr.fsj;

import fr.polytech.bbr.fsj.model.AppUser;
import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.model.Employer;
import fr.polytech.bbr.fsj.model.Role;
import fr.polytech.bbr.fsj.service.AppUserService;
import fr.polytech.bbr.fsj.service.CandidateService;
import fr.polytech.bbr.fsj.service.EmployerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class FsjApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsjApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService, EmployerService employerService, CandidateService candidateService) {
		return args -> {
			appUserService.saveRole(new Role(null, "ROLE_EMPLOYER"));
			appUserService.saveRole(new Role(null, "ROLE_CANDIDATE"));
			appUserService.saveUser(new AppUser("1", "radu.bortan@etu.umontpellier.fr", "password"), "ROLE_EMPLOYER");
			appUserService.saveUser(new AppUser("2", "bortanradu@gmail.com", "password"), "ROLE_CANDIDATE");
			employerService.saveEmployer(new Employer("1", "Polytech", "Montpellier", "0606515270"));
			candidateService.saveCandidate(new Candidate("2", "Bortan", "Radu", LocalDate.of(2000, 1, 11), "0603507066", "1234", "react, angular"));
		};
	}

}
