package net.therap.exception;

/**
 * @author khandaker.maruf
 * @since 8/11/22
 */
public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException() {
        super("Record not found");
    }
}
