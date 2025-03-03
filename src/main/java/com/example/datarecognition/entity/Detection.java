package com.example.datarecognition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "detections")
@Getter
@Setter
@AllArgsConstructor
public class Detection {
    @Id
    private String id;

    @Indexed
    private Instant timestamp;
    @Indexed
    private String source;
    private String uniqueId;
    @Indexed
    private String type;
    private double confidence;

    @GeoSpatialIndexed
    private GeoJsonPoint location;

}