package com.abm.exception;

import lombok.Getter;

@Getter
public class RentServiceException extends RuntimeException{


    private ErrorType errorType;

    public RentServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public RentServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

}
