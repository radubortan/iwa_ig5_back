package fr.polytech.bbr.fsj;

import fr.polytech.bbr.fsj.model.Rating;
import fr.polytech.bbr.fsj.service.RatingService;
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
	CommandLineRunner run(RatingService ratingService) {
		return args -> {
			ratingService.saveRating(new Rating("1", 2, "", "1", "2"));
			ratingService.saveRating(new Rating("2", 3, "test", "1", "2"));
		};
	}
}
