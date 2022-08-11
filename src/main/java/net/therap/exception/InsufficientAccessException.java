package net.therap.exception;

/**
 * @author khandaker.maruf
 * @since 7/31/22
 */
public class InsufficientAccessException extends RuntimeException {

    public InsufficientAccessException() {
        super("User is not authorized");
    }
}
