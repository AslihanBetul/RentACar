package com.abm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    USERNAME_ALREADY_TAKEN(1000, "Kullanıcı adı zaten alınmış", HttpStatus.CONFLICT),
    EMAIL_EXIST(1001, "Email zaten kullanılıyor", HttpStatus.CONFLICT),
    USERNAME_IS_NULL(1002, "Kullanıcı adı boş", HttpStatus.BAD_REQUEST),
    EMAIL_IS_NULL(1003, "Email boş", HttpStatus.BAD_REQUEST),
    USERNAME_FORMAT_NOT_CORRECT(1004, "Kullanıcı adı formatı yanlış", HttpStatus.BAD_REQUEST),
    EMAIL_FORMAT_NOT_CORRECT(1005, "Email formatı yanlış", HttpStatus.BAD_REQUEST),
    PASSWORD_MUST_BE_MIN_8_CHARACTER(1006, "Şifre en az 8 karakter olmalıdır", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_ERROR(1007, "Geçersiz istek parametreleri", HttpStatus.BAD_REQUEST),
    USERNAME_OR_PASSWORD_WRONG(1008, "Kullanıcı adı veya şifre yanlış", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH(1009, "Şifreler uyuşmuyor", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_REQUEST(1010, "Yetkisiz istek", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1011, "Geçersiz token", HttpStatus.UNAUTHORIZED),
    TOKEN_CREATION_FAILED(1012, "Token oluşturma başarısız", HttpStatus.SERVICE_UNAVAILABLE),
    TOKEN_VERIFY_FAILED(1013, "Token doğrulama başarısız", HttpStatus.SERVICE_UNAVAILABLE),
    TOKEN_FORMAT_NOT_ACCEPTABLE(1014, "Token formatı kabul edilemez", HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_WRONG(1015, "Aktivasyon kodu geçersiz", HttpStatus.BAD_REQUEST),
    ACCOUNT_IS_NOT_ACTIVE(1016, "Hesap aktif değil", HttpStatus.BAD_REQUEST),
    ACCOUNT_IS_BANNED(1017, "Hesap yasaklanmış", HttpStatus.FORBIDDEN),
    ACCOUNT_IS_DELETED(1018, "Hesap silinmiş", HttpStatus.NOT_FOUND),
    ACCOUNT_IS_ALREADY_DELETED(1019, "Hesap zaten silinmiş", HttpStatus.CONFLICT),
    ACCOUNT_ALREADY_ACTIVATED(1020, "Hesap zaten aktif", HttpStatus.CONFLICT),
    USER_NOT_FOUND(1021, "Kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    USER_SERVICE_UNAVAILABLE(1022, "Kullanıcı servisi kullanılamıyor", HttpStatus.SERVICE_UNAVAILABLE),
    USER_SERVICE_CAN_NOT_SAVE_USER_PROFILE(1023, "Kullanıcı profili kaydedilemiyor", HttpStatus.SERVICE_UNAVAILABLE),
    ACCOUNT_CREATION_FAILED(1024, "Hesap oluşturulurken hata oluştu. Lütfen tekrar deneyin.", HttpStatus.SERVICE_UNAVAILABLE),
    EMAIL_OR_PASSWORD_WRONG(1025, "Email veya şifre yanlış", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(1026, "Email bulunamadı", HttpStatus.BAD_REQUEST),

    REPASSWORDCODE_OR_EMAIL_WRONG(1027, "repassword kodu ya da email geçersiz", HttpStatus.BAD_REQUEST ),
    TOKEN_ARGUMENT_NOTVALID(1028, "token argument notvalid", HttpStatus.BAD_REQUEST ),
    INVALID_UNAUTHORIZED(1029, "invalid unauthorized", HttpStatus.BAD_REQUEST);


    private Integer code;
    private String message;
    private HttpStatus httpStatus;
}
