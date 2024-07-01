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
public class RepasswordRequestDto {
    @NotBlank(message = "Email boş olamaz")
    @Email
    private String email;
    String rePasswordCode;
    @NotBlank(message = "şifreniz boş olamaz")
    @Length(min = 8,max = 25)
    private String  password;
    @NotBlank(message = "şifreniz boş olamaz")
    String confirmPassword;
}
