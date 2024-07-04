package com.abm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RentSaveDto {
    private String carId;
    private String userId;
    private Long rentDate;
    private Long returnDate;
}
