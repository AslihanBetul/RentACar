package com.abm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginRequestDto {
    @NotBlank(message = "Email boş olamaz")
    @Email
    private String email;
    @NotBlank(message = "şifreniz boş olamaz")

    private String  password;
}
