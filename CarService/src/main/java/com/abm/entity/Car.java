package com.abm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document
public class Car {
    @MongoId
    private String id;
    private String name;

    private String brand;
    private String model;
    private String color;
    private String plate;
    private String fuel;
    private Double pricePerDay;
    private String sasiNo;
    private String imageUrl;
    @Builder.Default
    private CarStatus carStatus = CarStatus.AVAILABLE;

}
