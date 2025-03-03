package com.example.datarecognition.service;

import com.example.datarecognition.entity.Detection;
import com.example.datarecognition.repository.DetectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetectionServiceImpl implements DetectionService {
    private final DetectionRepository detectionRepository;

    Logger logger = LoggerFactory.getLogger(DetectionServiceImpl.class);

    @Autowired
    public DetectionServiceImpl(DetectionRepository detectionRepository) {
        this.detectionRepository = detectionRepository;
    }

    public List<Detection> getDetectionFilteredGeoLocation() {
        //TODO: add filtering by type into repo
        return new ArrayList<>();
    }

    @Override
    public Page<Detection> findAll(Pageable pageable) {
        try {
            return detectionRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error during findAll: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Detection> getDetectionFilteredByTime(String startTime, String endTime, Pageable pageable) {
        try {
            return detectionRepository.findByTimestampBetween(startTime, endTime, pageable);
        } catch (Exception e) {
            logger.error("Error during getDetectionFilteredByTime: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Detection> getDetectionFilteredByType(String type, Pageable pageable) {
        try {
            return detectionRepository.findByType(type, pageable);
        } catch (Exception e) {
            logger.error("Error during getDetectionFilteredByType: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}