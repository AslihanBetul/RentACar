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
public class Rent {
    @MongoId
    private String id;
    private String carId;
    private String userId;
    private String rentDate;
    private String returnDate;
    private Double totalPrice;
}
