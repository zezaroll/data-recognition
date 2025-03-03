package com.example.datarecognition.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetectionEvent {

    private Instant timestamp;
    private String source;
    private String uniqueId;
    private String type;
    private double confidence;
    private GeoLocation geoLocation;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GeoLocation {
        private double latitude;
        private double longitude;
    }
}