package com.example.datarecognition.controller;

import com.example.datarecognition.entity.Detection;
import com.example.datarecognition.model.DetectionEvent;
import com.example.datarecognition.service.DetectionProducer;
import com.example.datarecognition.service.DetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/detections")
public class DetectionController {
    private final DetectionService detectionService;
    private final DetectionProducer detectionProducer;

    Logger logger = LoggerFactory.getLogger(DetectionController.class);

    @Autowired
    public DetectionController(DetectionService detectionService, DetectionProducer detectionProducer) {
        this.detectionService = detectionService;
        this.detectionProducer = detectionProducer;
    }

    @PostMapping
    public ResponseEntity<String> receiveDetections(@RequestBody List<DetectionEvent> detections) {
        try {
            detectionProducer.receiveDetections(detections);
        } catch (Exception e) {
            logger.error("Error while handling detection: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Received " + detections.size() + " detections");
        }

        return ResponseEntity.accepted().body("Received " + detections.size() + " detections");
    }

    @GetMapping
    public ResponseEntity<List<Detection>> getDetections(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size);

        //TODO: add geoLocation filtering call & provide object mapper;

        if (startTime != null && endTime != null) {
            return ResponseEntity.ok(detectionService.getDetectionFilteredByTime(startTime, endTime, pageable).getContent());
        } else if (type != null) {
            return ResponseEntity.ok(detectionService.getDetectionFilteredByType(type, pageable).getContent());
        }

        return ResponseEntity.ok(detectionService.findAll(pageable).getContent());
    }
}