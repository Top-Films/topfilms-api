package io.topfilms.api.exceptions;


import org.springframework.graphql.execution.ErrorType;

public class TopFilmsException extends RuntimeException {

    private ErrorType errorType;

    public TopFilmsException() {
        super();
    }

    public TopFilmsException(String message) {
        super(message);
    }

    public TopFilmsException(ErrorType errorType) {
        super();
        this.errorType = errorType;
    }

    public TopFilmsException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

}
