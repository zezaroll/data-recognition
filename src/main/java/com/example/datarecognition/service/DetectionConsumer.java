package com.example.datarecognition.service;

import com.example.datarecognition.entity.Detection;
import com.example.datarecognition.model.DetectionEvent;
import com.example.datarecognition.repository.DetectionRepository;
import com.example.datarecognition.utils.DetectionMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetectionConsumer {
    private final DetectionMapper detectionMapper;
    private final DetectionRepository detectionRepository;

    Logger logger = LoggerFactory.getLogger(DetectionConsumer.class);

    @Autowired
    public DetectionConsumer(DetectionMapper detectionMapper, DetectionRepository detectionRepository) {
        this.detectionMapper = detectionMapper;
        this.detectionRepository = detectionRepository;
    }

    @KafkaListener(topics = "detections", groupId = "backend-group", containerFactory = "kafkaListener")
    public void process(List<DetectionEvent> events, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        System.out.println("Received " + events.size() + " events from key: " + key);
        System.out.println("Events: " + events);
        try {
            List<Detection> detectionEntities = events.stream()
                    .map(detectionMapper::toEntity)
                    .collect(Collectors.toList());

            detectionRepository.saveAll(detectionEntities);
            System.out.println("Stored " + detectionEntities.size() + " detections to MongoDB");
        } catch (Exception e) {
            logger.error("Error processing detections: " + e.getMessage());
            e.printStackTrace();
        }
    }
}