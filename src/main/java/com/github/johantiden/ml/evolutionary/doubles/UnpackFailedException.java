package com.github.johantiden.ml.evolutionary.doubles;

public class UnpackFailedException extends Exception {
    public UnpackFailedException() {
    }

    public UnpackFailedException(String message) {
        super(message);
    }

    public UnpackFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnpackFailedException(Throwable cause) {
        super(cause);
    }

    public UnpackFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
