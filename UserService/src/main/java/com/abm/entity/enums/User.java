package com.abm.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    @MongoId
    private String id;
    private Long authId;
    private String name;
    private String Lastname;
    private String email;
    private Status userStatus;
    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
