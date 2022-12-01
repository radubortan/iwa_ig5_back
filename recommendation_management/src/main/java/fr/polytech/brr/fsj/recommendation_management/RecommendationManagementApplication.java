package fr.polytech.brr.fsj.recommendation_management;

import fr.polytech.brr.fsj.recommendation_management.job_offer_recommendation.models.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class RecommendationManagementApplication {


    public static void main(String[] args) {
        SpringApplication.run(RecommendationManagementApplication.class, args);
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }

}
