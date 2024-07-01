package com.abm.entity;

import com.abm.entity.enums.AuthRole;
import com.abm.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tblauth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @Column(unique = true)
    @Length(min = 3,max = 30)
    private String  username;
    @Length(min = 8,max = 25)
    private String  password;
    @Column(unique = true)
    @Email
    private String email;
    private String activationCode;
    private String rePasswordCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    Status authStatus= Status.PENDING;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    AuthRole authRole=AuthRole.USER;

    @Builder.Default
    private Long createAt=System.currentTimeMillis();

    private Long updateAt;

}
