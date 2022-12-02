package fr.polytech.brr.fsj.recommendation_management.job_offer_recommendation.kafka_configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.brr.fsj.recommendation_management.job_offer_recommendation.models.JobOffer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class JobOfferDeserializer implements Deserializer<JobOffer> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public JobOffer deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), JobOffer.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
    }
}


