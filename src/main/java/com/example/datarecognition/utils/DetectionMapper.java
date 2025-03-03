package com.example.datarecognition.utils;

import com.example.datarecognition.entity.Detection;
import com.example.datarecognition.model.DetectionEvent;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

@Component
public class DetectionMapper {

    public Detection toEntity(DetectionEvent event) {
        return new Detection(
                null,
                event.getTimestamp(),
                event.getSource(),
                event.getUniqueId(),
                event.getType(),
                event.getConfidence(),
                new GeoJsonPoint(
                        event.getGeoLocation().getLatitude(),
                        event.getGeoLocation().getLongitude()
                )
        );
    }
}