package de.arthurpicht.utils.struct.dag.manager;

public class DagValidationException extends Exception {

    public DagValidationException() {
    }

    public DagValidationException(String message) {
        super(message);
    }

    public DagValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DagValidationException(Throwable cause) {
        super(cause);
    }

    public DagValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
