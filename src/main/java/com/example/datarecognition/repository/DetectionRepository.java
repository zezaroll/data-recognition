package com.example.datarecognition.repository;

import com.example.datarecognition.entity.Detection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectionRepository extends MongoRepository<Detection, String> {
    Page<Detection> findByTimestampBetween(String startTime, String endTime, Pageable pageable);
    Page<Detection> findByType(String type, Pageable pageable);
}