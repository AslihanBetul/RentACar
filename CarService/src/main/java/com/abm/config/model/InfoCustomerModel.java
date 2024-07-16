package com.abm.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InfoCustomerModel {
    private String userId;
    private String name;
    private String brand;
    private String model;
    private String plate;
    private String rentDate;
    private String returnDate;
    private Double totalPrice;




}
