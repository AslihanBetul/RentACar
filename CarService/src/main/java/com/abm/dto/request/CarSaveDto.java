package com.abm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CarSaveDto {
    private String name;
    private String brand;
    private String model;
    private String color;
    private String plate;
    private String fuel;
    private Double pricePerDay;
    private String sasiNo;
    private String imageUrl;
}
