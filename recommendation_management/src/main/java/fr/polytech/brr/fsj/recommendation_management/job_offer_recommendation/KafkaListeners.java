package fr.polytech.brr.fsj.recommendation_management.job_offer_recommendation;

import fr.polytech.brr.fsj.recommendation_management.job_offer_recommendation.models.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class KafkaListeners {
    @Value("${USER_SERVICE_URI}")
    private String user_service_uri;

    private WebClient webClient;

    public KafkaListeners(WebClient webclient) {
        this.webClient = webclient;
    }


    @KafkaListener(
            topics= "job_offer",
            groupId = "groupId"
    )
    void listener(String data){

        System.out.println("Listener received: " + data);
        Mono<Candidate[]> response = webClient
                .get()
                .uri("http://localhost:8080/api/users/candidates")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib3J0YW5yYWR1QGdtYWlsLmNvbSIsImFjY291bnRJZCI6IjIiLCJyb2xlcyI6WyJST0xFX0NBTkRJREFURSJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODEvYXBpL2xvZ2luIiwiZXhwIjoxNjcwNTExNDc1fQ.rMi7cV_u8YjR1xf8v5DRIhBBqaqq1gPmfOSiGNO4DEI")
                .retrieve()
                .bodyToMono(Candidate[].class)
                .log();
        Candidate[] candidates = response.block();
        for(Candidate candidate: candidates){
            System.out.println(candidate);
        }
    }
}
