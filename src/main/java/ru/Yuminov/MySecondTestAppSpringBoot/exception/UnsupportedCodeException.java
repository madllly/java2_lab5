package ru.yuminov.MySecondTestAppSpringBoot.exception;

public class UnsupportedCodeException extends RuntimeException {
    private final String errorCode;

    public UnsupportedCodeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
