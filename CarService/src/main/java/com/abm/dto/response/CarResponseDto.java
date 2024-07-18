package com.abm.dto.response;

import com.abm.entity.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CarResponseDto {
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
    private CarStatus carStatus;


}
