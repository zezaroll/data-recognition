package com.example.datarecognition.service;

import com.example.datarecognition.entity.Detection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DetectionService {
    Page<Detection> getDetectionFilteredByTime(String startTime, String endTime, Pageable pageable);
    Page<Detection> getDetectionFilteredByType(String type, Pageable pageable);
    Page<Detection> findAll(Pageable pageable);
}
