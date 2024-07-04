package com.abm.exception;

import lombok.Getter;

@Getter
public class CarServiceException extends RuntimeException{


    private ErrorType errorType;

    public CarServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public CarServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

}
