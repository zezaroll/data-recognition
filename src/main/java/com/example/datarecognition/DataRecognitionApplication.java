package com.example.datarecognition;

import com.example.datarecognition.model.DetectionEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class DataRecognitionApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataRecognitionApplication.class, args);
    }

    @Bean
    CommandLineRunner init() throws Exception {
        return args -> {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/v1/api/detections"; // Adjust if port differs
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Simulate 3 devices, each sending 50-100 detections
            String[] devices = {"device-001", "device-002", "device-003"};
            String[] types = {"vehicle", "person", "animal"};
            Random random = new Random();

            for (String deviceId : devices) {
                int detectionCount = 50 + random.nextInt(51); // 50-100 detections
                List<DetectionEvent> detections = new ArrayList<>();

                for (int i = 0; i < detectionCount; i++) {
                    DetectionEvent detection = new DetectionEvent(
                            Instant.now().minusSeconds(random.nextInt(3600)),
                            deviceId,
                            UUID.randomUUID().toString(),
                            types[random.nextInt(types.length)], // type
                            0.5 + random.nextDouble() * 0.5, // confidence (0.5-1.0)
                            new DetectionEvent.GeoLocation(-122.4194 + random.nextDouble(), 37.7749 + random.nextDouble())
                    );
                    detections.add(detection);
                }

                // Set device ID in headers
                headers.set("X-Device-ID", deviceId);
                HttpEntity<List<DetectionEvent>> request = new HttpEntity<>(detections, headers);

                // Send request
                String response = restTemplate.postForObject(url, request, String.class);
                System.out.println("Sent " + detectionCount + " detections for " + deviceId + ": " + response);

                // Simulate 100ms delay between batches (your spec)
                Thread.sleep(100);
            }

            System.out.println("CommandLineRunner completed: Simulated edge device requests");
        };
    }
}