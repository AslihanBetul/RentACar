package com.abm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RentResponseDto {
    private String rentId;
    private String carId;
    private String userId;
    private String rentDate;
    private String returnDate;
    private double totalPrice;
}
