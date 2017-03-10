package com.anax.preferencerhythm.exception;

/**
 * Created by Anas AlaaEldin on 3/10/2017.
 */

public class SharedPrefDataNotFoundException extends Exception {

    public SharedPrefDataNotFoundException() {
    }

    public SharedPrefDataNotFoundException(String message) {
        super(message);
    }

    public SharedPrefDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SharedPrefDataNotFoundException(Throwable cause) {
        super(cause);
    }
}
