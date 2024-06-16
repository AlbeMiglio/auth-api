package it.albemiglio.authapi.exceptions;

public class AuthModuleInitializationException extends Exception {

    public AuthModuleInitializationException(String message) {
        super(message);
    }

    public AuthModuleInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

