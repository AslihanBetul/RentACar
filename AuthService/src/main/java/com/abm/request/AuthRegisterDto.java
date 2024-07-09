package com.abm.request;

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
public class AuthRegisterDto {
    @NotBlank(message = "Email boş olamaz")
    @Email
    private String email;
    @NotBlank(message = "kullanıcı adınız boş olamaz")
    @Length(min = 3,max = 30)
    private String  username;
    @NotBlank(message = "şifreniz boş olamaz")

    private String  password;
    @NotBlank(message = "şifreniz boş olamaz")
    @Length(min = 8,max = 25)
    private String  confirmPassword;
}
