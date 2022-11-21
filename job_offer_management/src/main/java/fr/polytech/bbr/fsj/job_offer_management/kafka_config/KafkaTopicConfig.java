package fr.polytech.bbr.fsj.job_offer_management.kafka_config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic jobOfferTopic(){
        return TopicBuilder.name("job_offer")
                .build();
    }
}
