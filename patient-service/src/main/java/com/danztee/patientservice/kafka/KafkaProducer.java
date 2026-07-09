package com.danztee.patientservice.kafka;

import com.danztee.patientservice.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaProducer {

    private static final String TOPIC = "my_topic";
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setFirstName(patient.getFirstName())
                .setLastName(patient.getLastName())
                .setEmail(patient.getEmail())
                .setPhoneNumber(patient.getPhoneNumber())
                .setEventType("PATIENT_CREATED")
                .build();

        try {

            kafkaTemplate.send("patient", event.toByteArray());
            log.info("Event sent: {}", event);

        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
            throw new RuntimeException(e);
        }


//
//        kafkaTemplate.send(TOPIC, message);
//        System.out.println("Message sent: " + message);
    }

}
