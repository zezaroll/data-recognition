package com.example.datarecognition.service;

import com.example.datarecognition.model.DetectionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DetectionProducer {
    private final KafkaTemplate<String, List<DetectionEvent>> kafkaTemplate;

    Logger logger = LoggerFactory.getLogger(DetectionProducer.class);

    @Autowired
    public DetectionProducer(KafkaTemplate<String, List<DetectionEvent>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void receiveDetections(List<DetectionEvent> detections) {
        CompletableFuture<SendResult<String, List<DetectionEvent>>> detectionsFuture =
                kafkaTemplate.send("detections", detections.get(0).getSource(), detections);

        detectionsFuture.whenCompleteAsync((r, e) -> {
            if (e == null) {
                System.out.println("Successfully ingested " + detections.size() + " detections.");
            } else {
                logger.error("Error sending message: {}", e.getMessage());
            }
        });
    }
}
