package com.abm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TokenReturnDto {
    private String id;
    private String name;
    private  String password;
    private String role;

}
