package com.abm.dto.request;

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
public class AccountActivationRequestDto {
    @NotBlank(message = "kullanıcı adınız boş olamaz")
    @Length(min = 3,max = 30)
    private String  username;
    @NotBlank(message = "şifreniz boş olamaz")
    @Length(min = 8,max = 25)
    private String  password;
    @NotBlank(message = "aktivasyon kodunuz boş olamaz")
    private String activationCode;
}
