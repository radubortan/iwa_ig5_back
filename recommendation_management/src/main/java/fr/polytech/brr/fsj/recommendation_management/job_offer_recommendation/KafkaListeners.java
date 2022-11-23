package fr.polytech.brr.fsj.recommendation_management.job_offer_recommendation;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(
            topics= "job_offer",
            groupId = "groupId"
    )
    void listener(String data){
        System.out.println("Listener received: " + data);
    }
}
